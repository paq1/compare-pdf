package com.home.app

import com.home.documents.common.PlayDocumentsComponent
import com.play.{LoggingFilter, MetricsComponent}
import play.api.{ApplicationLoader, BuiltInComponentsFromContext, mvc, routing}

class MainComponents(context: ApplicationLoader.Context)
    extends BuiltInComponentsFromContext(context)
    with PlayDocumentsComponent
    with MetricsComponent {

  override def router: routing.Router = new ApiRouter(apiMetrics, pdfCompareController)
  override def httpFilters: List[mvc.EssentialFilter] = List(
    new LoggingFilter()
  )
}
