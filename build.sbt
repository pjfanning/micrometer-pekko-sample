name := "micrometer-pekko-sample"

scalaVersion := "2.13.18"

val pekkoVersion = "1.7.0"
val pekkoHttpVersion = "1.4.0"
val aspectJVersion = "1.9.25.1"
val micrometerVersion = "1.17.1"
val prometheusVersion = "1.8.0"

//ThisBuild / resolvers += Resolver.ApacheMavenSnapshotsRepo

enablePlugins(JavaAgent)
javaAgents += "org.aspectj" % "aspectjweaver" % aspectJVersion  % "runtime"

libraryDependencies ++= Seq(
  "com.github.pjfanning" %% "micrometer-pekko" % "0.21.0",
  "org.aspectj" % "aspectjweaver" % aspectJVersion  % "runtime",
  "io.micrometer" % "micrometer-registry-prometheus" % micrometerVersion,
  "io.prometheus" % "prometheus-metrics-core" % prometheusVersion,
  "org.apache.pekko" %% "pekko-http" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-http-spray-json" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-actor" % pekkoVersion,
  "org.apache.pekko" %% "pekko-actor-typed" % pekkoVersion,
  "org.apache.pekko" %% "pekko-stream" % pekkoVersion,
  "org.apache.pekko" %% "pekko-slf4j" % pekkoVersion,
  "ch.qos.logback" % "logback-classic" % "1.3.16"
)

//comment this add-opens -- needed for Java 16 and above runtimes -- if testing with Java 8 runtime
run / javaOptions ++= Seq("--add-opens", "java.base/java.lang=ALL-UNNAMED")

