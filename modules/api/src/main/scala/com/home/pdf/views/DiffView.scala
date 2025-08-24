package com.home.pdf.views

import com.home.pdf.services.comparator.LineDiff
import com.jsonapi.JsonApi
import play.api.libs.json.{Json, Writes}

final case class DiffView(
    isIdentique: Boolean,
    nombreErreur: Int,
    details: List[LineDiffView]
)
object DiffView {

  def intoSingleJsonApi(differences: List[LineDiff]): JsonApi.Single[DiffView] =
    JsonApi.Single(
      data = JsonApi.Data(
        `type` = "comparaison",
        id = "whatever",
        attributes = DiffView.apply(differences)
      )
    )

  def apply(differences: List[LineDiff]): DiffView = DiffView(
    isIdentique = differences.isEmpty,
    nombreErreur = differences.length,
    details = differences.map(LineDiffView.apply)
  )

  implicit val wSchema: Writes[DiffView] = { obj: DiffView =>
    Json.obj(
      "isIdentique" -> obj.isIdentique,
      "details" -> obj.details,
      "nombreErreur" -> obj.nombreErreur
    )
  }
}
