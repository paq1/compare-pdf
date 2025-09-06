package com.home.common.cornichon

import com.github.agourlay.cornichon.CornichonFeature
import sttp.client4.SyncBackend
import sttp.client4.httpurlconnection.HttpURLConnectionBackend

abstract class CornichonFeatureCustom
    extends CornichonFeature
    with CornichonConfig
    with ConfigDsl {

  lazy val httpBackend: SyncBackend = HttpURLConnectionBackend()

}
