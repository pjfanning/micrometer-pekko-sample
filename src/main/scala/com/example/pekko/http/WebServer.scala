package com.example.pekko.http

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.Http

import scala.concurrent.{ExecutionContextExecutor, Future}

object WebServer {

  def start(): Future[Nothing] = {
    implicit val system: ActorSystem = ActorSystem("pekko-http-sample")
    sys.addShutdownHook(system.terminate())

    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val routes = PrometheusService.route ~ TypedActorService.route
    for {
      bindingFuture <- Http().newServerAt("0.0.0.0", 12345).bind(routes)
      waitOnFuture  <- Future.never
    } yield waitOnFuture
  }
}
