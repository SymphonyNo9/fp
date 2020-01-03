package catsp
import scala.language.higherKinds
import cats.Functor

sealed trait Tree[+A] {
}

final case class Branch[A](left: Tree[A], right: Tree[A])
  extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object Tree {
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)

//  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
//    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
//      case Leaf(a) => Leaf(f(a))
//      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
//    }
//  }
  implicit val treeFunctor: Functor[Tree] =
  new Functor[Tree] {
    def map[A, B](tree: Tree[A])(func: A => B): Tree[B] =
      tree match {
        case Branch(left, right) =>
          Branch(map(left)(func), map(right)(func))
        case Leaf(value) =>
          Leaf(func(value))
      }
  }
}

object catsp3 {
  def main(args: Array[String]): Unit = {
    // todo why not working?
//    import Tree._
//    Tree.leaf(100).map(_ * 2)
  }
}
