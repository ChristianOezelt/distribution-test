package at.christianoezelt.test.cendecen.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Flow

trait RestEndpoint {
  def endpoint(implicit actorSystem: ActorSystem): Route
}
