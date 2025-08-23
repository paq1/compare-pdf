package com.home.pdf.routers

import com.home.pdf.routers.HelloWorldController.HelloWorldView
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.{JsError, JsSuccess}
import play.api.test._
import play.api.test.Helpers._

import scala.concurrent.ExecutionContext.Implicits.global

class HelloWorldControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "show hello world" in {

      val expectedView = HelloWorldView()
      val controller = new HelloWorldController(stubControllerComponents())
      val helloWorldF = controller.helloWorld().apply(FakeRequest(GET, "/helloWorld"))

      status(helloWorldF) mustBe OK
      contentType(helloWorldF) mustBe Some("application/json")
      contentAsJson(helloWorldF)
        .validate[HelloWorldView] match {
        case JsSuccess(value, _) => value mustBe expectedView
        case JsError(_) => fail("should be valid")
      }
    }
  }

}
