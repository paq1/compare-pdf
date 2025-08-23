package com.home.pdf.routers

import com.play.controllers.HealthController
import com.play.controllers.HealthController.HealthView
import org.scalatestplus.play._
import play.api.libs.json.{JsError, JsSuccess}
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.ExecutionContext.Implicits.global

class HealthControllerSpec extends PlaySpec {

  "HealthController GET" should {

    "show up" in {

      val expectedView = HealthView()
      val controller = new HealthController(stubControllerComponents())
      val healthF = controller.health().apply(FakeRequest(GET, "/health"))

      status(healthF) mustBe OK
      contentType(healthF) mustBe Some("application/json")
      contentAsJson(healthF)
        .validate[HealthView] match {
        case JsSuccess(value, _) => value mustBe expectedView
        case JsError(_) => fail("should be valid")
      }
    }
  }

}
