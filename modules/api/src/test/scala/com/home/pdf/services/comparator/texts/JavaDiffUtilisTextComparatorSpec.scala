package com.home.pdf.services.comparator.texts

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

      comparator.compare(text1, text2) mustBe Nil
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

      comparator.compare(text1, text2).length mustBe 1
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

      comparator.compare(text1, text2).length mustBe 2
    }
  }


  lazy val comparator = new JavaDiffUtilisTextComparator()
}
