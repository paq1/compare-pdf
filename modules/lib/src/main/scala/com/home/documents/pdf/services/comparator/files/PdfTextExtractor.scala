package com.home.documents.pdf.services.comparator.files

import cats.data.Validated.{Invalid, Valid}
import com.errors.ErrorCode.InternalServerError
import com.errors.Failure
import com.errors.cats.ValidatedErr
import com.home.common.data.Texte
import com.home.documents.common.services.comparator.files.CanExtractText
import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper

import scala.util.{Success, Try, Failure => FailureTry}

class PdfTextExtractor extends CanExtractText[Array[Byte]] {

  override def extractText(wrapper: Array[Byte]): ValidatedErr[Texte] =
    Try {
      val doc = Loader.loadPDF(wrapper)
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
