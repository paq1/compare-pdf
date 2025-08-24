package com.errors.cats

import cats.data.Validated.{Invalid, Valid}
import com.errors.catsLib.ValidatedErr
import com.errors.{ErrorCode, Failure}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.Result

object Implicits extends ImplicitsFailureJson {

  implicit class JsonSerialisationValidated[VIEW](validatedErr: ValidatedErr[VIEW]) {

    def intoResult(
        expectedSuccessHttpCode: Int
    )(implicit wSchema: Writes[VIEW]): Result = {
      (validatedErr match {
        case Valid(a)                  => handleSuccess(expectedSuccessHttpCode, a)
        case Invalid(failure: Failure) => failure.intoResult
        case _                         => Invalid(Failure.of(ErrorCode.InternalServerError()))
      })
        .getOrElse(
          defaultResultError
        )
    }

    private def handleSuccess[VIEW](expectedCode: Int, view: VIEW)(implicit
        wSchema: Writes[VIEW]
    ): ValidatedErr[Result] = {
      val jsView = Json.toJson(view)
      expectedCode match {
        case 201 => Valid(Created(jsView))
        case _   => Valid(Ok(jsView))
      }

    }

    private val defaultResultError = InternalServerError(
      Json.toJson(Failure.of(ErrorCode.InternalServerError()))
    )

  }

}
