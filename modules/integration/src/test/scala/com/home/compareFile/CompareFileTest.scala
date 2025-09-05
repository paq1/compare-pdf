package com.home.compareFile

import cats.data.EitherT
import com.github.agourlay.cornichon.CornichonFeature
import com.github.agourlay.cornichon.core.FeatureDef
import com.github.agourlay.cornichon.steps.regular.EffectStep
import com.home.cornichon.CornichonConfig
import sttp.client4.httpurlconnection.HttpURLConnectionBackend
import sttp.client4.{UriContext, asByteArray, quickRequest}

import java.util.Base64
import scala.concurrent.Future

class CompareFileTest
    extends CornichonFeature
    with CornichonConfig
    with DownloadFileStep {

  override def feature: FeatureDef = Feature("Call metrics") {

    (0 to 1).map { id =>
      Scenario(s"comparaison numero $id") {

        AttachAs("telechargement du document") {
          downloadFile("pouet")
          Then I print_step(s"téléchargement de : <pouet>")
        }

        Then I print_step(s"step : $id fini")
      }
    }

  }
}
