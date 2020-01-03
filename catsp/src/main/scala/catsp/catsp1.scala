package catsp
import cats.Eq
import cats.instances.int._
import cats.instances.string._
import cats.instances.option._
import cats.syntax.eq._


// Define a very simple JSON AST
sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
case object JsNull extends Json

// The "serialize to JSON" behaviour is encoded in this trait
trait JsonWriter[A] {
  def write(value: A): Json
}

final case class Person(name: String, email: String)

object JsonWriterInstances {
  // https://stackoverflow.com/questions/16259168/how-does-curly-braces-following-trait-instantiation-work
  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(value: String): Json =
        JsString(value)
    }

  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      def write(value: Person): Json =
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))
    }

  // etc...
}

object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}

final case class Cat(name: String, age: Int, color: String)

object catsp1 {
  /*
  Type Class Variance	Invariant	Covariant	Contravariant
  Supertype instance used?	No	No	Yes
  More specific type preferred?	No	Yes	No
   */
  import JsonWriterInstances._

  Json.toJson(Person("Dave", "dave@example.com"))
  // res4: Json = JsObject(Map(name -> JsString(Dave), email -> JsString(dave@example.com)))
  Option(1) === Option.empty[Int]

  implicit val catEqual: Eq[Cat] =
    Eq.instance[Cat] { (cat1, cat2) =>
      (cat1.name  === cat2.name ) &&
        (cat1.age   === cat2.age  ) &&
        (cat1.color === cat2.color)
    }

  def main(args: Array[String]): Unit = {
    123 === 234
  }
}
