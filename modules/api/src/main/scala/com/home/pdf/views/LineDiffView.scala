package com.home.pdf.views

import com.home.pdf.services.comparator.LineDiff
import play.api.libs.json.{Json, Writes}

final case class LineDiffView (left: String, right: String)
object LineDiffView {

  def apply(lineDiff: LineDiff): LineDiffView = LineDiffView(
    left = lineDiff.left,
    right = lineDiff.right
  )

  implicit val wSchema: Writes[LineDiffView] = { obj: LineDiffView =>
    Json.obj(
      "left" -> obj.left,
      "right" -> obj.right
    )
  }

}
