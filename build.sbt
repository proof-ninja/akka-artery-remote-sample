ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "2.13.10"

val AkkaVersion = "2.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "akka-artery-remote-sample"
  )

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.4.2",
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-remote" % AkkaVersion
)