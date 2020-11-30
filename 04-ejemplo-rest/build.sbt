name := """04-ejemplo-rest"""
organization := "com.example"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

scalaVersion := "2.12.8"


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

libraryDependencies += "org.webjars" % "swagger-ui" % "2.2.0"

swaggerDomainNameSpaces := Seq("models")