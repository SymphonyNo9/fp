package fpis

//hide std library `Option` and `Either`, since we are writing our own in this chapter
import scala.{Either => _, Option => _}

sealed trait Option[+A] {
  def map[B](f: A=>B): Option[B] =
    this match {
      case None => None
      case Some(a: A) => Some(f(a))
    }

  def getOrElse[B >: A](default: => B):B = this match {
    case None => default
    case Some(a) => a
  }

  def flatMap[B](f: A=>Option[B]): Option[B] = this match {
    case None => None
    case Some(a: A) => f(a)
  }
  // 非匹配方式
//  def flatMap[B](f: A => Option[B]): Option[B] =
//    map(f) getOrElse None

  // if not None, Some(self), else ob
  def orElse[B>:A](ob: => Option[B]): Option[B] =
    this map (Some(_)) getOrElse ob
  /*
Again, we can implement this with explicit pattern matching.
*/
  def orElse_1[B>:A](ob: => Option[B]): Option[B] = this match {
    case None => ob
    case _ => this
  }

  def filter(f: A=>Boolean): Option[A] = this match {
    case Some(a) if f(a) => this
    case _ => None
  }

}
case object None extends Option[Nothing]
case class Some[+A](get: A) extends Option[A]



object FPIS4Option {
  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum/xs.length)

  def variance(xs: Seq[Double]): Option[Double] ={
    mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))))
  }

}
