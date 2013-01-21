object SimpleObj {

  def longOperation {
    (List(1, 2, 3) zip List(4, 5, 6)).map(t => t._1 + t._2).max
  }
  
}
