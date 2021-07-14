import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route.seal
import config.AppConfig._
import kamon.Kamon
import repo.CassandraDB
import repo.CassandraDB.closeDBSession
import route.{HealthRoute, InventoryRoute, OrderRoute}
import utils.Logging

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Main extends App with Logging {

  Future.fromTry(CassandraDB.init).onComplete {
    case Success(_) =>
      logger.info("DB initialisation completed successfully.")
      closeDBSession()
      startServer()
    case Failure(exception) =>
      logger.error(s"Exception thrown during DB initialisation => ${exception.getMessage}")
      closeDBSession()
  }

  private def startServer(): Unit = Http()
    .newServerAt(appConfig.http.hostname, appConfig.http.port)
    .bindFlow(seal(concat(OrderRoute.route, InventoryRoute.route, HealthRoute.route)))
    .onComplete {
      case Success(_) =>
        Kamon.init
        logger.info(s"App running (${appConfig.http.hostname}:${appConfig.http.port})")
      case Failure(ex) =>
        logger.error(s"App failed to start:\n${ex.getMessage}")
        sys.exit(1)
    }
}
