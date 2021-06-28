import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route.seal
import config.AppConfig._
import kamon.Kamon
import repo.CassandraDB
import route.{HealthRoute, InventoryRoute, OrderRoute}
import utils.Logging

import scala.util.{Failure, Success}

object Main extends App with Logging {

  CassandraDB.init()

  Http()
    .newServerAt(appConfig.http.hostname, appConfig.http.port)
    .bindFlow(seal(concat(OrderRoute.route, InventoryRoute.route, HealthRoute.route)))
    .onComplete {
      case Success(_) =>
        Kamon.init
        logger.info(s"App running (${appConfig.http.hostname}:${appConfig.http.port})")
      case Failure(ex) => logger.error(s"App failed to start:\n${ex.getMessage}")
    }
}
