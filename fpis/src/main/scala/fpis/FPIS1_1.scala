package fpis

object FPIS1_1 {
  class Coffee {
    val price: Int = 5
  }

  class Cafe {
    def buyCoffee(cc: String): (Coffee, Charge) = {
      val cup = new Coffee()
      (cup, Charge(cc, cup.price))
    }
    def buyCoffees(cc: String, n: Int): (List[Coffee], Charge) = {
      val purchases: List[(Coffee, Charge)] = List.fill(n)(buyCoffee(cc))
      val (coffees, charges) = purchases.unzip
      (coffees, charges.reduce((c1, c2) => c1.combine(c2)))
    }
  }

  case class Charge(cc: String, amount: Double){
    def combine(other: Charge) = {
      if (cc == other.cc)
        Charge(cc, amount + other.amount)
      else
        throw new Exception("can not combine")
    }
  }

  def coalesce(charges: List[Charge]): List[Charge] =
    charges.groupBy(_.cc).values.map(_.reduce(_ combine _)).toList

  def main(args: Array[String]): Unit = {
    val c = new Cafe()
    val oneCupCharge = c.buyCoffee("sliver card")
    val anotherCupCharge = c.buyCoffee("sliver card")
    val multiCupCharge = c.buyCoffees("gold card", 5)
    val bill = coalesce(List(oneCupCharge._2, anotherCupCharge._2, multiCupCharge._2))
    println(bill)
  }
}
