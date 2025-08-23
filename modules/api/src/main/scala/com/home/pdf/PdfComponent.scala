package com.home.pdf

import com.home.pdf.routers.{HelloWorldController, PdfCompareController}
import com.home.pdf.services.SimpleTexteComparator
import play.api.BuiltInComponentsFromContext

trait PdfComponent { self: BuiltInComponentsFromContext =>

  lazy val pdfCompareController = new PdfCompareController(textCompartor, controllerComponents)
  lazy val helloWorldController = new HelloWorldController(controllerComponents)



  private lazy val textCompartor = new SimpleTexteComparator()
}
