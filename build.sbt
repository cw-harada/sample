name := """play-hands-on"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  jdbc,
  guice,
  //"org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  //"com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.play" %% "play-slick" %  "3.0.3",
  "com.typesafe.slick" %% "slick-codegen" % "3.2.1",
  "mysql" % "mysql-connector-java" % "6.0.6",
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

fork := true // required for "sbt run" to pick up javaOptions
