package com.acelrtech.log

import akka.actor._
import akka.event.Logging

import com.acelrtech.log.backends.actor._
import com.acelrtech.log.backends.gl2._

object LoggingEngine {
  import com.typesafe.config._
  val config:Config = ConfigFactory.load()
  val system = ActorSystem("LocalLogTest")
  //val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")
  //val remote = system.actorSelection("akka.tcp://AcelrTechLabsLoggingSystem@127.0.0.1:5151/user/LoggingActor")
  //val log = Logger(remote,config)
  val remote = system.actorSelection("akka.tcp://AcelrTechLabsGraylog2LoggingSystem@127.0.0.1:5152/user/Graylog2LoggingActor")
  //val remote = system.actorSelection("akka.tcp://AcelrTechLabsLoggingSystem@127.0.0.1:5151")
  val log = Graylog2Logger(remote,config)
  def main(args:Array[String]) {
      println(s"Running main of client for logging engine..")
      log.info("\nLoggingActor client has started...")
      try {
        log.info("GL2")
        val x = com.acelrtech.log.models.app.SimpleLog(id = Some("100"), entity = "pawan", logCategory = com.acelrtech.log.models.app.LogCategory.DEBUG, module = "UI", message = "Testing applog", stackTrace = Some("exception msg"), input = "none", output = None, target = "a", calledFunction = "main()", host = "test.com")
        log.log(x)
        1 / 0
      }catch {
        case e:Exception =>
          log.error("1/0",e)
          remote ! com.acelrtech.log.models.Enable
          log.debug("Debug test message")
      }
    }
}

/*
class LocalActor extends Actor {
  def receive = {
      case _ =>
  }
}*/
