// package chapter7

// final case class Rational(numerator: Int, denominator: Int)

// final case class Order(units: Int, unitPrice: Double) {
//   val totalPrice: Double = units * unitPrice
// }

// object Order {
//   implicit val lessThanOrdering = Ordering.fromLessThan[Order]{ (x, y) =>
//     x.totalPrice < y.totalPrice
//   }
// }

// object OrderUnitPriceOrdering {
//   implicit val unitPriceOrdering = Ordering.fromLessThan[Order]{ (x, y) =>
//     x.unitPrice < y.unitPrice
//   }
// }

// object OrderUnitsOrdering {
//   implicit val unitsOrdering = Ordering.fromLessThan[Order]{ (x, y) =>
//     x.units < y.units
//   }
// }

// trait Equal[A] {
//   def equal(v1: A, v2: A): Boolean
// }

// case class Person(name: String, email: String)

// object PersonEqualEmail extends Equal[Person] {
//   def equal(p1: Person, p2: Person): Boolean = 
//     p1.email == p2.email

// }

// object PersonEqualBoth extends Equal[Person] {
//   def equal(p1: Person, p2: Person): Boolean =
//     (p1.email == p2.email) && p1.name == p2.name
// }

// object Main {

//   def main(args: Array[String]): Unit = {
//     println("ch7")

//     implicit val absOrdering = Ordering.fromLessThan[Int]((x, y) => {
//         math.abs(x) < math.abs(y)
//     })

//     assert(List(-4, -1, 0, 2, 3).sorted == List(0, -1, 2, 3, -4))
//     assert(List(-4, -3, -2, -1).sorted == List(-1, -2, -3, -4))    

//     implicit val ordering = Ordering.fromLessThan[Rational]( (x, y) =>
//       (x.numerator.toDouble / x.denominator.toDouble) <
//       (y.numerator.toDouble / y.denominator.toDouble)
//     )    

//     assert(List(Rational(1, 2), Rational(3, 4), Rational(1, 3)).sorted ==
//        List(Rational(1, 3), Rational(1, 2), Rational(3, 4)))

//   }
// }












