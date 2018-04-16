package at.christianoezelt.test.cendecen.model.device

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait Stuff extends Serializable

case class PrinterConnectionDetails(ip: String ) extends Stuff
object PrinterConnectionDetailsSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val PortofolioFormats = jsonFormat1(PrinterConnectionDetails)
}