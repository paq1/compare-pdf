import sbt.*

object Dependencies {

  // common
  val cats = "org.typelevel" %% "cats-core" % "2.13.0"

  // specific for this project
  val pdfbox = "org.apache.pdfbox" % "pdfbox" % "3.0.5"
  val javaDiffUtils = "io.github.java-diff-utils" % "java-diff-utils" % "4.16"

  // play
  val play = "org.playframework" %% "play-server" % "3.0.8"
  val playNetty = "org.playframework" %% "play-netty-server" % "3.0.8"
  val scalatestPlay = "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2"
  val playJsonParser = "org.playframework" %% "play-json" % "3.0.5"

  // it
  val sttp = "com.softwaremill.sttp.client4" %% "async-http-client-backend" % "4.0.0-M20"
  val cornichon = "com.github.agourlay" %% "cornichon-test-framework" % "0.22.1"
  val cornichonTestFramework = new TestFramework("com.github.agourlay.cornichon.framework.CornichonFramework")
}
