package at.christianoezelt.test.cendecen.actor.rest

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.{Directive, Route}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import at.christianoezelt.test.cendecen.util.ImplicitActorSystem

import scala.concurrent.Future

sealed trait RestEndpointActorCommand
case class OpenEndpoint(hostname: String, port: Int, routes: Route ) extends RestEndpointActorCommand
case class CloseEndpoint() extends RestEndpointActorCommand

/**
  * This Actor manages a http endpoint on instance level
  */
class RestEndpointActor extends Actor with ActorLogging {
  override def receive: Receive = initial orElse unknownMessageHandler
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()

  val initial: Receive = {
    case OpenEndpoint( hostname, port, routes ) =>

      val bindingFuture = Http()(context.system).bindAndHandle(routes, hostname, port)
      context become ( openServer( bindingFuture ) orElse unknownMessageHandler )
  }

  val openServer: ( Future[ServerBinding] ) => Receive = ( binding ) => {
    case CloseEndpoint() =>
      import context.dispatcher
      binding flatMap { _.unbind } onComplete {
        _ => context become initial
      }
  }

  val unknownMessageHandler: Receive = {
    case msg @ _ =>
      log.warning(s"Received unintended message ${msg.getClass.getName}")
      log.debug(msg.toString)

  }
}