package com.home.pdf

import com.home.common.comparator.files.CanCompareFile
import com.home.common.comparator.texts.CanCompareText
import com.home.pdf.routers.PdfCompareController
import com.home.pdf.services.{FilePdfService, TemporaryFilePdfService}
import com.home.pdf.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import com.home.pdf.services.comparator.files.{
  CanExtractText,
  FileComparator,
  FileFromRequestComparator,
  PdfTextExtractor
}
import com.home.pdf.services.comparator.texts.JavaDiffUtilisTextComparator
import org.apache.pekko.util.ByteString
import play.api.BuiltInComponentsFromContext

trait PdfComponent { self: BuiltInComponentsFromContext =>

  lazy val pdfCompareController = new PdfCompareController(
    fileFromRequestComparator,
    controllerComponents
  )

  private lazy val fileFromRequestComparator
      : CanCompareFile[FilePartTemporary] = new FileFromRequestComparator(
    fileComparator,
    temporaryFilePdfService
  )
  private lazy val fileComparator: CanCompareFile[ByteString] =
    new FileComparator(textExtractor, fileContentCompartor)

  private lazy val textExtractor: CanExtractText[ByteString] =
    new PdfTextExtractor()
  private lazy val fileContentCompartor: CanCompareText =
    new JavaDiffUtilisTextComparator()

  private lazy val temporaryFilePdfService
      : FilePdfService[FilePartTemporary, ByteString] =
    new TemporaryFilePdfService()
}
