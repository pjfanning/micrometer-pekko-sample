package com.example.pekko.http

import org.apache.pekko.http.scaladsl.server.{Directives, Route}

import scala.concurrent.ExecutionContext

object TypedActorService extends Directives {

  def route(implicit executionContext: ExecutionContext): Route = {
    path("typed") {
      get {
        complete("OK")
      }
    }
  }
}
