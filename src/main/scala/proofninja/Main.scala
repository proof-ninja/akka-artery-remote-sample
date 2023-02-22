package proofninja

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.DurationInt
import scala.concurrent.Await

object Main {

  sealed trait Message extends Product with Serializable
  object Message {
    case class Command(text: String) extends Message
    case class Text(text: String) extends Message
    case object Received extends Message
  }

  class TestActor(target: ActorSelection) extends Actor {
    override def receive: Receive = {
      case Message.Command(text) =>
        println(s"Command: $text")
        target ! Message.Text(text)
      case Message.Text(text) =>
        println(s"Received message: ${text}")
        println(s"From: ${sender().path}")
        sender() ! Message.Received
      case Message.Received =>
        println(s"Message received by ${sender().path}")
    }
  }

  def main(args: Array[String]): Unit = {

    val conf = ConfigFactory.load()
    println(s"Actor system load on port=${conf.getLong("akka.remote.artery.canonical.port")}")

    val targetPort =
      args
        .headOption
        .flatMap(_.toIntOption)
        .getOrElse {
          throw new RuntimeException("The port of target remote is required.")
        }

    val system = ActorSystem("system")
    val target = system.actorSelection(s"akka://system@localhost:$targetPort/user/tester")

    val tester = system.actorOf(Props(classOf[TestActor], target), "tester")

    @scala.annotation.tailrec
    def loop(): Unit = {
      println("Input your message, or type 'exit' to close.")
      val msg = scala.io.StdIn.readLine()
      if (msg == "exit") {
        Await.result(system.terminate(), 1.second)
      } else {
        tester ! Message.Command(msg)
        loop()
      }
    }
    loop()
  }
}
