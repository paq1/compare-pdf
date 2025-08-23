package com.home.app

import play.api.{Application, ApplicationLoader}

class LoaderApplicationLoader extends ApplicationLoader {
  def load(context: ApplicationLoader.Context): Application = {
    new MainComponents(context).application
  }
}