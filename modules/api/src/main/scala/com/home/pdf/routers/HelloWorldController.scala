package com.home.pdf.routers

import com.home.pdf.routers.HelloWorldController.HelloWorldView
import play.api.libs.json.{Json, Reads, Writes}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

final class HelloWorldController(
    override val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext)
    extends BaseController {
  def helloWorld(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val jsValue = Json.toJson(HelloWorldView())
    Future.successful(Ok(jsValue))
  }
}

object HelloWorldController {
  final case class HelloWorldView(content: String = "hello world")
  object HelloWorldView {
    implicit lazy val write: Writes[HelloWorldView] = { obj =>
      Json.obj("content" -> obj.content)
    }
    implicit lazy val reads: Reads[HelloWorldView] = { json =>
      (json \ "content")
        .validate[String]
        .map { content =>
          HelloWorldView(
            content = content
          )
        }
    }
  }
}
