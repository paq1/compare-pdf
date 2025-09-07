package com.home.documents.common.views

import play.api.libs.json.{Json, Writes}

final case class DifferencesView(
    isIdentique: Boolean,
    nombreErreur: Int,
    details: List[DifferenceView]
)
object DifferencesView {
  implicit val wSchema: Writes[DifferencesView] = { obj: DifferencesView =>
    Json.obj(
      "isIdentique" -> obj.isIdentique,
      "details" -> obj.details.map((difference: DifferenceView) => Json.toJson(difference)),
      "nombreErreur" -> obj.nombreErreur
    )
  }
}
