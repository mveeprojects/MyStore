package utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import model.MyProtocols._
import model.Order

import scala.concurrent.{ExecutionContext, Future}

object HttpUtils {

  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext     = actorSystem.dispatcher

  private val apiPort = 8080

  private val baseUrl          = "http://localhost"
  private val ordersBaseUrl    = s"$baseUrl:$apiPort/orders"
  private val inventoryBaseUrl = s"$baseUrl:$apiPort/inventory"
  private val requestHeaders   = List(headers.RawHeader("`Content-Type`", "application/json"))

  def fireGetRequest(userId: String): Future[Seq[Order]] =
    Http()
      .singleRequest(HttpRequest(HttpMethods.GET, s"$ordersBaseUrl/$userId", requestHeaders))
      .flatMap(response => Unmarshal(response).to[Seq[Order]].collect { case orders => orders })

  def firePutRequest(userId: String, orderId: String, quantity: Int): Future[StatusCode] =
    Http()
      .singleRequest(
        HttpRequest(HttpMethods.PUT, s"$ordersBaseUrl/$userId/$orderId/${quantity.toString}", requestHeaders)
      )
      .map(_.status)

  def fireDeleteRequest(userId: String, orderId: String): Future[StatusCode] =
    Http()
      .singleRequest(HttpRequest(HttpMethods.DELETE, s"$ordersBaseUrl/$userId/$orderId", requestHeaders))
      .map(_.status)
}
