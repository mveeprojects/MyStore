package repo

import config.AppConfig.executionContext
import config.DBConfig._
import model._

import scala.concurrent.Future

class InventoryRepository {
  import quillDB._

  def selectByItemId(itemId: String): Future[List[Inventory]] =
    quillDB.run(quote {
      query[Inventory].filter(_.itemId.equals(lift(itemId)))
    })

  def insertItemToInventory(inventoryItem: Inventory): Future[Unit] =
    quillDB.run(quote {
      query[Inventory].insert(lift(inventoryItem))
    })

  def updateInventoryQuantityForItem(itemId: String, qty: Int): Future[Unit] =
    updateInventoryQuantityForItem(itemId, qty, OVERRIDE)

  def updateInventoryQuantityForItem(itemId: String, qty: Int, action: InventoryQtyAction): Future[Unit] =
    generateUpdatedInventoryItem(itemId, qty, action).map { newInvItem =>
      quillDB.run(quote {
        query[Inventory]
          .filter(_.itemId == lift(itemId))
          .update(lift(newInvItem))
      })
    }

  def removeItemFromInventory(itemId: String): Future[Unit] =
    quillDB.run(quote {
      query[Inventory]
        .filter(_.itemId.equals(lift(itemId)))
        .delete
    })

  private def generateUpdatedInventoryItem(itemId: String, qty: Int, action: InventoryQtyAction): Future[Inventory] =
    for {
      item: Inventory <- selectByItemId(itemId).map(_.head)
      currentQty = item.quantity
      updatedItem = action match {
        case INCREASE => item.copy(quantity = currentQty + qty)
        case DECREASE => item.copy(quantity = currentQty - qty)
        case OVERRIDE => item.copy(quantity = qty)
      }
    } yield updatedItem
}
