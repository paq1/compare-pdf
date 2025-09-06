package com.home.documents

import com.home.common.comparator.files.CanCompareFile
import com.home.common.comparator.texts.CanCompareText
import com.home.documents.common.services.comparator.files.CanExtractText
import com.home.documents.common.services.comparator.texts.JavaDiffUtilisTextComparator
import com.home.documents.pdf.services.comparator.files.{
  PdfFileComparator,
  PdfTextExtractor
}

trait DocumentsLibComponent {

  lazy val fileComparator: CanCompareFile[Array[Byte]] =
    new PdfFileComparator(textExtractor, fileContentCompartor)



  private lazy val textExtractor: CanExtractText[Array[Byte]] =
    new PdfTextExtractor()
  private lazy val fileContentCompartor: CanCompareText =
    new JavaDiffUtilisTextComparator()

}
