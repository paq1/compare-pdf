package com.home.documents.common.view.parsers

import com.home.documents.common.views.{DifferenceView, DifferencesView}
import play.api.libs.json.{Json, Writes}
import DifferenceViewJson.Implicits._

object DifferencesViewJson {

  object Implicits {

    implicit val wSchemaDifferences: Writes[DifferencesView] = { obj: DifferencesView =>
      Json.obj(
        "isIdentique" -> obj.isIdentique,
        "details" -> obj.details.map((difference: DifferenceView) => Json.toJson(difference)),
        "nombreErreur" -> obj.nombreErreur
      )
    }

  }

}
