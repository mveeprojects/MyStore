package route

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import base.IntSpecBase
import config.TestConfig.testConf._
import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import utils.HttpUtils._

import scala.concurrent.Future

class HealthRouteSpec extends IntSpecBase {

  override implicit val patienceConfig: PatienceConfig =
    PatienceConfig(patience.timeout, patience.interval)

  "Health route" - {
    "when the readiness endpoint is called" - {
      "should return a 202" - {
        eventually {
          val getReadinessResult: Future[HttpResponse] = fireGetReadinessRequest
          getReadinessResult.futureValue.status shouldBe StatusCodes.Accepted
        }
      }
    }
  }
}
