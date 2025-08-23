package com.home.app

import play.api.Application
import play.api.ApplicationLoader.Context
import play.api.Mode.Dev
import play.core.server.NettyServer


// FIXME : clean le launcher (voir comment c'est fait chez le F)
object Launcher extends App {

  // Création du contexte Play
  val context = Context.create(
    environment = play.api.Environment.simple(mode = Dev)
  )

  // Configuration du serveur (port 9000)
  val application: Application = new LoaderApplicationLoader()
    .load(context)

  val server = NettyServer.fromApplication(application)

  println("Serveur démarré sur http://localhost:9000")
  scala.sys.addShutdownHook {
    println("Arrêt du serveur...")
    server.stop()
  }
}
