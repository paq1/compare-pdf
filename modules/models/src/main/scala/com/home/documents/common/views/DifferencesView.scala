package com.home.documents.common.views

final case class DifferencesView(
    isIdentique: Boolean,
    nombreErreur: Int,
    details: List[DifferenceView]
)
