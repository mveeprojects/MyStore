package route

import base.ApiIntSpecBase
import config.TestConfig.testConf._
import model.Inventory
import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import utils.HttpUtils._

class InventoryRouteSpec extends ApiIntSpecBase {

  override implicit val patienceConfig: PatienceConfig =
    PatienceConfig(patience.timeout, patience.interval)

  "Inventory routes" - {
    "when GET is called for am itemId that does not exist" - {
      "should return an empty list of orders" in {
        eventually {
          val eventualInventories = fireGetInventoryRequest(testItemId)
          eventualInventories.futureValue shouldBe Seq.empty[Inventory]
        }
      }
    }
  }
}
