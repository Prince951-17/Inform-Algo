ThisBuild / organization := "org.informalgo"
ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version      := "1.0"

lazy val `Inform-Algo` = (project in file("."))
  .aggregate(server, client, shared.jvm, shared.js)

lazy val server = project
  .settings(
    scalaJSProjects := Seq(client),
    Assets / pipelineStages  := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    // triggers scalaJSPipeline when using compile or continuous compilation
    Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Dependencies.server ++ Seq(guice)
  )
  .enablePlugins(PlayScala)
  .dependsOn(shared.jvm)

lazy val client = project
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "1.1.0",
      "org.querki" %%% "jquery-facade" % "2.0"
    ),
    jsDependencies += "org.webjars" % "jquery" % "3.1.0" / "3.1.0/jquery.js" minified "jquery.min.js"
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb, JSDependenciesPlugin)
  .dependsOn(shared.js)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .jsConfigure(_.enablePlugins(ScalaJSWeb))

Global / onLoad := (Global / onLoad).value.andThen(state => "project server" :: state)
