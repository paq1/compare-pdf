package com.home.common.steps

import cats.data.EitherT
import com.github.agourlay.cornichon.core.Step
import com.github.agourlay.cornichon.steps.regular.EffectStep
import com.home.common.cornichon.{CornichonErrorCustom, CornichonFeatureCustom}

import scala.concurrent.{ExecutionContext, Future}

trait AssertHealthStep { self: CornichonFeatureCustom =>

  protected def assertHealthUp(expected: String = "up")(implicit ec: ExecutionContext): Step = {
    AttachAs("Call health and assert is up") {
      When I get(s"$url/health")
      Then I save_body_path("health" -> "health_result")
      Then assert status.is(200)
      And assert body.is(
        """{
          |  "health": "up"
          |}""".stripMargin)

      EffectStep.fromEitherT(
        title = "check result",
        effect = { sc =>
          EitherT {
            sc.session
              .getOpt("health_result")
              .map { healthResult =>
                Future
                  .successful(isUp(healthResult, expected))
                  .map { res =>
                    if (res) Right(sc.session)
                    else Left(CornichonErrorCustom("fail"))
                  }
              }
              .getOrElse(Future.successful(Left(CornichonErrorCustom("fail"))))
          }

        }
      )
    }
  }

  private def isUp(value: String, expected: String = "up"): Boolean = value == expected

}
