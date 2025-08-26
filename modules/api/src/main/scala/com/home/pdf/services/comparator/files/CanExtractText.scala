package com.home.pdf.services.comparator.files

import com.errors.catsLib.ValidatedErr
import com.home.common.data.Texte

trait CanExtractText[Wrapper] {

  def extractText(wrapper: Wrapper): ValidatedErr[Texte]

}
