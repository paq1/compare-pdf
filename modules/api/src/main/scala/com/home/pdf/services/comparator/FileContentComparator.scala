package com.home.pdf.services.comparator

import com.home.pdf.services.comparator.texts.CanCompareText

final class FileContentComparator extends CanCompareText {

  override def compare(f1: String, f2: String): List[LineDiff] = {
    val l1 = sanitize(f1).split("\n", -1).toVector
    val l2 = sanitize(f2).split("\n", -1).toVector
    val max = math.max(l1.length, l2.length)
    val diffs = (0 until max).flatMap { i =>
      val a = if (i < l1.length) l1(i) else ""
      val b = if (i < l2.length) l2(i) else ""
      if (a != b) Some(LineDiff(i + 1, a, b)) else None
    }
    diffs.toList
  }

  private def sanitize(s: String): String =
    s.replaceAll("\r\n?", "\n")
      .replaceAll("[ \t]+", " ")
      .trim
}
