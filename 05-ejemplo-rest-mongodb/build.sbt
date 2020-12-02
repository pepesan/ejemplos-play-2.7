name := """05-ejemplo-rest-mongo"""
organization := "com.example"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

scalaVersion := "2.12.8"

PlayKeys.devSettings := Seq("play.server.http.port" -> "9003")

resolvers += "Maven Central Server" at "https://repo1.maven.org/maven2"

resolvers += "Typesafe Server" at "https://repo.typesafe.com/typesafe/releases"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.2"
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "4.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.1",
  "mysql" % "mysql-connector-java" % "8.0.16",
)
libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.13-play27"
)
libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo-play-json" % "0.20.13-play27"
)
play.sbt.routes.RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"
routesGenerator := InjectedRoutesGenerator


libraryDependencies += "org.webjars" % "swagger-ui" % "2.2.0"

swaggerDomainNameSpaces := Seq("models")