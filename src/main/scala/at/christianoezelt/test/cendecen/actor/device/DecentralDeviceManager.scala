package at.christianoezelt.test.cendecen.actor.device

import akka.actor.ActorLogging
import akka.persistence.{PersistentActor, PersistentRepr}
import at.christianoezelt.test.cendecen.model.device.PrinterConnectionDetails

sealed trait DecentralDeviceManagerCommands
case class ConfigurePrinter( printerConnectionDetails: PrinterConnectionDetails ) extends DecentralDeviceManagerCommands

class DecentralDeviceManager extends PersistentActor with ActorLogging {
  override def receiveRecover: Receive = {
    case x: DecentralDeviceManagerCommands => log.info(s"Rerunning ${x.toString}")
  }

  override def receiveCommand: Receive = {
    case msg @ConfigurePrinter( printerConnectionConfig ) =>
      log.info(s"Configuring printer at ${printerConnectionConfig.ip}")
      persist(msg){
        m =>
      }
    case x: DecentralDeviceManager =>
      log.warning(s"Command[${x.getClass.getName}] not implemented yet!")
  }

  override def persistenceId: String = context.self.path.name
}
