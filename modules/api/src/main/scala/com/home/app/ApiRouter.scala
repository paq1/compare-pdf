package com.home.app

import com.home.documents.common.routers.DocumentsCompareController
import com.play.routers.ApiMetrics
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

final class ApiRouter(
    apiMetrics: ApiMetrics,
    pdfCompareController: DocumentsCompareController
) extends SimpleRouter {

  override def routes: Routes = apiMetrics.routes
    .orElse(diffRoutes)

  private lazy val diffRoutes: Routes = {
    case POST(p"/diff") =>
      pdfCompareController.diffText()
    case GET(p"/download") =>  pdfCompareController.mockDownloadFile()
  }
}
