package com.home.pdf

import com.home.pdf.routers.PdfCompareController
import com.home.pdf.services.{FilePdfService, TemporaryFilePdfService}
import com.home.pdf.services.comparator.LineDiff
import com.home.pdf.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import com.home.pdf.services.comparator.files.{
  CanCompareFile,
  FileComparator,
  FileFromRequestComparator
}
import com.home.pdf.services.comparator.texts.{
  CanCompareText,
  JavaDiffUtilisTextComparator
}
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
    new FileComparator(fileContentCompartor)
  private lazy val fileContentCompartor: CanCompareText[List[LineDiff]] =
    new JavaDiffUtilisTextComparator()

  private lazy val temporaryFilePdfService
      : FilePdfService[FilePartTemporary, ByteString] =
    new TemporaryFilePdfService()
}
