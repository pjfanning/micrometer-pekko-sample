name := "micrometer-pekko-sample"

scalaVersion := "2.13.16"

val pekkoVersion = "1.1.3"
val pekkoHttpVersion = "1.2.0"
val aspectJVersion = "1.9.24"
val micrometerVersion = "1.15.0"
val prometheusVersion = "1.3.6"

//ThisBuild / resolvers += Resolver.ApacheMavenSnapshotsRepo

enablePlugins(JavaAgent)
javaAgents += "org.aspectj" % "aspectjweaver" % aspectJVersion  % "runtime"

libraryDependencies ++= Seq(
  "com.github.pjfanning" %% "micrometer-pekko" % "0.18.0",
  "org.aspectj" % "aspectjweaver" % aspectJVersion  % "runtime",
  "io.micrometer" % "micrometer-registry-prometheus" % micrometerVersion,
  "io.prometheus" % "prometheus-metrics-core" % prometheusVersion,
  "org.apache.pekko" %% "pekko-http" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-http-spray-json" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-actor" % pekkoVersion,
  "org.apache.pekko" %% "pekko-actor-typed" % pekkoVersion,
  "org.apache.pekko" %% "pekko-stream" % pekkoVersion,
  "org.apache.pekko" %% "pekko-slf4j" % pekkoVersion,
  "ch.qos.logback" % "logback-classic" % "1.3.15"
)

//uncomment this add-opens -- needed for Java 16 and above runtimes -- will cause issues if used with Java 8 runtime
//run / javaOptions ++= Seq("--add-opens", "java.base/java.lang=ALL-UNNAMED")
