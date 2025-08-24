package com.home.pdf.services.comparator.texts

import com.home.pdf.services.comparator.{CanCompare, LineDiff}

trait CanCompareText extends CanCompare[String, List[LineDiff]] {

}
