package com.home.documents.common.views

import play.api.libs.json.{JsValue, Json, Reads, Writes}

final case class DifferenceView(left: String, right: String)
object DifferenceView {
  implicit val wSchema: Writes[DifferenceView] = { obj: DifferenceView =>
    Json.obj(
      "left" -> obj.left,
      "right" -> obj.right
    )
  }

  implicit val rSchema: Reads[DifferenceView] = { js: JsValue =>
    for {
      left <- (js \ "left").validate[String]
      right <-  (js \ "right").validate[String]
    } yield {
      DifferenceView(left, right)
    }
  }
}
