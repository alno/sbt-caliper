
class SimpleBenchmark extends com.google.caliper.SimpleBenchmark {

  def timeSomeAction(reps: Int) {
    for (i <- 1 to reps)
      SimpleObj.longOperation
  }
  
}