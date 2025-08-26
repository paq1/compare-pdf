package com.home.common.data

final case class Differences (datas: List[Difference]) extends AnyVal {
  def getNombreErreur: Int = datas.length
  def hasError: Boolean = getNombreErreur == 0
}
