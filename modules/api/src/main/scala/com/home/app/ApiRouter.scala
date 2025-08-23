package com.home.app

import com.home.pdf.routers.HelloWorldController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ApiRouter (helloWorldController: HelloWorldController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/helloWorld") => helloWorldController.helloWorld()
  }
}