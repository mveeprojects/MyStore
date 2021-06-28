package base

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import utils.DBUtils._

trait ApiIntSpecBase extends IntSpecBase with BeforeAndAfterEach with BeforeAndAfterAll {

  val testUserId  = "userA"
  val testOrderId = "orderA"
  val testItemId  = "itemA"

  override def beforeEach(): Unit = {
    removeOrdersFromDB(testUserId)
    removeInventoryItemFromDB(testItemId)
  }

  override def afterAll(): Unit =
    closeTestCassandraSession()
}
