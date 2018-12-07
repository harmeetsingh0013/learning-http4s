name := "learning-http4s"

version := "0.1"

scalaVersion := "2.12.7"

val http4sVersion = "0.18.21"
val scalaTest = "3.0.5"
val circe = "0.9.3"
val doobie = "0.5.2"

libraryDependencies ++= Seq(
    "org.http4s"                    %% "http4s-blaze-server"    % http4sVersion,
    "org.http4s"                    %% "http4s-blaze-client"    % http4sVersion,
    "org.http4s"                    %% "http4s-circe"           % http4sVersion,
    "org.http4s"                    %% "http4s-dsl"             % http4sVersion,
    
    "org.scalactic"                 %% "scalactic"              % scalaTest,
    "org.scalatest"                 %% "scalatest"              % scalaTest          % "test",
    
    "io.monix"                      %% "monix"                  % "3.0.0-RC1",
    
    "org.http4s"                    %% "http4s-circe"           % http4sVersion,
    "io.circe"                      %% "circe-generic"          % circe,
    "io.circe"                      %% "circe-literal"          % circe,
    
    "com.github.pureconfig"         %% "pureconfig"             % "0.10.1",
    
    "org.tpolecat"                  %% "doobie-core"            % doobie,
    "org.tpolecat"                  %% "doobie-h2"              % doobie,
    "org.tpolecat"                  %% "doobie-hikari"          % doobie,

    "com.h2database"                %  "h2"                     % "1.4.192"
)

addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

scalacOptions += "-Ypartial-unification"