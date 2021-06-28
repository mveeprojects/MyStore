package service

import model.CustomerOrder
import repo.OrderRepository

import java.time.Instant
import java.util.UUID
import scala.concurrent.Future

object OrderService {

  private val orderRepository: OrderRepository = new OrderRepository()

  def retrieveAllOrders(userId: String): Future[List[CustomerOrder]] =
    orderRepository.selectAllForUser(userId)

  def addOrderForUser(userId: String, itemId: String, quantity: Int): Unit =
    orderRepository.insertOrderForUser(CustomerOrder(userId, itemId, UUID.randomUUID().toString, quantity, Instant.now))

  def deleteOrderForUser(userId: String, orderId: String): Unit =
    orderRepository.deleteOrderForUser(userId, orderId)
}
