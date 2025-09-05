package com.home.metrics

import com.github.agourlay.cornichon.CornichonFeature
import com.github.agourlay.cornichon.core.{Config, FeatureDef}

import scala.concurrent.ExecutionContext.Implicits.global

class MetricsTest extends CornichonFeature with AssertHealthStep {

  override def feature: FeatureDef = Feature("Call metrics") {

    (0 to  1).map { id =>
      val errors = (0 to 500)
      val expected = if (errors.contains(id)) "up" // FIXME : mettre down
      else "up"
      Scenario(s"health $id") {
        assertHealthUp(expected)
        Then I print_step(s"step : $id fini")
      }
    }

  }
}
