package com.home.pdf.routers

import cats.data.Validated
import com.home.pdf.routers.PdfCompareController.isPdf
import com.home.pdf.services.comparator.files.CanCompareFile
import com.home.pdf.views.DiffView
import org.apache.pekko.util.ByteString
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

class PdfCompareController(
    fileCompartor: CanCompareFile[ByteString],
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
            } yield {

              fileCompartor
                .compare(bsText1, bsText2)
                .map(DiffView.intoSingleJsonApi)
                .map(single => Json.toJson(single))
                .map(Ok(_)) match {
                case Validated.Valid(result) => result
                case Validated.Invalid(e) =>
                  InternalServerError(
                    Json.obj("error" -> "une erreur est survenue")
                  )
              }
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

  private def isPdf(
      file1: FilePartTemporary,
      file2: FilePartTemporary
  ): Boolean = {
    file1.contentType.contains("application/pdf") && file2.contentType.contains(
      "application/pdf"
    )
  }
}
