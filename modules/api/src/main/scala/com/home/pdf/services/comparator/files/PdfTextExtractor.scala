package com.home.pdf.services.comparator.files

import cats.data.Validated.{Invalid, Valid}
import com.errors.ErrorCode.InternalServerError
import com.errors.Failure
import com.errors.catsLib.ValidatedErr
import com.home.common.data.Texte
import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.pekko.util.ByteString

import scala.util.{Success, Try, Failure => FailureTry}

class PdfTextExtractor extends CanExtractText[ByteString] {

  override def extractText(wrapper: ByteString): ValidatedErr[Texte] =
    Try {
      val doc = Loader.loadPDF(wrapper.toArray)
      val stripper = new PDFTextStripper()
      val text = stripper.getText(doc)
      doc.close()
      text
    } match {
      case FailureTry(_) =>
        Invalid(Failure.of(InternalServerError()))
      case Success(value) =>
        Valid(Texte(value))
    }

}
