import Aliases.customAliases
import Dependencies._
import Monitoring.kamonSettings

lazy val root = Project("mystore", file("."))
  .aggregate(mystore_api)
  .settings(customAliases)

lazy val mystore_api = Project("mystore-api", file("mystore-api"))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings,  libraryDependencies ++= testDependencies.map(_ % "it,test"))
  .settings(kamonSettings)
  .settings(apiDependencies)
  .enablePlugins(JavaAppPackaging, DockerComposePlugin, JavaAgent)
