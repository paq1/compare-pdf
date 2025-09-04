package com.home.cornichon

import cats.data.Validated.Invalid
import cats.data.{NonEmptyList, ValidatedNel}
import com.github.agourlay.cornichon.core.{CornichonError, Done}
import com.github.agourlay.cornichon.steps.regular.assertStep.Assertion
import com.home.cornichon.FailAssertion.DefaultError

case class FailAssertion (errorMessages: List[String] = Nil) extends Assertion {

  override def validated(): ValidatedNel[CornichonError, Done] = Invalid(
    errorMessagesNonEmptyList
  )

  private def errorMessagesNonEmptyList: NonEmptyList[CornichonErrorCustom] = errorMessages
    .foldLeft(NonEmptyList(DefaultError, Nil)) { (acc, current) =>
      acc :+ CornichonErrorCustom(current)
    }
}
object FailAssertion {
  val DefaultError = CornichonErrorCustom("failed")
}