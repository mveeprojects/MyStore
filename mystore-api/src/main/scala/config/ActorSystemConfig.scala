package config

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

trait ActorSystemConfig {
  implicit val system: ActorSystem                = ActorSystem("mystore-api-actor-system")
  implicit val executionContext: ExecutionContext = system.dispatcher
}
