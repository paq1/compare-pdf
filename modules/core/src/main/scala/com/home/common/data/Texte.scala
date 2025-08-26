package com.home.common.data

final case class Texte(underlying: String) extends AnyVal {

  def getSplitedContent: List[String] = sanitize
    .split("\n")
    .toList

  private def sanitize: String =
    underlying
      .replaceAll("\r\n?", "\n")
      .replaceAll("[ \t]+", " ")
      .trim

}
