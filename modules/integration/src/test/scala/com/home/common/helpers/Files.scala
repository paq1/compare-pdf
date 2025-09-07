package com.home.common.helpers

import com.home.common.cornichon.CornichonErrorCustom

import java.util.Base64
import scala.util.Try

object Files {

  def encode(byteArray: Array[Byte]): String = Base64.getEncoder.encodeToString(byteArray)
  def decode(base64: String): Either[CornichonErrorCustom, Array[Byte]] = {
    Try {
      Base64.getDecoder.decode(base64)
    }
      .toEither
      .left.map(th => CornichonErrorCustom(th.getMessage))
  }

}
