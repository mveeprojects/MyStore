package repo

import config.AppConfig.executionContext
import config.DBConfig._
import model.CustomerOrder

import scala.concurrent.Future

class OrderRepository {
  import quillDB._

  def selectAllForUser(userId: String): Future[List[CustomerOrder]] =
    quillDB.run(quote {
      query[CustomerOrder].filter(_.userId.equals(lift(userId)))
    })

  def selectFirstNForUser(userId: String, numberOfRecords: Int): Future[List[CustomerOrder]] =
    selectAllForUser(userId).collect { case orders: List[CustomerOrder] =>
      orders.take(numberOfRecords)
    }

  def insertOrderForUser(order: CustomerOrder): Future[Unit] =
    quillDB.run(quote {
      query[CustomerOrder].insert(lift(order))
    })

  def deleteOrderForUser(userId: String, orderId: String): Future[Unit] =
    quillDB.run(quote {
      query[CustomerOrder]
        .filter(_.userId.equals(lift(userId)))
        .filter(_.orderId.equals(lift(orderId)))
        .delete
    })
}
