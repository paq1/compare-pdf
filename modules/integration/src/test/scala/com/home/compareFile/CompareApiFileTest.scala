package com.home.compareFile

import com.github.agourlay.cornichon.core.{FeatureDef, Scenario}
import com.home.common.cornichon.CornichonFeatureCustom
import com.home.common.effects.{CompareFromCallApiEffect, DownloadFileEffect}

final class CompareApiFileTest extends CornichonFeatureCustom with DownloadFileEffect with CompareFromCallApiEffect {

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
      Then I compareFileFromDownloadKey(downloadKey, downloadKey)
      Then I print_step(s"step fini")
    }
  }

  private def scenarioFichierDifferent: Scenario = {
    // TODO : comparer deux fichiers différents ensembles
    val downloadKey1 = "downloadKey"
    val downloadKey2 = "downloadKey"
    Scenario("comparaison en erreur car deux fichiers differents") {
      When I downloadFile(downloadKey1)
      And I downloadFile(downloadKey2)
      Then I compareFileFromDownloadKey(downloadKey1, downloadKey2)
      Then I print_step(s"step fini")
    }
  }
}
