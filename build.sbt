name := "gatling-circe"

version := "0.1"

scalaVersion := "2.12.8"


val Http4sVersion = "0.20.1"
val CirceVersion = "0.11.1"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"

lazy val api = (project in file("api"))
  .settings(
    scalacOptions ++= Seq("-Ypartial-unification"),
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "ch.qos.logback" % "logback-classic" % LogbackVersion
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6"),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.2.4")
  )


lazy val gatling = (project in file("gatling"))
  .settings(
    libraryDependencies ++= Seq(
      "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.0.0" % "test",
      "io.gatling" % "gatling-test-framework" % "3.0.0" % "test",
      "io.circe" %% "circe-generic" % CirceVersion % "test",
      "io.circe" %% "circe-parser" % CirceVersion % "test",
    )
  ).enablePlugins(GatlingPlugin)
  .dependsOn(api)


scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)