package com.play

import com.play.controllers.HealthController
import com.play.routers.ApiMetrics
import play.api.BuiltInComponentsFromContext

trait MetricsComponent { self: BuiltInComponentsFromContext =>

  lazy val apiMetrics: ApiMetrics = new ApiMetrics(healthController)

  private lazy val healthController = new HealthController(controllerComponents)
}
