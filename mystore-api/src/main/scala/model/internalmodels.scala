package model

import java.time.Instant

case class CustomerOrder(
    userId: String,
    orderId: String,
    itemId: String,
    quantity: Int,
    orderDate: Instant
)

case class Inventory(
    itemId: String,
    description: String,
    quantity: Int
)

trait InventoryQtyAction {
  def asString: String
}

case object INCREASE extends InventoryQtyAction {
  val asString = "INCREASE"
}

case object DECREASE extends InventoryQtyAction {
  val asString = "DECREASE"
}

case object OVERRIDE extends InventoryQtyAction {
  val asString = "OVERRIDE"
}
