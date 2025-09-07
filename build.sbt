import Dependencies.*

val baseName = "compare-documents"

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
  .dependsOn(lib)
  .dependsOn(models)
  .enablePlugins(JavaAppPackaging, UniversalPlugin)
  .settings(
    name := s"$baseName-api",
    libraryDependencies ++= Seq(
      playNetty,
      play,
      scalatestPlay % Test
    )
  )
  .settings(commonSettings)


lazy val lib = (project in file("modules/lib"))
  .dependsOn(core)
  .settings(
    name := s"$baseName-lib",
    libraryDependencies ++= Seq(
      pdfbox,
      javaDiffUtils,
    )
  )
  .settings(commonSettings)


lazy val core = (project in file("modules/core"))
  .settings(
    name := s"$baseName-core",
    libraryDependencies ++= Seq(
      cats
    )
  )
  .settings(commonSettings)

lazy val models = (project in file("modules/models"))
  .settings(
    name := s"$baseName-models",
    libraryDependencies ++= Seq(
      cats,
      playJsonParser
    )
  )
  .settings(commonSettings)


lazy val integration = (project in file("modules/integration"))
  .dependsOn(models)
  .settings(
    name := s"$baseName-api",
    libraryDependencies ++= Seq(
      sttp,
      cornichon % Test
    ),
    testFrameworks += cornichonTestFramework
  )
  .settings(commonSettings)