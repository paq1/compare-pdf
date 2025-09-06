package com.errors.cats

import cats.data.Validated.{Invalid, Valid}
import com.errors.ErrorCode.InternalServerError
import com.errors.cats.ValidatedErr
import com.errors.{ErrorCode, Failure}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Result, Results}

private[cats] trait ImplicitsFailureJson extends Results {
  implicit class JsonSerialisation(failure: Failure) {
    def intoResult: ValidatedErr[Result] = {
      failure match {
        case Failure(_: InternalServerError, _) =>
          Valid(InternalServerError(Json.toJson(failure)))
        case Failure(_: ErrorCode.BadRequest, _) =>
          Valid(BadRequest(Json.toJson(failure)))
        case Failure(_: ErrorCode.NotFound, _) =>
          Valid(NotFound(Json.toJson(failure)))
        case Failure(_: ErrorCode.UnsupportedMediaType, _) =>
          Valid(UnsupportedMediaType(Json.toJson(failure)))
        case _ => Invalid(Failure.of(ErrorCode.InternalServerError()))
      }
    }
  }

  implicit val wSchemaFailure: Writes[Failure] = { obj =>
    Json.obj(
      "error" -> Json.obj(
        "title" -> obj.errorCode.title,
        "codeHttp" -> obj.errorCode.httpCode,
        "details" -> obj.details
      )
    )
  }
}
