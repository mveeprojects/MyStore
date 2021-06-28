package model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{deserializationError, DefaultJsonProtocol, JsString, JsValue, RootJsonFormat}

import java.time.Instant

object MyProtocols extends SprayJsonSupport with DefaultJsonProtocol {

  implicit object InstantJsonFormat extends RootJsonFormat[Instant] {
    def write(instant: Instant): JsString = JsString(instant.toString)
    def read(value: JsValue): Instant = value match {
      case JsString(i) => Instant.parse(i)
      case x           => deserializationError("Expected Instant as JsString, but got " + x)
    }
  }

  implicit val orderFormat: RootJsonFormat[Order] = jsonFormat5(Order)
  implicit val inventoryFormat: RootJsonFormat[Inventory] = jsonFormat3(Inventory)
}
