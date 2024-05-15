package com.example.pekko.http

import java.io.{PipedInputStream, PipedOutputStream}
import scala.concurrent.{ExecutionContext, Future}
import org.apache.pekko.http.scaladsl.model.{HttpCharsets, HttpEntity, MediaType}
import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import org.apache.pekko.stream.scaladsl.StreamConverters
import io.prometheus.metrics.expositionformats.ExpositionFormats
import io.prometheus.metrics.model.registry.PrometheusRegistry

object PrometheusService extends Directives {

  lazy val prometheusTextType =
    MediaType.customWithFixedCharset("text", "plain", HttpCharsets.`UTF-8`, params = Map("version" -> "0.0.4"))

  def route(implicit executionContext: ExecutionContext): Route = {
    path("metrics") {
      complete {
        val in = new PipedInputStream
        val out = new PipedOutputStream(in)
        val byteSource = StreamConverters.fromInputStream(() => in)
        Future {
          try {
            ExpositionFormats.init().getPrometheusTextFormatWriter()
              .write(out, PrometheusRegistry.defaultRegistry.scrape())
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
