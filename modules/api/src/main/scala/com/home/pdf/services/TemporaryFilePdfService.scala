package com.home.pdf.services

import com.home.pdf.services.TemporaryFilePdfService.PdfApplicationMime
import com.home.pdf.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import org.apache.pekko.util.ByteString

final class TemporaryFilePdfService extends FilePdfService[FilePartTemporary, ByteString] {

  override def isPdf(file: FilePartTemporary): Boolean =
    file.contentType.contains(PdfApplicationMime)

  override def getContentOpt(file: FilePartTemporary): Option[ByteString] =
    file.refToBytes(file.ref)
}
object TemporaryFilePdfService {
  private val PdfApplicationMime = "application/pdf"
}
