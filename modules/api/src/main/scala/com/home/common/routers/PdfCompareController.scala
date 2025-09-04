package com.home.common.routers

import com.errors.cats.Implicits._
import com.errors.{ErrorCode, Failure}
import com.home.common.comparator.files.CanCompareFile
import com.home.common.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import com.home.common.views.DifferencesView
import org.apache.pekko.stream.scaladsl.StreamConverters
import play.api.Environment
import play.api.libs.Files
import play.api.libs.json.Json
import play.api.mvc._

import scala.annotation.unused
import scala.concurrent.{ExecutionContext, Future}

class PdfCompareController(
    fileComparator: CanCompareFile[FilePartTemporary],
    env: Environment,
    override val controllerComponents: ControllerComponents
)(implicit @unused ec: ExecutionContext)
    extends BaseController {

  def diffTextPdf(): Action[MultipartFormData[Files.TemporaryFile]] =
    Action(parse.multipartFormData).async { request =>
      val response = (for {
        pdf1 <- request.body.file("pdf1")
        pdf2 <- request.body.file("pdf2")
      } yield (pdf1, pdf2))
        .map { case (pdf1, pdf2) =>
          fileComparator
            .compare(pdf1, pdf2)
            .map(DifferencesView.intoSingleJsonApi)
            .intoResult(200)
        }
        .getOrElse(
          BadRequest(
            Json.toJson(
              Failure
                .of(ErrorCode.BadRequest())
                .withDetail("il faut deux pdf (pdf1 et pdf2)")
            )
          )
        )

      Future.successful(response)
    }

  def mockDownloadFile(): Action[AnyContent] = Action {
    env
      .resourceAsStream("exemple.pdf")
      .map { inputStream =>
        val source = StreamConverters.fromInputStream(() => inputStream)
        Ok.chunked(content = source, inline = false, fileName = Some("exemple.pdf"))
      }
      .getOrElse(NotFound)

  }

}
