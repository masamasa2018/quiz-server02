name := """quiz-server"""
organization := "com.masahiro"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",  // このバージョンは適宜最新にしてください
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",

)
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.26" exclude("mysql", "mysql-connector-java")

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
libraryDependencies += filters


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.masahiro.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.masahiro.binders._"
