package sbtcaliper

import sbt._
import sbt.Defaults._
import sbt.Keys._

object Plugin extends sbt.Plugin {

  val benchmark = TaskKey[Unit]("benchmark", "Executes all benchmarks.")

  val benchmarkTasks = Seq(
    benchmark <<= (fullClasspath in Test, sources in Test, scalaInstance, javaHome, javaOptions, baseDirectory, outputStrategy, streams) map {
      (cpa, srcs, si, jhome, jopts, dir, strategy, s) =>
        val cp = "-classpath" :: Path.makeString(cpa.files) :: Nil
        val args = srcs map { _.base } filter { _.endsWith("Benchmark") } toArray

        val fr = new ForkRun(
          ForkOptions(scalaJars = si.jars,
            javaHome = jhome,
            runJVMOptions = jopts ++ cp,
            outputStrategy = strategy,
            workingDirectory = Some(dir)))

        sbt.toError(fr.run("com.google.caliper.Runner", Build.data(cpa), args, s.log))
    })

  override val settings = benchmarkTasks

}
