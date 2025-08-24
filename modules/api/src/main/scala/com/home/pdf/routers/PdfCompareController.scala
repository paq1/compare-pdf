package com.home.pdf.routers

import com.home.pdf.services.comparator.files.CanCompareFile
import com.home.pdf.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import com.home.pdf.views.DiffView
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
    fileCompartor: CanCompareFile[FilePartTemporary],
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
          fileCompartor
            .compare(pdf1, pdf2)
            .map(DiffView.intoSingleJsonApi)
            .map(view => Json.toJson(view))
            .map(Ok(_))
            .getOrElse(
              InternalServerError(
                Json.obj("error" -> "une erreur est survenue")
              )
            )
        }
        .getOrElse(
          BadRequest(Json.obj("error" -> "il faut deux pdf (pdf1 et pdf2)"))
        )

      Future.successful(response)
    }

}
