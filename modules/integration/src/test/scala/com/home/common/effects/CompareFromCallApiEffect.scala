package com.home.common.effects

import cats.data.EitherT
import com.github.agourlay.cornichon.core.Step
import com.github.agourlay.cornichon.steps.regular.EffectStep
import com.home.common.cornichon.{CornichonErrorCustom, CornichonFeatureCustom}
import com.home.common.helpers.Files
import com.home.documents.common.views.DifferencesView
import com.jsonapi.JsonApi
import play.api.libs.json.Json
import sttp.client4.{UriContext, basicRequest, multipart}

import scala.concurrent.Future

trait CompareFromCallApiEffect { self: CornichonFeatureCustom =>

  def compareFileFromDownloadKey(keyFile1: String, keyFile2: String): Step = EffectStep
    .fromEitherT(
      s"compare file from keys ($keyFile1 and $keyFile2)",
      effect = { context =>
        EitherT {

          Future.successful {
            for {
              encodedFile1 <- context
                .session
                .getOpt(keyFile1)
                .toRight(CornichonErrorCustom(s"pas de fichier pour $keyFile1"))
              encodedFile2 <- context
                .session
                .getOpt(keyFile2)
                .toRight(CornichonErrorCustom(s"pas de fichier pour $keyFile2"))

              file1 <- Files.decode(encodedFile1)
              file2 <- Files.decode(encodedFile2)

              response = basicRequest
                .post(uri"$url/diff")
                .multipartBody(
                  multipart("document1", file1).fileName("doc1.pdf").contentType("application/pdf"),
                  multipart("document2", file2).fileName("doc2.pdf").contentType("application/pdf"),
                )
                .send(httpBackend)

              jsonApiResponse <- if (response.code.code != 200) {
                Left(CornichonErrorCustom(s"erreur lors de la comparaison : (${response.code.code})"))
              } else {
                response
                  .body
                  .left
                  .map {_ => CornichonErrorCustom(s"pas de body")}
                  .flatMap { stringifyJson =>
                    Json
                      .parse(stringifyJson)
                      .validate[JsonApi.Single[DifferencesView]]
                      .asEither
                      .left
                      .map { err =>
                        CornichonErrorCustom(err.toString)
                      }
                  }
              }

              _ <- if (!jsonApiResponse.data.attributes.isIdentique) {
                Left(CornichonErrorCustom("les deux fichier ne sont pas identique"))
              } else Right(())



            } yield context.session
          }
        }
      }
    )

}
