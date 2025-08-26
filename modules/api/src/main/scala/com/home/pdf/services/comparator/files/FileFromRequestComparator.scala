package com.home.pdf.services.comparator.files

import cats.data.Validated.Invalid
import com.errors.catsLib.ValidatedErr
import com.errors.{ErrorCode, Failure}
import com.home.common.comparator.files.CanCompareFile
import com.home.common.data.Differences
import com.home.pdf.services.FilePdfService
import com.home.pdf.services.comparator.files.FileFromRequestComparator.FilePartTemporary
import org.apache.pekko.util.ByteString
import play.api.libs.Files
import play.api.mvc.MultipartFormData

class FileFromRequestComparator(
    fileComparator: CanCompareFile[ByteString],
    filePdfService: FilePdfService[FilePartTemporary, ByteString]
) extends CanCompareFile[FilePartTemporary] {

  override def compare(
      pdf1: FilePartTemporary,
      pdf2: FilePartTemporary
  ): ValidatedErr[Differences] = {

    if (!isPdf(pdf1, pdf2)) {
      Invalid(
        Failure
          .of(ErrorCode.UnsupportedMediaType())
          .withDetail("les deux fichiers doivent être des pdf")
      )
    } else {
      (for {
        byteStringPdf1 <- filePdfService.getContentOpt(pdf1)
        byteStringPdf2 <- filePdfService.getContentOpt(pdf2)
      } yield {
        fileComparator
          .compare(byteStringPdf1, byteStringPdf2)
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

  private def isPdf(
      file1: FilePartTemporary,
      file2: FilePartTemporary
  ): Boolean = filePdfService.isPdf(file1) && filePdfService.isPdf(file2)

}
object FileFromRequestComparator {
  type FilePartTemporary =
    MultipartFormData.FilePart[Files.TemporaryFile]
}
