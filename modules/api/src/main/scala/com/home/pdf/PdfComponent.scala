package com.home.pdf

import com.home.pdf.routers.{HealthController, PdfCompareController}
import com.home.pdf.services.SimpleTexteComparator
import play.api.BuiltInComponentsFromContext

trait PdfComponent { self: BuiltInComponentsFromContext =>

  lazy val pdfCompareController = new PdfCompareController(textCompartor, controllerComponents)
  lazy val healthController = new HealthController(controllerComponents)



  private lazy val textCompartor = new SimpleTexteComparator()
}
