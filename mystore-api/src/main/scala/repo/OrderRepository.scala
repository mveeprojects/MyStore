package repo

import config.AppConfig.executionContext
import config.DBConfig._
import model.Order

import scala.concurrent.Future

class OrderRepository {
  import quillDB._

  def selectAllForUser(userId: String): Future[List[Order]] =
    quillDB.run(quote {
      query[Order].filter(_.userId.equals(lift(userId)))
    })

  def selectFirstNForUser(userId: String, numberOfRecords: Int): Future[List[Order]] =
    selectAllForUser(userId).collect { case orders: List[Order] =>
      orders.take(numberOfRecords)
    }

  def insertOrderForUser(order: Order): Future[Unit] =
    quillDB.run(quote {
      query[Order].insert(lift(order))
    })

  def deleteOrderForUser(userId: String, orderId: String): Future[Unit] =
    quillDB.run(quote {
      query[Order]
        .filter(_.userId.equals(lift(userId)))
        .filter(_.orderId.equals(lift(orderId)))
        .delete
    })
}
