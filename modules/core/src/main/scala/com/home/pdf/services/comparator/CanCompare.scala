package com.home.pdf.services.comparator

trait CanCompare[IN, OUT] {
  def compare(a: IN, b: IN): OUT
}
