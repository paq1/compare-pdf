package com.home.common.views

import com.home.common.data.Difference
import play.api.libs.json.{Json, Writes}

final case class DifferenceView(left: String, right: String)
object DifferenceView {

  def apply(difference: Difference): DifferenceView = DifferenceView(
    left = difference.left,
    right = difference.right
  )

  implicit val wSchema: Writes[DifferenceView] = { obj: DifferenceView =>
    Json.obj(
      "left" -> obj.left,
      "right" -> obj.right
    )
  }

}
