package com.home.documents.pdf.services.comparator.files

import cats.implicits._
import com.errors.cats.ValidatedErr
import com.home.common.comparator.files.CanCompareFile
import com.home.common.comparator.texts.CanCompareText
import com.home.common.data.Differences
import com.home.documents.common.services.comparator.files.CanExtractText

final class PdfFileComparator(
    textExtractor: CanExtractText[Array[Byte]],
    textComparator: CanCompareText
) extends CanCompareFile[Array[Byte]] {

  override def compare(
      f1: Array[Byte],
      f2: Array[Byte]
  ): ValidatedErr[Differences] = {
    (for {
      text1 <- textExtractor.extractText(f1).toEither
      text2 <- textExtractor.extractText(f2).toEither
    } yield textComparator.compare(text1, text2)).toValidated
  }
}
