package com.home.pdf.services.comparator.texts

import com.home.common.data.Texte
import com.home.documents.common.services.comparator.texts.JavaDiffUtilisTextComparator
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class JavaDiffUtilisTextComparatorSpec extends AnyWordSpec with Matchers {
  "JavaDiffUtilisTextComparator compare" should {
    "list empty when is same" in {
      val text1 =
        """
          |voici un simple text
          |
          |    il y a un espace
          |
          |    voici mes garantie:
          |        DM : 30 euros
          |        RCA : 50 euros
          |        PJMO : 20 euros
          |""".stripMargin
      val text2 =
        """
          |voici un simple text
          |
          |    il y a un espace
          |
          |    voici mes garantie:
          |        DM : 30 euros
          |        RCA : 50 euros
          |        PJMO : 20 euros
          |""".stripMargin

      comparator.compare(Texte(text1), Texte(text2)).datas mustBe Nil
    }

    "list with 1 delta when is not same at one place" in {
      val text1 =
        """
          |voici un simple text
          |
          |    il y a un espace
          |
          |    voici mes garantie:
          |        DM : 30 euros
          |        RCA : 50 euros
          |        PJMO : 20 euros
          |""".stripMargin
      val text2 =
        """
          |voici un simple text
          |
          |    il y a un espace
          |
          |    voici mes garantie:
          |        DM : 30 euros
          |        RCA : 50 euros
          |        je met nimp
          |        et le result,  at doit etre faux
          |        PJMO : 20 euros
          |""".stripMargin

      comparator.compare(Texte(text1), Texte(text2)).getNombreErreur mustBe 1
    }

    "list with 2 deltas when is not same at 2 places" in {
      val text1 =
        """
          |voici un simple text
          |
          |    il y a un espace
          |
          |    voici mes garantie:
          |        DM : 30 euros
          |        RCA : 50 euros
          |        PJMO : 20 euros
          |""".stripMargin
      val text2 =
        """
          |voici un simple text
          |
          |    il y a un espace et un petit bruit ici
          |
          |    voici mes garantie:
          |        DM : 30 euros
          |        RCA : 50 euros
          |        je met nimp
          |        et le result,  at doit etre faux
          |        PJMO : 20 euros
          |""".stripMargin

      comparator.compare(Texte(text1), Texte(text2)).getNombreErreur mustBe 2
    }

    "readable content of delta" in {
      val text1 =
        """
          |voici un simple text
          |
          |
          |    il y a un espace
          |
          |    voici mes garantie:
          |        DM : 30 euros
          |        RCA : 50 euros
          |        PJMO : 20 euros
          |""".stripMargin
      val text2 =
        """
          |voici un simple text
          |
          |
          |    il y a un espace et un petit bruit ici
          |
          |    voici mes garantie:
          |        DM : 30 euros
          |        RCA : 50 euros
          |        PJMO : 20 euros
          |""".stripMargin

      val result = comparator.compare(Texte(text1), Texte(text2)).datas.head
      result.left mustBe "il y a un espace"
      result.right mustBe "il y a un espace et un petit bruit ici"
    }
  }


  lazy val comparator = new JavaDiffUtilisTextComparator()
}
