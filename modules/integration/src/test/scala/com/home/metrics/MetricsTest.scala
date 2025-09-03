package com.home.metrics

import com.github.agourlay.cornichon.CornichonFeature
import com.github.agourlay.cornichon.core.FeatureDef
import scala.concurrent.ExecutionContext.Implicits.global

class MetricsTest extends CornichonFeature with AssertHealthStep {

  override def feature: FeatureDef = Feature("Call metrics") {

    Scenario("health") {
      assertHealthUp()
    }

  }
}
