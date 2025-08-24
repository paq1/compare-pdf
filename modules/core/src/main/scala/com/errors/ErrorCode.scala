package com.errors

sealed trait ErrorCode {
  def httpCode: Int
  def title: String
}

object ErrorCode {
  final case class InternalServerError(
      httpCode: Int = 500,
      title: String = "internal server error, reessayer plus tard"
  ) extends ErrorCode
  final case class BadRequest(
      httpCode: Int = 400,
      title: String = "bad request!"
  ) extends ErrorCode
  final case class NotFound(
      httpCode: Int = 404,
      title: String = "bad request!"
  ) extends ErrorCode
  final case class UnsupportedMediaType(
      httpCode: Int = 415,
      title: String = "format de fichier non supporté"
  )
}
