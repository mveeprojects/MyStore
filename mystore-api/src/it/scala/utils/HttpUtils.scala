package utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import model.MyProtocols._
import model.{CustomerOrder, Inventory}

import scala.concurrent.{ExecutionContext, Future}

object HttpUtils {

  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext     = actorSystem.dispatcher

  private val apiPort = 8080

  private val baseUrl          = s"http://localhost:$apiPort"
  private val ordersBaseUrl    = s"$baseUrl/orders"
  private val inventoryBaseUrl = s"$baseUrl/inventory"
  private val requestHeaders   = List(headers.RawHeader("`Content-Type`", "application/json"))

  def fireGetOrdersRequest(userId: String): Future[Seq[CustomerOrder]] =
    Http()
      .singleRequest(HttpRequest(HttpMethods.GET, s"$ordersBaseUrl/$userId", requestHeaders))
      .flatMap(response => Unmarshal(response).to[Seq[CustomerOrder]].collect { case orders => orders })

  def fireGetInventoryRequest(itemId: String): Future[Seq[Inventory]] =
    Http()
      .singleRequest(HttpRequest(HttpMethods.GET, s"$inventoryBaseUrl/$itemId", requestHeaders))
      .flatMap(response => Unmarshal(response).to[Seq[Inventory]].collect { case inventory => inventory })

  def fireGetReadinessRequest: Future[HttpResponse] = Http()
    .singleRequest(HttpRequest(HttpMethods.GET, s"$baseUrl/readiness"))

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
