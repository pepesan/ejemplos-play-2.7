name := """05-ejemplo-play-28"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

PlayKeys.devSettings := Seq("play.server.http.port" -> "9003")

scalaVersion := "2.12.8"

resolvers += "Maven Central Server" at "https://repo1.maven.org/maven2"

resolvers += "Typesafe Server" at "https://repo.typesafe.com/typesafe/releases"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies += "org.reactivemongo" % "play2-reactivemongo_2.12" % "1.0.1-play28"

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo-play-json" % "1.0.1-play28"
)
play.sbt.routes.RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"
routesGenerator := InjectedRoutesGenerator

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
