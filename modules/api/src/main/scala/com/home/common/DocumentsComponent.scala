package com.home.common

import com.home.common.comparator.files.CanCompareFile
import com.home.common.comparator.texts.CanCompareText
import com.home.common.routers.PdfCompareController
import com.home.common.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import com.home.common.services.comparator.files.{CanExtractText, FileFromRequestComparator}
import com.home.common.services.comparator.texts.JavaDiffUtilisTextComparator
import com.home.pdf.services.{FilePdfService, TemporaryFilePdfService}
import com.home.pdf.services.comparator.files.{PdfFileComparator, PdfTextExtractor}
import org.apache.pekko.util.ByteString
import play.api.BuiltInComponentsFromContext

trait DocumentsComponent { self: BuiltInComponentsFromContext =>

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
    new PdfFileComparator(textExtractor, fileContentCompartor)

  private lazy val textExtractor: CanExtractText[ByteString] =
    new PdfTextExtractor()
  private lazy val fileContentCompartor: CanCompareText =
    new JavaDiffUtilisTextComparator()

  private lazy val temporaryFilePdfService
      : FilePdfService[FilePartTemporary, ByteString] =
    new TemporaryFilePdfService()
}
