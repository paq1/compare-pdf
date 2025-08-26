package com.home.common.comparator.files

import com.errors.catsLib.ValidatedErr
import com.home.common.comparator.CanCompare
import com.home.common.data.Differences

trait CanCompareFile[FILE] extends CanCompare[FILE, ValidatedErr[Differences]] {
  def compare(f1: FILE, f2: FILE): ValidatedErr[Differences]
}
