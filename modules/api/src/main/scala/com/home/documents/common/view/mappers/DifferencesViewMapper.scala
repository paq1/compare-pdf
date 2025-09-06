package com.home.documents.common.view.mappers

import com.home.common.data.Differences
import com.home.documents.common.views.DifferencesView
import com.jsonapi.JsonApi

object DifferencesViewMapper {
  def apply(differences: Differences): DifferencesView = DifferencesView(
    isIdentique = differences.hasError,
    nombreErreur = differences.getNombreErreur,
    details = differences.datas.map(DifferenceViewMapper.apply)
  )

  def intoSingleJsonApi(differences: Differences): JsonApi.Single[DifferencesView] = JsonApi.Single(
    data = JsonApi.Data(
      `type` = "differences",
      id = "1",
      attributes = apply(differences)
    )
  )
}
