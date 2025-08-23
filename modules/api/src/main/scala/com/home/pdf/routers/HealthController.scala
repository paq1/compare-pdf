package com.home.pdf.routers

import com.home.pdf.routers.HealthController.HealthView
import play.api.libs.json.{Json, Reads, Writes}
import play.api.mvc._

import scala.annotation.unused
import scala.concurrent.{ExecutionContext, Future}

final class HealthController(
    override val controllerComponents: ControllerComponents
)(implicit @unused ec: ExecutionContext)
    extends BaseController {
  def health(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val jsValue = Json.toJson(HealthView())
    Future.successful(Ok(jsValue))
  }
}

object HealthController {
  final case class HealthView(health: String = "up")
  object HealthView {
    implicit lazy val write: Writes[HealthView] = { obj =>
      Json.obj("health" -> obj.health)
    }
    implicit lazy val reads: Reads[HealthView] = { json =>
      (json \ "health")
        .validate[String]
        .map { content =>
          HealthView(
            health = content
          )
        }
    }
  }
}
