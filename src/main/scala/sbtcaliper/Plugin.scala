package sbtcaliper

import sbt._
import sbt.Keys._
import sbt.Defaults._

object Plugin extends sbt.Plugin {

  val benchmark = TaskKey[Unit]("benchmark", "Executes all benchmarks.")
  val benchmarkOnly = InputKey[Unit]("benchmark-only", "Executes specified benchmarks.")

  val benchmarkTasks = Seq(
    benchmark <<= benchmarkTaskInit.zip(sources in Test) {
      case (runTask, srcsTask) =>
        (runTask :^: srcsTask :^: KNil) map {
          case run :+: srcs :+: HNil =>
            run { srcs map { _.base } filter { _.endsWith("Benchmark") } }
        }
    },

    benchmarkOnly <<= sbt.inputTask { (argTask: TaskKey[Seq[String]]) =>
      benchmarkTaskInit.zip(argTask) {
        case (runTask, argTask) =>
          (runTask :^: argTask :^: KNil) map {
            case run :+: args :+: HNil =>
              run { args }
          }
      }
    })

  override val settings = benchmarkTasks

  private def benchmarkTaskInit: Project.Initialize[Task[Seq[String] => Unit]] =
    (fullClasspath in Test, scalaInstance, javaHome, javaOptions, baseDirectory, outputStrategy, streams) map {
      (cpa, si, jhome, jopts, dir, strategy, s) =>
        val cp = "-classpath" :: Path.makeString(cpa.files) :: Nil
        val fr = new ForkRun(
          ForkOptions(scalaJars = si.jars,
            javaHome = jhome,
            runJVMOptions = jopts ++ cp,
            outputStrategy = strategy,
            workingDirectory = Some(dir)))

        { args: Seq[String] =>
          if (args.isEmpty)
            println("No benchmarks specified - nothing to run")
          else
            sbt.toError(fr.run("com.google.caliper.Runner", Build.data(cpa), args, s.log))
        }
    }

}
