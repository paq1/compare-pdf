package com.home.compareFile

import com.github.agourlay.cornichon.core.{FeatureDef, Scenario}
import com.home.common.cornichon.CornichonFeatureCustom
import com.home.common.effects.{CompareFromCallApiEffect, DownloadFileEffect}

final class CompareFileTest extends CornichonFeatureCustom with DownloadFileEffect with CompareFromCallApiEffect {

  override def feature: FeatureDef = Feature("Call metrics") {
    List(
      scenarioFichierIdentique,
      scenarioFichierDifferent,
    )
  }

  private def scenarioFichierIdentique: Scenario = {
    val downloadKey = "downloadKey"
    Scenario("comparaison en success de deux meme fichier") {
      When I downloadFile(downloadKey)
      // Then I print_step(s"téléchargement de : <$downloadKey>")
      // TODO : comparer deux memes fichiers ensembles
      Then I compareFromCall(downloadKey, downloadKey)
      Then I print_step(s"step fini")
    }
  }

  private def scenarioFichierDifferent: Scenario =
    Scenario("comparaison en erreur car deux fichiers differents") {
      When I downloadFile("downloadKey")
      // Then I print_step(s"téléchargement de : <$downloadKey>")
      // TODO : comparer deux fichiers différents ensembles
      Then I print_step(s"step fini")
    }
}
