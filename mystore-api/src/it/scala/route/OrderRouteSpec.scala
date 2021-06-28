package route

import akka.http.scaladsl.model.StatusCodes
import base.ApiIntSpecBase
import config.TestConfig.testConf._
import model.CustomerOrder
import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import utils.DBUtils._
import utils.HttpUtils._

import scala.concurrent.Future

class OrderRouteSpec extends ApiIntSpecBase {

  override implicit val patienceConfig: PatienceConfig =
    PatienceConfig(patience.timeout, patience.interval)

  "Order routes" - {
    "when GET is called for a userId that does not exist" - {
      "should return an empty list of orders" in {
        eventually {
          val getResult: Future[Seq[CustomerOrder]] = fireGetOrdersRequest(testUserId)
          getResult.futureValue shouldBe Seq.empty[CustomerOrder]
        }
      }
    }

    "when GET is called for a userId that already exists" - {
      "should return a list of orders for that user" in {
        insertOrderIntoDB(testUserId, testOrderId)
        eventually {
          val getResult: Future[Seq[CustomerOrder]] = fireGetOrdersRequest(testUserId)
          val orders: Seq[CustomerOrder]            = getResult.futureValue

          orders.length shouldBe 1
          orders.head.userId shouldBe testUserId
          orders.head.orderId shouldBe testOrderId
        }
      }
    }

    "after PUT is called to add an order for a user" - {
      "calling GET should return a single-element list of orders" in {
        eventually {
          val putResult = firePutRequest(testUserId, testOrderId, 1)
          putResult.futureValue shouldBe StatusCodes.Created

          val getResult                  = fireGetOrdersRequest(testUserId)
          val orders: Seq[CustomerOrder] = getResult.futureValue
          orders.length shouldBe 1
          orders.head.userId shouldBe testUserId
          orders.head.orderId shouldBe testOrderId
          orders.head.quantity shouldBe 1
        }
      }
    }

    "after calling DELETE for a order that exists for a user" - {
      "calling GET should return a list of orders without the DELETE'd order" in {
        insertOrderIntoDB(testUserId, testOrderId)
        eventually {
          val deleteResult = fireDeleteRequest(testUserId, testOrderId)
          deleteResult.futureValue shouldBe StatusCodes.NoContent

          val getResult: Future[Seq[CustomerOrder]] = fireGetOrdersRequest(testUserId)
          getResult.futureValue shouldBe Seq.empty[CustomerOrder]
        }
      }
    }
  }
}
