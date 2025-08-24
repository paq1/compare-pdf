val baseName = "compare-pdf"

name := baseName
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.16"


lazy val root = (project in file("."))
  .aggregate(core, api)
  .settings(
    publish / skip := true
  )

// Plugins communs
lazy val commonSettings = Seq(
  scalaVersion := "2.13.16",
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
  ThisBuild / organization := "com.example",
  ThisBuild / version := "0.1.0-SNAPSHOT"
)

lazy val api = (project in file("modules/api"))
  .dependsOn(core)
  .settings(
    name := s"$baseName-api",
    libraryDependencies ++= Seq(
      "org.apache.pdfbox" % "pdfbox" % "3.0.5",
      "io.github.java-diff-utils" % "java-diff-utils" % "4.16",
      "org.playframework" %% "play-netty-server" % "3.0.8",
      "org.playframework" %% "play-server" % "3.0.8",
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test
    )
  )
  .settings(commonSettings)


lazy val core = (project in file("modules/core"))
  .settings(
    name := s"$baseName-core",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.13.0"
    )
  )
  .settings(commonSettings)
