package com.home.common.comparator

trait CanCompare[IN, OUT] {
  def compare(a: IN, b: IN): OUT
}
