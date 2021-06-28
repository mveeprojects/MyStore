package base

import org.scalatest.concurrent.Eventually
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import utils.DBUtils.{closeTestCassandraSession, removeOrdersFromDB}
import utils.Logging

trait FuncSpecBase
    extends AnyFreeSpec
    with Matchers
    with Eventually
    with BeforeAndAfterEach
    with BeforeAndAfterAll
    with Logging {

  val testUserId  = "userA"
  val testOrderId = "videoA"

  override def beforeEach(): Unit =
    removeOrdersFromDB(testUserId)

  override def afterAll(): Unit =
    closeTestCassandraSession()
}
