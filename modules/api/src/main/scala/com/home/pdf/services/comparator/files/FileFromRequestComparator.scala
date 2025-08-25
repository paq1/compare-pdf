package com.home.pdf.services.comparator.files

import cats.data.Validated.Invalid
import com.errors.catsLib.ValidatedErr
import com.errors.{ErrorCode, Failure}
import com.home.pdf.services.comparator.LineDiff
import com.home.pdf.services.comparator.files.FileFromRequestComparator.{
  FilePartTemporary,
  isPdf
}
import org.apache.pekko.util.ByteString
import play.api.libs.Files
import play.api.mvc.MultipartFormData

class FileFromRequestComparator(
    fileComparator: CanCompareFile[ByteString]
) extends CanCompareFile[FilePartTemporary] {

  override def compare(
      pdf1: FilePartTemporary,
      pdf2: FilePartTemporary
  ): ValidatedErr[List[LineDiff]] = {

    if (!isPdf(pdf1, pdf2)) {
      Invalid(
        Failure
          .of(ErrorCode.UnsupportedMediaType())
          .withDetail("les deux fichiers doivent être des pdf")
      )
    } else {
      (for {
        bsText1 <- pdf1.refToBytes(pdf1.ref)
        bsText2 <- pdf2.refToBytes(pdf2.ref)
      } yield {
        fileComparator
          .compare(bsText1, bsText2)
      })
        .getOrElse(
          Invalid(
            Failure
              .of(ErrorCode.InternalServerError())
              .withDetail(
                "une erreur est survenue lors de la transformation en ByteString des fichier pdf1 et/ou pdf2"
              )
          )
        )
    }

  }
}
object FileFromRequestComparator {
  type FilePartTemporary =
    MultipartFormData.FilePart[Files.TemporaryFile]

  private def isPdf(
      file1: FilePartTemporary,
      file2: FilePartTemporary
  ): Boolean = isPdf(file1) && isPdf(file2)

  private def isPdf(
      file1: FilePartTemporary
  ): Boolean = {
    file1.contentType.contains(PdfApplicationMime)
  }

  private val PdfApplicationMime = "application/pdf"
}
