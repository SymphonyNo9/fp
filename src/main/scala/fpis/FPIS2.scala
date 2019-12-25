package fpis

object FPIS2 {
  def abs(n: Int): Int =
    if (n < 0) -n
    else n

  private def formatAbs(x: Int) = {
    val msg = "The absolute value of %d is %d"
    msg.format(x, abs(x))
  }

  // A definition of factorial, using a local, tail recursive function
  def factorial(n: Int): Int = {
    @annotation.tailrec
    def go(n: Int, acc: Int): Int =
      if (n <= 0) acc
      else go(n-1, n*acc)

    go(n, 1)
  }

  def fib(n: Int): Int = {
    @annotation.tailrec
    def loop(n: Int, prev: Int, cur: Int):Int = {
      if (0 == n)
        prev
      else
        loop(n-1, cur, prev + cur)
    }

    loop(n, 0, 1)
  }

  def isSorted[A](as: Array[A], gt: (A, A) => Boolean): Boolean = {
    @annotation.tailrec
    def loop(n: Int): Boolean = {
      if (n >= as.length-1) true
      else if (gt(as(n), as(n+1))) false
      else loop(n + 1)
    }

    loop(0)
  }

  def partial1[A,B,C](a: A, f: (A, B) => C): B => C =
    (b: B) => f(a, b)

  def curry[A,B,C](f: (A, B) => C): A => (B => C) =
    (a: A) => (b: B) => f(a, b)

  def compose[A, B, C](f: B=>C, g:A=>B): A=>C =
    a => f(g(a))

  def main(args: Array[String]): Unit = {
//    println(formatAbs(-42))
    println(fib(6))
    println(isSorted(Array[Int](1, 2, 3, 4), (a:Int, b:Int) => a > b))
  }
}
