name := """slick3play"""

organization := "com.pedrorijo91"
version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

PlayKeys.devSettings := Seq("play.server.http.port" -> "9003")

resolvers += "Maven Central Server" at "https://repo1.maven.org/maven2"

resolvers += "Typesafe Server" at "https://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
  "com.h2database" % "h2" % "1.4.200" // replace `${H2_VERSION}` with an actual version number
  //"mysql" % "mysql-connector-java" % "8.0.15",
)