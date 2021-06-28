package service

import model.Order
import repo.OrderRepository

import java.time.Instant
import java.util.UUID
import scala.concurrent.Future

object OrderService {

  private val orderRepository: OrderRepository = new OrderRepository()

  def retrieveAllOrders(userId: String): Future[List[Order]] =
    orderRepository.selectAllForUser(userId)

  def addOrderForUser(userId: String, itemId: String, quantity: Int): Unit =
    orderRepository.insertOrderForUser(Order(userId, itemId, UUID.randomUUID().toString, quantity, Instant.now()))

  def deleteOrderForUser(userId: String, orderId: String): Unit =
    orderRepository.deleteOrderForUser(userId, orderId)
}
