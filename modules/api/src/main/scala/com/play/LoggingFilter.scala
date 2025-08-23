package com.play

import play.api.mvc.{EssentialAction, EssentialFilter}

final class LoggingFilter extends EssentialFilter {

  override def apply(next: EssentialAction): EssentialAction = EssentialAction { request =>
    println(s">>>>>>>>>> ${request.method} ${request.uri}")
    next(request)
  }
}
