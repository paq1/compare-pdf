package com.home.metrics

import com.github.agourlay.cornichon.core.FeatureDef
import com.home.common.cornichon.CornichonFeatureCustom
import com.home.common.steps.AssertHealthStep

import scala.concurrent.ExecutionContext.Implicits.global

class MetricsTest extends CornichonFeatureCustom with AssertHealthStep {

  override def feature: FeatureDef = Feature("Call metrics") {

    (0 to  1).map { id =>
      val errors = 0 to 500
      val expected = if (errors.contains(id)) "up" // FIXME : mettre down
      else "up"
      Scenario(s"health $id") {
        assertHealthUp(expected)
        Then I print_step(s"step : $id fini")
      }
    }

  }
}
