package com.example.pekko.http

import java.io.{ OutputStreamWriter, PipedInputStream, PipedOutputStream }

import scala.concurrent.{ ExecutionContext, Future }

import org.apache.pekko.http.scaladsl.model.{ HttpCharsets, HttpEntity, MediaType }
import org.apache.pekko.http.scaladsl.server.{ Directives, Route }
import org.apache.pekko.stream.scaladsl.StreamConverters
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat

object PrometheusService extends Directives {

  lazy val prometheusTextType =
    MediaType.customWithFixedCharset("text", "plain", HttpCharsets.`UTF-8`, params = Map("version" -> "0.0.4"))

  def route(implicit executionContext: ExecutionContext): Route = {
    path("metrics") {
      complete {
        val in = new PipedInputStream
        val out = new OutputStreamWriter(new PipedOutputStream(in), HttpCharsets.`UTF-8`.value)
        val byteSource = StreamConverters.fromInputStream(() => in)
        Future {
          try {
            TextFormat.write004(out, CollectorRegistry.defaultRegistry.metricFamilySamples())
            out.flush()
          } finally {
            out.close()
          }
        }
        HttpEntity(prometheusTextType, byteSource)
      }
    }
  }
}
