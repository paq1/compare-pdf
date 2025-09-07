package com.home.documents.common.views

import play.api.libs.json.{JsValue, Json, Reads, Writes}

final case class DifferencesView(
    isIdentique: Boolean,
    nombreErreur: Int,
    details: List[DifferenceView]
)
object DifferencesView {
  implicit val wSchema: Writes[DifferencesView] = { obj: DifferencesView =>
    Json.obj(
      "isIdentique" -> obj.isIdentique,
      "details" -> obj.details.map((difference: DifferenceView) =>
        Json.toJson(difference)
      ),
      "nombreErreur" -> obj.nombreErreur
    )
  }

  implicit val rSchema: Reads[DifferencesView] = { js: JsValue =>
    for {
      isIdentique <- (js \ "isIdentique").validate[Boolean]
      nombreErreur <- (js \ "nombreErreur").validate[Int]
      details <- (js \ "details").validate[List[DifferenceView]]
    } yield {
      DifferencesView(isIdentique, nombreErreur, details)
    }
  }
}
