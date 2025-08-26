package com.home.pdf.services.comparator.texts

import com.github.difflib.DiffUtils
import com.github.difflib.patch.{AbstractDelta, Chunk}
import com.home.common.comparator.texts.CanCompareText
import com.home.common.data.{Difference, Differences, Texte}
import com.home.pdf.services.comparator.texts.JavaDiffUtilisTextComparator.fromDeltaToDifference

import scala.jdk.CollectionConverters._

class JavaDiffUtilisTextComparator extends CanCompareText {

  override def compare(texte1: Texte, texte2: Texte): Differences =
    Differences(
      DiffUtils
        .diff(texte1.getSplitedContent.asJava, texte2.getSplitedContent.asJava)
        .getDeltas
        .asScala
        .map(fromDeltaToDifference)
        .toList
    )

}
object JavaDiffUtilisTextComparator {
  private def fromDeltaToDifference(
      delta: AbstractDelta[String]
  ): Difference = {
    val left: Chunk[String] = delta.getSource
    val right: Chunk[String] = delta.getTarget

    val leftText = left.getLines.asScala.mkString.trim
    val rightText = right.getLines.asScala.mkString.trim

    Difference(left = leftText, right = rightText)
  }
}
