package com.home.pdf.services.comparator.files

import cats.data.Validated.{Invalid, Valid}
import com.errors.ErrorCode.InternalServerError
import com.errors.Failure
import com.errors.catsLib.ValidatedErr
import com.home.pdf.services.comparator.LineDiff
import com.home.pdf.services.comparator.files.FileComparator.extractPdfText
import com.home.pdf.services.comparator.texts.CanCompareText
import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.pekko.util.ByteString

import scala.util.{Success, Try, Failure => FailureTry}

class FileComparator(
    textComparator: CanCompareText[List[LineDiff]]
) extends CanCompareFile[ByteString] {

  override def compare(
      f1: ByteString,
      f2: ByteString
  ): ValidatedErr[List[LineDiff]] = {
    extractPdfText(f1, f2)
      .map { case (text1, text2) =>
        textComparator.compare(text1, text2)
      }
  }
}
object FileComparator {

  private def extractPdfText(
      f1: ByteString,
      f2: ByteString
  ): ValidatedErr[(String, String)] = extractPdfText(f1.toArray)
    .andThen { text1 =>
      extractPdfText(f2.toArray)
        .map(text1 -> _)
    }

  private def extractPdfText(bytes: Array[Byte]): ValidatedErr[String] = {
    Try {
      val doc = Loader.loadPDF(bytes)
      val stripper = new PDFTextStripper()
      val text = stripper.getText(doc)
      doc.close()
      text
    } match {
      case FailureTry(exception) =>
        Invalid(Failure.of(InternalServerError()))
      case Success(value) =>
        Valid(value)
    }
  }
}
