package com.home.pdf

import com.home.pdf.routers.PdfCompareController
import com.home.pdf.services.comparator.LineDiff
import com.home.pdf.services.comparator.texts.{CanCompareText, JavaDiffUtilisTextComparator}
import play.api.BuiltInComponentsFromContext

trait PdfComponent { self: BuiltInComponentsFromContext =>

  lazy val pdfCompareController = new PdfCompareController(fileContentCompartor, controllerComponents)

  private lazy val fileContentCompartor: CanCompareText[List[LineDiff]] = new JavaDiffUtilisTextComparator()
}
