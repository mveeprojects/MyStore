package service

import model.{DECREASE, INCREASE, Inventory, InventoryQtyAction}
import repo.InventoryRepository

import scala.concurrent.Future

object InventoryService {

  private val inventoryRepository: InventoryRepository = new InventoryRepository()

  def retrieveInventoryItemById(itemId: String): Future[List[Inventory]] =
    inventoryRepository.selectByItemId(itemId)

  def addNewItemToInventory(itemId: String, description: String, quantity: Int): Future[Unit] = {
    val inventoryItem = Inventory(itemId, description, quantity)
    inventoryRepository.insertItemToInventory(inventoryItem)
  }

  def updateStockQuantityForItem(itemId: String, quantity: Int): Future[Unit] =
    inventoryRepository.updateInventoryQuantityForItem(itemId, quantity)

  def updateStockQuantityForItem(itemId: String, quantity: Int, action: InventoryQtyAction): Future[Unit] =
    inventoryRepository.updateInventoryQuantityForItem(itemId, quantity, action)

  def removeInventoryItemFromStock(itemId: String): Future[Unit] =
    inventoryRepository.removeItemFromInventory(itemId)
}
