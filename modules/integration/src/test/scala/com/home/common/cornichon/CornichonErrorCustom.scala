package com.home.common.cornichon

import com.github.agourlay.cornichon.core.CornichonError

final case class CornichonErrorCustom (override val baseErrorMessage: String) extends CornichonError {}
