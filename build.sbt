name := "micrometer-pekko-sample"

scalaVersion := "2.13.11"

val pekkoVersion = "1.0.1"
val pekkoHttpVersion = "0.0.0+4468-963bd592-SNAPSHOT"
val aspectJVersion = "1.9.19"
val micrometerVersion = "1.11.2"
val prometheusVersion = "0.16.0"

ThisBuild / resolvers += Resolver.ApacheMavenSnapshotsRepo

enablePlugins(JavaAgent)
javaAgents += "org.aspectj" % "aspectjweaver" % aspectJVersion  % "runtime"

libraryDependencies ++= Seq(
  "com.github.pjfanning" %% "micrometer-pekko" % "0.15.0",
  "org.aspectj" % "aspectjweaver" % aspectJVersion  % "runtime",
  "io.micrometer" % "micrometer-registry-prometheus" % micrometerVersion,
  "io.prometheus" % "simpleclient" % prometheusVersion,
  "io.prometheus" % "simpleclient_common" % prometheusVersion,
  "org.apache.pekko" %% "pekko-http" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-http-spray-json" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-actor" % pekkoVersion,
  "org.apache.pekko" %% "pekko-stream" % pekkoVersion,
  "org.apache.pekko" %% "pekko-slf4j" % pekkoVersion,
  "ch.qos.logback" % "logback-classic" % "1.3.5"
)

//uncomment this add-opens -- needed for Java 16 and above runtimes -- will cause issues if used with Java 8 runtime
//run / javaOptions ++= Seq("--add-opens", "java.base/java.lang=ALL-UNNAMED")
