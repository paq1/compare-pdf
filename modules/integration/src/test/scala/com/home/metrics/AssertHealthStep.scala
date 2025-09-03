package com.home.metrics

import com.github.agourlay.cornichon.CornichonFeature
import com.github.agourlay.cornichon.core.Step
import com.github.agourlay.cornichon.steps.regular.EffectStep
import com.github.agourlay.cornichon.steps.regular.assertStep.{AssertStep, GenericEqualityAssertion}

import scala.concurrent.{ExecutionContext, Future}

trait AssertHealthStep { self: CornichonFeature =>

  def assertHealthUp()(implicit ec: ExecutionContext): Step = {
    AttachAs("Call health and assert is up") {
      When I get("http://localhost:9000/health")
      Then I save_body_path(("health" -> "health_result"))
      Then assert status.is(200)
      And assert body.is(
        """{
          |  "health": "up"
          |}""".stripMargin)

      // EffectStep.fromAsync(
      //   title = "check result",
      //   effect = { sc =>
      //     Future.successful {
      //       sc.session.addValue("result_check", isUp("<health_result>").toString)
      //       sc.session
      //     }
      //   }
      // )

      Then I AssertStep(title = "xxx", sc => {
        sc.session
          .getOpt("health_result")
          .map { healthResult =>
            GenericEqualityAssertion(isUp(healthResult), true)
          }
          .getOrElse(GenericEqualityAssertion(true, false))
      })
    }
  }


  private def isUp(value: String): Boolean = value == "up"

}
