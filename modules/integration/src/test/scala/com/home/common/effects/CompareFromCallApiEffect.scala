package com.home.common.effects

import cats.data.EitherT
import com.github.agourlay.cornichon.core.Step
import com.github.agourlay.cornichon.steps.regular.EffectStep
import com.home.common.cornichon.{CornichonErrorCustom, CornichonFeatureCustom}
import com.home.common.helpers.Files
import sttp.client4.{UriContext, basicRequest, multipart}

import scala.concurrent.Future

trait CompareFromCallApiEffect { self: CornichonFeatureCustom =>

  def compareFromCall(keyFile1: String, keyFile2: String): Step = EffectStep
    .fromEitherT(
      s"download file from $endpointDownload",
      effect = { context =>
        EitherT {


          val monObjet = for {
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

            monObjetAanalyser <- if (response.code.code != 200) {
              Left(CornichonErrorCustom(s"erreur lors de la comparaison : (${response.code.code})"))
            } else {
              response
                .body
                .map { _ =>
                  "mon objet"
                }
                .left
                .map(e => CornichonErrorCustom(e))
            }

            // TODO : serialize objet to view
            // TODO : check qu'il n'y ai pas de différence
            // TODO : remonter une erreur en cas de différence

          } yield monObjetAanalyser


          Future.successful(monObjet.map(_ => context.session))
        }
      }
    )

}
