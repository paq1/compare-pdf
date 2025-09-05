package com.home.compareFile

import cats.data.EitherT
import com.github.agourlay.cornichon.CornichonFeature
import com.github.agourlay.cornichon.core.Step
import com.github.agourlay.cornichon.steps.regular.EffectStep
import com.home.cornichon.CornichonConfig
import sttp.client4.httpurlconnection.HttpURLConnectionBackend
import sttp.client4.{UriContext, asByteArray, quickRequest}

import java.util.Base64
import scala.concurrent.Future

trait DownloadFileStep { self: CornichonFeature with CornichonConfig =>
  def downloadFile(key: String = "downloadFile"): Step = {

    AttachAs("download file") {

      EffectStep
        .fromEitherT("download file", effect = { context =>
          EitherT {
            val backend = HttpURLConnectionBackend()

            val result = quickRequest
              .get(uri"$endpointDownload")
              .response(asByteArray)
              .send(backend)

            val byteArray = result
              .body
              .map { bytes =>
                println("downloaded")
                bytes
              }
              .getOrElse(Array.emptyByteArray)

            val encoded: String = Base64.getEncoder.encodeToString(byteArray)


            Future.successful(
              context.session.addValue(key, encoded)
            )
          }
        })

    }

  }
}
