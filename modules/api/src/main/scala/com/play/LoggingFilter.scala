package com.play

import play.api.mvc.{EssentialAction, EssentialFilter}

final class LoggingFilter extends EssentialFilter {

  override def apply(next: EssentialAction): EssentialAction = EssentialAction { requestHeader =>
    println(s">>>>>>>>>> ${requestHeader.method} ${requestHeader.uri}")
    next(requestHeader)
  }
}
