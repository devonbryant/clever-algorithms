package randomsearch

import scala.util.Random._
import Numeric.Implicits._

object RandomSearch extends App {
  
  def randomVec[A : Numeric](low: A, high: A, n: Int, rand: => A) = {
    Seq.fill(n) { low + (high - low) * rand }
  }
  
  def cost[A : Numeric, B](as: Seq[A]) = {
    val z = implicitly[Numeric[A]].zero
    Option(as.foldLeft(z) { (a, b) => a + (b * b) })
  }
  
  def search[A : Numeric, B](randSpace: => Seq[A], cost: Seq[A] => Option[B], iterations: Int)(implicit cmp: Ordering[B]) = {
    
    def iter(best: (Option[B], Seq[A]), i: Int): (Option[B], Seq[A]) = {
      if (i == iterations) best
      else {
        val currentSpace = randSpace
        val currentCost = cost(currentSpace)
        
        val soln = best._1 match {
          case Some(prevCost) if (cmp.gt(currentCost.get, prevCost)) => best
          case _ => (currentCost, currentSpace)
        }
        
        iter(soln, i+1)
      }
    }
    
    iter((None, Nil), 0)
  }
  
  val solution = search[Float, Float](randomVec(-5.0f, 5.0f, 2, nextFloat), cost, 100)
  println("Score: " + solution._1.get + " Solution: " + solution._2)
}