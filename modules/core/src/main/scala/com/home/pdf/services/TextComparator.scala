package com.home.pdf.services


/**
 *
 */
trait TextComparator {

  /** Compare ligne par ligne, renvoie (equal, diffs) */
  def compareLineByLine(t1: String, t2: String): (Boolean, Seq[LineDiff]) = {
    val l1 = normalize(t1).split("\n", -1).toVector
    val l2 = normalize(t2).split("\n", -1).toVector
    val max = math.max(l1.length, l2.length)
    val diffs = (0 until max).flatMap { i =>
      val a = if (i < l1.length) l1(i) else ""
      val b = if (i < l2.length) l2(i) else ""
      if (a != b) Some(LineDiff(i + 1, a, b)) else None
    }
    (diffs.isEmpty, diffs)
  }

  /** Petites normalisations pour limiter les faux positifs */
  private def normalize(s: String): String =
    s.replaceAll("\r\n?", "\n")     // CRLF -> LF
      .replaceAll("[ \t]+", " ")     // compresser espaces/tabs
      .trim

}
