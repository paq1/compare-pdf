package com.home.documents.common.view.mappers

import com.home.common.data.Difference
import com.home.documents.common.views.DifferenceView

object DifferenceViewMapper {
  def apply(difference: Difference): DifferenceView = DifferenceView(
    left = difference.left,
    right = difference.right
  )
}
