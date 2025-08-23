package com.home.pdf

import com.home.pdf.routers.HelloWorldController
import play.api.BuiltInComponentsFromContext

trait PdfComponent { self: BuiltInComponentsFromContext =>
  lazy val helloWorldController = new HelloWorldController(controllerComponents)
}
