package com.home.pdf.services.comparator.texts

import com.github.difflib.DiffUtils

import scala.jdk.CollectionConverters._

class JavaDiffUtilisTextComparator extends CanCompareText[List[String]] {

  override def compare(a: String, b: String): List[String] = {
    val l1 = sanitize(a).split("\n").toList
    val l2 = sanitize(b).split("\n").toList

    val diff = DiffUtils.diff(l1.asJava, l2.asJava)
    diff.getDeltas.asScala.map { abstractDelta =>
      abstractDelta.toString
    }
      .toList

  }


  private def sanitize(s: String): String =
    s.replaceAll("\r\n?", "\n")
      .replaceAll("[ \t]+", " ")
      .trim
}
