package com.example.pekko.http

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.server.RouteConcatenation

import scala.concurrent.{ExecutionContextExecutor, Future}

object WebServer extends RouteConcatenation {

  def start(): Future[Nothing] = {
    implicit val system: ActorSystem = ActorSystem("pekko-http-sample")
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val routes = PrometheusService.route ~ TypedActorService.route
    for {
      bindingFuture <- Http().newServerAt("0.0.0.0", 12345).bind(routes)
      waitOnFuture  <- Future.never
    } yield waitOnFuture
  }
}
