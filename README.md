# SBT Caliper Plugin

[SBT](http://scala-sbt.org/) plugin to support [Caliper](http://code.google.com/p/caliper/) benchmark execution. Uses test sources for benchmarking.

[![Build Status](https://secure.travis-ci.org/alno/sbt-caliper.png?branch=master)](http://travis-ci.org/alno/sbt-caliper)

## Usage

Add to plugins.sbt file:

    addSbtPlugin("com.github.alno" % "sbt-caliper" % "0.1-SNAPSHOT")

Put your benchmarks in `src/test/scala`. Benchmark classes should have names ending with `Benchmark` and extend Caliper `Benchmark` class.

Run `benchmark` task.

## Examples

Simplest example:

    import com.google.caliper.SimpleBenchmark

    class ExampleBenchmark extends SimpleBenchmark {

      def timeSomeAction(reps: Int) {
        for (i <- 1 to reps)
          SimpleObj.longOperation
      }
  
    }

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request

## Contributors

See https://github.com/alno/sbt-caliper/graphs/contributors

## License

**chef-base**

* Freely distributable and licensed under the [MIT license](http://alno.mit-license.org/).
* Copyright (c) 2012 Alexey Noskov (alexey.noskov@gmail.com) [![endorse](http://api.coderwall.com/alno/endorsecount.png)](http://coderwall.com/alno)
