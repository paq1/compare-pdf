package com.home.documents.common.view.parsers

import com.home.documents.common.views.DifferenceView
import play.api.libs.json.{Json, Writes}

object DifferenceViewJson {

  object Implicits {

    implicit val wSchemaDifference: Writes[DifferenceView] = { obj: DifferenceView =>
      Json.obj(
        "left" -> obj.left,
        "right" -> obj.right
      )
    }

  }

}
