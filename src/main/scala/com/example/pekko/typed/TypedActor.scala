package com.example.pekko.typed

import org.apache.pekko.actor.typed.scaladsl.AskPattern.Askable
import org.apache.pekko.actor.typed.{ActorRef, ActorSystem, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object TypedActor {
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  final case class Greeted(whom: String, from: ActorRef[Greet])

  def apply(): Behavior[Greet] = Behaviors.receive { (context, message) =>
    context.log.info("Hello {}!", message.whom)
    message.replyTo ! Greeted(message.whom, context.self)
    Behaviors.same
  }

  val system: ActorSystem[Greet] = ActorSystem(TypedActor(), "greet")
  system.ask[Greeted](Greet("World", _)) { replyTo =>
    Greeted("World", replyTo)
  }(system.scheduler, system.executionContext).onComplete {
    case scala.util.Success(Greeted(whom, _)) => system.log.info("Received greeting from {}", whom)
    case scala.util.Failure(exception)        => system.log.error("Failed to greet: {}", exception.getMessage)
  }
}
