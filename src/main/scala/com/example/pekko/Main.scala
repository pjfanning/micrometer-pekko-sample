package com.example.pekko

import com.example.pekko.http.WebServer
import com.github.pjfanning.micrometer.pekko.PekkoMetricRegistry
import io.micrometer.core.instrument.Clock
import io.micrometer.prometheus.{PrometheusConfig, PrometheusMeterRegistry}
import io.prometheus.client.CollectorRegistry

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  val prometheusMeterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT, CollectorRegistry.defaultRegistry, Clock.SYSTEM)
  PekkoMetricRegistry.setRegistry(prometheusMeterRegistry)

  Await.ready(WebServer.start(), Duration.Inf)
}
