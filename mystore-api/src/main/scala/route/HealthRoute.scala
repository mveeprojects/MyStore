package route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import akka.http.scaladsl.server.Route

object HealthRoute {
  val route: Route = concat(
    get {
      path("readiness") {
        complete(StatusCodes.Accepted)
      }
    }
  )
}
