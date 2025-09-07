package com.home.documents.common.views

import play.api.libs.json.{Json, Writes}

final case class DifferenceView(left: String, right: String)
object DifferenceView {
  implicit val wSchema: Writes[DifferenceView] = { obj: DifferenceView =>
    Json.obj(
      "left" -> obj.left,
      "right" -> obj.right
    )
  }
}
