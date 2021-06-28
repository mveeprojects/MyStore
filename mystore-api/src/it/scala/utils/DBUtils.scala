package utils

import com.datastax.driver.core.Cluster
import io.getquill.{CamelCase, CassandraAsyncContext}
import model.Order

import java.time.Instant
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object DBUtils {

  private lazy val testQuillCluster = Cluster
    .builder()
    .addContactPoint("localhost")
    .withoutJMXReporting
    .build()

  private val testQuillDB = new CassandraAsyncContext(CamelCase, testQuillCluster, "mystore", 100)

  import testQuillDB._

  def insertOrderIntoDB(userId: String, orderId: String): Future[Unit] =
    testQuillDB.run(quote {
      query[Order].insert(lift(Order(userId, orderId, "123", 1, Instant.now())))
    })

  def removeOrdersFromDB(userId: String): Future[Unit] =
    testQuillDB.run(quote {
      query[Order]
        .filter(_.userId.equals(lift(userId)))
        .delete
    })

  def closeTestCassandraSession(): Unit = {
    testQuillDB.close()
  }
}
