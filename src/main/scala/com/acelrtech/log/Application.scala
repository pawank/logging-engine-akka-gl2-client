package com.acelrtech.log

import akka.actor._
import akka.event.Logging

import com.acelrtech.log.models._



object LoggingEngine {
  import com.typesafe.config._
  val config:Config = ConfigFactory.load()
  val system = ActorSystem("LocalLogTest")
  //val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")
  val remote = system.actorSelection("akka.tcp://AcelrTechLabsLoggingSystem@127.0.0.1:5151/user/LoggingActor")
  val log = Logger(remote,config)
  def main(args:Array[String]) {

      log.info("\nLoggingActor client has started...")
      try {
        1 / 0
      }catch {
        case e:Exception =>
          log.error("1/0",e)
      }
    }
}

/*
class LocalActor extends Actor {
  def receive = {
      case _ =>
  }
}*/
