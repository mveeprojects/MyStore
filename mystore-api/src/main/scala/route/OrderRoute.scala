package route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import akka.http.scaladsl.server.Route
import config.AppConfig._
import model.MyProtocols._
import service.InventoryService._
import service.OrderService._
import spray.json._

object OrderRoute {
  private val basePath = "orders"
  val route: Route = concat(
    get {
      path(basePath / Segment) { userId =>
        val result = retrieveAllOrders(userId).map(_.toJson)
        complete(StatusCodes.OK, result)
      }
    },
    put {
      path(basePath / Segment / Segment / Segment) { (userId, itemId, quantity) =>
        addOrderForUser(userId, itemId, quantity.toInt)
        complete(StatusCodes.Created, s"$quantity x $itemId has been added to $userId's orders")
      }
    },
    put {
      path(basePath / Segment / Segment / Segment) { (itemId, desc, quantity) =>
        addNewItemToInventory(itemId, desc, quantity.toInt)
        complete(StatusCodes.Created, s"$quantity x $desc has been added to the inventory")
      }
    },
    delete {
      path(basePath / Segment / Segment) { (userId, orderId) =>
        deleteOrderForUser(userId, orderId)
        complete(StatusCodes.NoContent)
      }
    }
  )
}
