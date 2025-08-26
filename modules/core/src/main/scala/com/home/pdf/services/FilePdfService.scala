package com.home.pdf.services

trait FilePdfService[FILE, CONTENT] {
  def isPdf(file: FILE): Boolean
  def getContentOpt(file: FILE): Option[CONTENT]
}
