package com.home.pdf.services.comparator

trait CanCompareFile[FILE] {
  def compare(f1: FILE, f2: FILE): List[LineDiff]
}
