package com.acelrtech.log

import akka.actor._
import akka.event.Logging

import com.acelrtech.log.backends.actor._
import com.acelrtech.log.backends.gl2._

object LoggingEngine {
  import com.typesafe.config._
  val config:Config = ConfigFactory.load()
  val system = ActorSystem("LocalLogTest")

  def gl2Test = {
  val remote = system.actorSelection("akka.tcp://AcelrTechLabsGraylog2LoggingSystem@127.0.0.1:5150/user/LoggingActor")
  val log = Graylog2Logger(remote,config)
      log.info("\nLoggingActor client has started...")
      try {
        log.info("GL2")
        val x = com.acelrtech.log.models.app.SimpleLog(id = Some("100"), entity = "pawan", logCategory = com.acelrtech.log.models.app.LogCategory.DEBUG, module = "UI", message = "Testing applog", stackTrace = Some("exception msg"), input = "none", output = None, target = "a", calledFunction = "main()", host = "test.com", _t = com.acelrtech.log.models.app.AppLogType(), createdOn = org.joda.time.DateTime.now)
        log.log(x)
        val y = com.acelrtech.log.models.app.FullLog(id = Some("100"), entity = "pawan", logCategory = com.acelrtech.log.models.app.LogCategory.FATAL, module = "UI", message = "Testing applog", stackTrace = Some("exception msg"), input = "none", output = None, target = "a", calledFunction = "main()", host = "test.com", _t = com.acelrtech.log.models.app.AppLogType(), createdOn = org.joda.time.DateTime.now, request = com.acelrtech.log.models.app.PlayLogRequest(ip = "127.0.0.1", path = "/search", status = "200", action = "/search", queryString = Some("a=b"), session = Some("play.id=43432432423432")))
        log.log(y)
        log.unknown("Unkown error")
        1 / 0
      }catch {
        case e:Exception =>
          log.error("1/0",e)
          remote ! com.acelrtech.log.models.Enable
          log.debug("Debug test message")
      }
  }
  def akkaTest = {
  val remote = system.actorSelection("akka.tcp://AcelrTechLabsLoggingSystem@127.0.0.1:5150/user/LoggingActor")
  val log = AkkaLogger(remote,config)
      log.info("\nLoggingActor client has started...")

      try {
        log.info("GL2")
        val x = com.acelrtech.log.models.app.SimpleLog(id = Some("100"), entity = "pawan", logCategory = com.acelrtech.log.models.app.LogCategory.DEBUG, module = "UI", message = "Testing applog", stackTrace = Some("exception msg"), input = "none", output = None, target = "a", calledFunction = "main()", host = "test.com", _t = com.acelrtech.log.models.app.AppLogType(), createdOn = org.joda.time.DateTime.now)
        log.unknown("Unkown error")
        log.error(x.toString)
        1 / 0
      }catch {
        case e:Exception =>
          log.error("1/0",e)
          remote ! com.acelrtech.log.models.Enable
          log.debug("Debug test message")
      }
  }
  def main(args:Array[String]) {
      println(s"Running main of client for logging engine..")
      //akkaTest
      gl2Test
    }
}

/*
class LocalActor extends Actor {
  def receive = {
      case _ =>
  }
}*/
