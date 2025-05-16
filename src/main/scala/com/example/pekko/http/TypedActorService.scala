package com.example.pekko.http

import com.example.pekko.typed.TypedActor
import com.example.pekko.typed.TypedActor.{Greet, Greeted}
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.AskPattern.Askable
import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import org.apache.pekko.util.Timeout

import scala.concurrent.duration.DurationInt

object TypedActorService extends Directives {

  private val system: ActorSystem[Greet] = ActorSystem(TypedActor(), "greet-service")
  sys.addShutdownHook(system.terminate())

  def route(): Route = {
    path("typed") {
      get {
        val fut = system.ask[Greeted](Greet("World", _))(Timeout(10.seconds), system.scheduler)
        complete(
          fut.map { greeted => s"Hello ${greeted.whom}!" }(system.executionContext)
        )
      }
    }
  }
}
