package com.home.pdf.views

import com.home.common.data.Differences
import com.jsonapi.JsonApi
import play.api.libs.json.{Json, Writes}

final case class DifferencesView(
    isIdentique: Boolean,
    nombreErreur: Int,
    details: List[DifferenceView]
)
object DifferencesView {

  def intoSingleJsonApi(differences: Differences): JsonApi.Single[DifferencesView] =
    JsonApi.Single(
      data = JsonApi.Data(
        `type` = "differences",
        id = "1",
        attributes = DifferencesView.apply(differences)
      )
    )

  def apply(differences: Differences): DifferencesView = DifferencesView(
    isIdentique = differences.hasError,
    nombreErreur = differences.getNombreErreur,
    details = differences.datas.map(DifferenceView.apply)
  )

  implicit val wSchema: Writes[DifferencesView] = { obj: DifferencesView =>
    Json.obj(
      "isIdentique" -> obj.isIdentique,
      "details" -> obj.details,
      "nombreErreur" -> obj.nombreErreur
    )
  }
}
