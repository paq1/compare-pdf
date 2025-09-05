package com.home.cornichon

trait CornichonConfig {

  val url: String = "http://localhost:9000"
  val endpointDownload: String = s"$url/download"

}
