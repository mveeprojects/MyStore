package route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import model.MyProtocols._
import model.{DECREASE, INCREASE}
import service.InventoryService._

object InventoryRoutes {
  private val basePath = "inventory"
  val route: Route = concat(
    get {
      path(basePath / Segment) { itemId =>
        val result = retrieveInventoryItemById(itemId)
        complete(StatusCodes.OK, result)
      }
    },
    put {
      path(basePath / Segment / Segment / Segment) { (itemId, desc, quantity) =>
        addNewItemToInventory(itemId, desc, quantity.toInt)
        complete(StatusCodes.Created, s"$quantity x $desc has been added to the inventory")
      }
    },
    put {
      path(basePath / Segment / Segment) { (itemId, quantity) =>
        updateStockQuantityForItem(itemId, quantity.toInt)
        complete(StatusCodes.OK, s"Quantity for $itemId has been overridden to $quantity in the inventory")
      }
    },
    put {
      path(basePath / Segment / Segment / Segment) { (action, itemId, quantity) =>
        action.toUpperCase match {
          case INCREASE.asString => updateStockQuantityForItem(itemId, quantity.toInt, INCREASE)
          case DECREASE.asString => updateStockQuantityForItem(itemId, quantity.toInt, DECREASE)
          case _ =>
            complete(
              StatusCodes.BadRequest,
              "Make sure the first path parameter is either \"increase\" or \"decrease\""
            )
        }
        complete(StatusCodes.OK, s"Quantity for $itemId has been ${action}d by $quantity in the inventory")
      }
    },
    delete {
      path(basePath / Segment) { itemId =>
        removeInventoryItemFromStock(itemId)
        complete(StatusCodes.NoContent, s"$itemId has been removed from the inventory")
      }
    }
  )
}
