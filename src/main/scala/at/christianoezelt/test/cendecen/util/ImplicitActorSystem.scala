package at.christianoezelt.test.cendecen.util

import akka.actor.{Actor, ActorSystem}
import akka.stream.ActorMaterializer

trait ImplicitActorSystem {
  this: Actor =>
  lazy implicit val actorSystem: ActorSystem = context.system
}
