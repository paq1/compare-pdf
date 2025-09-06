package com.home.common.cornichon

import com.typesafe.config.Config

trait CornichonConfig {

  def _config: Config

  lazy val url: String = _config.getString("home.url")
  lazy val endpointDownload: String = _config.getString("home.downloadEndpoint")

}
