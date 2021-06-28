import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route.seal
import config.AppConfig._
import repo.CassandraDB
import akka.http.scaladsl.server.Directives._
import route.{InventoryRoutes, OrderRoutes}
import utils.Logging
import kamon.Kamon

import scala.util.{Failure, Success}

object Main extends App with Logging {

  CassandraDB.init()

  Http()
    .newServerAt(appConfig.http.hostname, appConfig.http.port)
    .bindFlow(seal(concat(OrderRoutes.route, InventoryRoutes.route)))
    .onComplete {
      case Success(_) =>
        Kamon.init
        logger.info(s"App running (${appConfig.http.hostname}:${appConfig.http.port})")
      case Failure(ex) => logger.error(s"App failed to start:\n${ex.getMessage}")
    }
}
