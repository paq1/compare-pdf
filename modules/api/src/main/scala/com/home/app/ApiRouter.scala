package com.home.app

import com.home.pdf.routers.{HealthController, PdfCompareController}
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ApiRouter(
    healthController: HealthController,
    pdfCompareController: PdfCompareController
) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/health") => healthController.health()
    case POST(p"/diff")  => pdfCompareController.diffTextPdf()
  }
}
