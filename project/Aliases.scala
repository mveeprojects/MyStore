import sbt.{Def, _}

object Aliases {
  val customAliases: Seq[Def.Setting[State => State]] = {
    addCommandAlias("runAllTests", "dockerComposeUp; test; dockerComposeStop") ++
      addCommandAlias("dockerUp", "project mystore-api; dockerComposeUp") ++
      addCommandAlias("dockerDown", "project mystore-api; dockerComposeStop") ++
      addCommandAlias("dockerRestart", "dockerDown; dockerUp") ++
      addCommandAlias("dockerIntTests", "dockerRestart; IntegrationTest/test; dockerDown")
  }
}
