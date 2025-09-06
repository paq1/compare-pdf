package com.home.documents.common

import com.home.common.comparator.files.CanCompareFile
import com.home.documents.common.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import com.home.documents.DocumentsLibComponent
import com.home.documents.common.routers.PdfCompareController
import com.home.documents.common.services.comparator.files.FileFromRequestComparator
import com.home.documents.pdf.services.PlayTemporaryFilePdfService
import com.home.pdf.services.FilePdfService
import org.apache.pekko.util.ByteString
import play.api.BuiltInComponentsFromContext

trait PlayDocumentsComponent extends DocumentsLibComponent { self: BuiltInComponentsFromContext =>

  lazy val pdfCompareController = new PdfCompareController(
    fileFromRequestComparator,
    environment,
    controllerComponents
  )

  private lazy val fileFromRequestComparator
      : CanCompareFile[FilePartTemporary] = new FileFromRequestComparator(
    fileComparator,
    temporaryFilePdfService
  )

  private lazy val temporaryFilePdfService
      : FilePdfService[FilePartTemporary, ByteString] =
    new PlayTemporaryFilePdfService()
}
