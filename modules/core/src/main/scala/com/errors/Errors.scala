package com.errors

sealed trait Errors {}

final case class Failure(errorCode: ErrorCode, details: List[String]) extends Errors {
  def withDetail(detail: String): Failure = copy(details = details :+ detail)
}

object Failure {
  def of(errorCode: ErrorCode): Failure =
    Failure(errorCode, Nil)
}
