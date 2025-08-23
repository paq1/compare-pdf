package com.home.app

import com.home.pdf.PdfComponent
import com.play.LoggingFilter
import play.api.{ApplicationLoader, BuiltInComponentsFromContext, mvc, routing}

class MainComponents(context: ApplicationLoader.Context)
    extends BuiltInComponentsFromContext(context)
    with PdfComponent {

  override def router: routing.Router = new ApiRouter(healthController, pdfCompareController)
  override def httpFilters: List[mvc.EssentialFilter] = List(
    new LoggingFilter()
  )
}
