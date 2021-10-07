import Aliases.customAliases
import Dependencies._
import Monitoring.kamonSettings

scalaVersion := "2.13.1"

lazy val root = Project("mystore", file("."))
  .aggregate(mystore_api, mystore_ui)
  .settings(customAliases)

lazy val mystore_api = Project("mystore-api", file("mystore-api"))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings,  libraryDependencies ++= testDependencies.map(_ % "it,test"))
  .settings(kamonSettings)
  .settings(apiDependencies)
  .enablePlugins(JavaAppPackaging, DockerComposePlugin, JavaAgent)

lazy val mystore_ui = Project("mystore-ui", file("mystore-ui"))
//  .settings(uiDependencies)
  .enablePlugins(ScalaJSPlugin)
