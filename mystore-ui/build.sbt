scalaVersion                           := "2.13.1"
scalaJSUseMainModuleInitializer        := true
dockerImageCreationTask                := (Docker / publishLocal).value
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.2.0"
