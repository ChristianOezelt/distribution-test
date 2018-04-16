package at.christianoezelt.test.cendecen

import akka.actor.{ActorSystem, Props}
import at.christianoezelt.test.cendecen.actor.device.DecentralDeviceManager
import at.christianoezelt.test.cendecen.actor.rest.{OpenEndpoint, RestEndpointActor}
import at.christianoezelt.test.cendecen.rest.DeviceEndpoint

object Main {
  def main(args: Array[String]): Unit = {
    implicit val actorSystem: ActorSystem = ActorSystem("DeviceServiceActorSystem")
    val deviceEndpoint = actorSystem.actorOf(Props[RestEndpointActor], "deviceendpoint")
    val decentralDeviceManager = actorSystem.actorOf(Props[DecentralDeviceManager], "decentraldevicemanager2")

    // configure deviceEndpoint and open connection
    deviceEndpoint ! OpenEndpoint( "localhost", 8080, DeviceEndpoint.endpoint )
  }
}
