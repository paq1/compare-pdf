package com.home.documents.pdf.services

import com.home.documents.common.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import PlayTemporaryFilePdfService.PdfApplicationMime
import com.home.pdf.services.FilePdfService
import org.apache.pekko.util.ByteString

final class PlayTemporaryFilePdfService extends FilePdfService[FilePartTemporary, ByteString] {

  override def isPdf(file: FilePartTemporary): Boolean =
    file.contentType.contains(PdfApplicationMime)

  override def getContentOpt(file: FilePartTemporary): Option[ByteString] =
    file.refToBytes(file.ref)
}
object PlayTemporaryFilePdfService {
  private val PdfApplicationMime = "application/pdf"
}
