package com.home.common.effects

import cats.data.EitherT
import com.github.agourlay.cornichon.core.Step
import com.github.agourlay.cornichon.steps.regular.EffectStep
import com.home.common.cornichon.CornichonFeatureCustom
import sttp.client4.{UriContext, asByteArray, quickRequest}

import java.util.Base64
import scala.concurrent.Future

trait DownloadFileEffect { self: CornichonFeatureCustom =>

  def downloadFile(keyEncodedFile: String): Step =
    EffectStep
      .fromEitherT(
        s"download file from $endpointDownload",
        effect = { context =>
          EitherT {
            val result = quickRequest
              .get(uri"$endpointDownload")
              .response(asByteArray)
              .send(httpBackend)

            val byteArray = result.body
              .map { bytes =>
                println("downloaded")
                bytes
              }
              .getOrElse(Array.emptyByteArray)

            val encoded: String = Base64.getEncoder.encodeToString(byteArray)

            Future.successful(
              context.session.addValue(keyEncodedFile, encoded)
            )
          }
        }
      )

}
