package com.home.pdf.services.comparator.files

import com.errors.catsLib.ValidatedErr
import com.home.pdf.services.comparator.{CanCompare, LineDiff}

trait CanCompareFile[FILE] extends CanCompare[FILE, ValidatedErr[List[LineDiff]]] {
  def compare(f1: FILE, f2: FILE): ValidatedErr[List[LineDiff]]
}
