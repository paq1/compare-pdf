package com.home.pdf.services.comparator.files

import cats.implicits._
import com.errors.catsLib.ValidatedErr
import com.home.common.comparator.files.CanCompareFile
import com.home.common.comparator.texts.CanCompareText
import com.home.common.data.Differences
import org.apache.pekko.util.ByteString

class FileComparator(
    textExtractor: CanExtractText[ByteString],
    textComparator: CanCompareText
) extends CanCompareFile[ByteString] {

  override def compare(
      f1: ByteString,
      f2: ByteString
  ): ValidatedErr[Differences] = {
    (for {
      text1 <- textExtractor.extractText(f1).toEither
      text2 <- textExtractor.extractText(f2).toEither
    } yield textComparator.compare(text1, text2)).toValidated
  }
}
