package com.home.common.services.comparator.files

import com.errors.cats.ValidatedErr
import com.home.common.data.Texte

trait CanExtractText[Wrapper] {

  def extractText(wrapper: Wrapper): ValidatedErr[Texte]

}
