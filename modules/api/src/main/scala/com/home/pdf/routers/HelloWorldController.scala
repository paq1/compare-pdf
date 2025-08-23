package com.home.pdf.routers

import com.home.pdf.routers.HelloWorldController.HelloWorldView
import play.api.libs.json.{Json, Writes}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

final class HelloWorldController(
    override val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext)
    extends BaseController {
  def helloWorld(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    println("pouet")
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
  }
}
