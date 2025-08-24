package com.home.pdf.routers

import com.home.pdf.routers.PdfCompareController.{extractPdfText, isPdf}
import com.home.pdf.services.comparator.texts.CanCompareText
import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper
import play.api.libs.Files
import play.api.libs.json.Json
import play.api.mvc.{
  Action,
  BaseController,
  ControllerComponents,
  MultipartFormData
}

import scala.annotation.unused
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class PdfCompareController(
    fileContentCompartor: CanCompareText,
    override val controllerComponents: ControllerComponents
)(implicit @unused ec: ExecutionContext)
    extends BaseController {

  def diffTextPdf: Action[MultipartFormData[Files.TemporaryFile]] =
    Action(parse.multipartFormData).async { request =>
      val response = (for {
        pdf1 <- request.body.file("pdf1")
        pdf2 <- request.body.file("pdf2")
      } yield (pdf1, pdf2))
        .map { case (pdf1, pdf2) =>
          if (!isPdf(pdf1, pdf2)) {
            UnsupportedMediaType(
              Json.obj("error" -> "les deux fichiers doivent être des pdf")
            )
          } else {
            (for {
              bsText1 <- pdf1.refToBytes(pdf1.ref)
              bsText2 <- pdf2.refToBytes(pdf2.ref)
              text1 <- extractPdfText(bsText1.toArray)
              text2 <- extractPdfText(bsText2.toArray)
            } yield {
              val differences = fileContentCompartor.compare(text1, text2)

              Ok(
                Json.obj(
                  "data" -> Json.obj(
                    "type" -> "comparaison",
                    "id" -> "whatever", // FIXME : generer un id
                    "attributes" -> Json.obj(
                      "isIdentique" -> differences.isEmpty,
                      "details" -> differences.map(_.toString)
                    )
                  )
                )
              )

            })
              .getOrElse(
                InternalServerError(
                  Json.obj("error" -> "une erreur est survenue")
                )
              )

          }
        }
        .getOrElse(
          BadRequest(Json.obj("error" -> "il faut deux pdf (pdf1 et pdf2)"))
        )

      Future.successful(response)

    }

}
object PdfCompareController {
  type FilePartTemporary = MultipartFormData.FilePart[Files.TemporaryFile]

  private def extractPdfText(bytes: Array[Byte]): Option[String] = {
    Try {
      val doc = Loader.loadPDF(bytes)
      val stripper = new PDFTextStripper()
      val text = stripper.getText(doc)
      doc.close()
      text
    }.toOption
  }

  private def isPdf(
      file1: FilePartTemporary,
      file2: FilePartTemporary
  ): Boolean = {
    file1.contentType.contains("application/pdf") && file2.contentType.contains(
      "application/pdf"
    )
  }
}
