package com.home.pdf.services.comparator.texts

import com.github.difflib.DiffUtils
import com.github.difflib.patch.{AbstractDelta, Chunk}
import com.home.pdf.services.comparator.LineDiff

import scala.jdk.CollectionConverters._

class JavaDiffUtilisTextComparator extends CanCompareText[List[LineDiff]] {

  override def compare(a: String, b: String): List[LineDiff] = {
    val l1 = sanitize(a).split("\n").toList
    val l2 = sanitize(b).split("\n").toList

    val diff = DiffUtils.diff(l1.asJava, l2.asJava)
    diff.getDeltas.asScala.map { abstractDelta: AbstractDelta[String] =>
      val left: Chunk[String] = abstractDelta.getSource
      val right = abstractDelta.getTarget

      val leftText = left.getLines.asScala.mkString.trim
      val rightText = right.getLines.asScala.mkString.trim

      LineDiff(left.getPosition, leftText, rightText)
    }.toList

  }

  private def sanitize(s: String): String =
    s.replaceAll("\r\n?", "\n")
      .replaceAll("[ \t]+", " ")
      .trim
}
