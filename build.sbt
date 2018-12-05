name := "learning-http4s"

version := "0.1"

scalaVersion := "2.12.7"

val http4sVersion = "0.18.21"

libraryDependencies ++= Seq(
    "org.http4s"        %% "http4s-blaze-server"    % http4sVersion,
    "org.http4s"        %% "http4s-blaze-client"    % http4sVersion,
    "org.http4s"        %% "http4s-circe"           % http4sVersion,
    "org.http4s"        %% "http4s-dsl"             % http4sVersion,
    "org.scalactic"     %% "scalactic"              % "3.0.5",
    "org.scalatest"     %% "scalatest"              % "3.0.5"           % "test"
)

addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")

scalacOptions += "-Ypartial-unification"