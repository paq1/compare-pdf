package com.play.routers

import com.play.controllers.HealthController
import play.api.routing.Router.Routes
import play.api.routing.sird._

final class ApiMetrics(healthController: HealthController) {

  val routes: Routes = {
    case GET(p"/health") => healthController.health()
  }

}
