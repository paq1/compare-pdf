package com.home.documents.common.routers

import com.errors.cats.Implicits._
import com.errors.{ErrorCode, Failure}
import com.home.common.comparator.files.CanCompareFile
import com.home.documents.common.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import com.home.documents.common.view.mappers.DifferencesViewMapper
import org.apache.pekko.stream.scaladsl.StreamConverters
import play.api.Environment
import play.api.libs.Files
import play.api.libs.json.Json
import play.api.mvc._

import scala.annotation.unused
import scala.concurrent.{ExecutionContext, Future}

class DocumentsCompareController(
    fileComparator: CanCompareFile[FilePartTemporary],
    env: Environment,
    override val controllerComponents: ControllerComponents
)(implicit @unused ec: ExecutionContext)
    extends BaseController {

  // FIXME : prendre en compte tout type de documents (pdf uniquement pour le moment)
  def diffText(): Action[MultipartFormData[Files.TemporaryFile]] =
    Action(parse.multipartFormData).async { request =>
      val response = (for {
        pdf1 <- request.body.file("document1")
        pdf2 <- request.body.file("document2")
      } yield (pdf1, pdf2))
        .map { case (pdf1, pdf2) =>
          fileComparator
            .compare(pdf1, pdf2)
            .map(DifferencesViewMapper.intoSingleJsonApi)
            .intoResult(200)
        }
        .getOrElse(
          BadRequest(
            Json.toJson(
              Failure
                .of(ErrorCode.BadRequest())
                .withDetail("il faut deux document (documents1 et documents2)")
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
