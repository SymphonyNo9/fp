package fpis

trait Stream[+A] {
  /*
  递归方法，容易溢出. 另外原生的List使用的左结合
  def toList: List[A] = this match {
    case Empty => Nil
    case Cons(h, t) => h() :: t().toList
  }
   */
  def toList: List[A] = {
    @scala.annotation.tailrec
    def go(s: Stream[A], acc:List[A]): List[A] = s match {
      case Empty => Nil
      case Cons(h, t) => go(t(), h():: acc)
    }
    go(this, List()).reverse
  }

}
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream{
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }
  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))

  // This is more efficient than `cons(a, constant(a))` since it's just
  // one object referencing itself.
  def constant[A](a: A): Stream[A] = {
    lazy val tail: Stream[A] = Cons(() => a, () => tail)
    tail
  }
}

object FPIS5 {
  def if2[A](cond: Boolean, onTrue: =>A, onFalse: =>A): A =
    if (cond) onTrue else onFalse

  def constant[A](a: A): Stream[A] =
    Stream.cons(a, constant(a))
}
