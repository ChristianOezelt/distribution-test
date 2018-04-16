package at.christianoezelt.test.cendecen.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import at.christianoezelt.test.cendecen.actor.device.ConfigurePrinter
import at.christianoezelt.test.cendecen.model.device.PrinterConnectionDetails

import at.christianoezelt.test.cendecen.model.device.PrinterConnectionDetailsSupport._

object DeviceEndpoint extends RestEndpoint {
  def endpoint( implicit actorSystem: ActorSystem ): Route = {
    val deviceEndpoint = actorSystem.actorSelection("/user/decentraldevicemanager2")

    pathPrefix("device" ) {
      path("printer") {
        post {
          entity(as[PrinterConnectionDetails]) { printerConenctionDetails =>
            deviceEndpoint ! ConfigurePrinter(printerConenctionDetails)
            complete(HttpEntity("Added Printer"))
          }
        }
      }
    }
  }
}
