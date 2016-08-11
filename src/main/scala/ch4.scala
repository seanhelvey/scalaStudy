package chapter4
import math._

//4.1.4.1
trait Feline {
  def colour: String
  def sound: String
}

case class Lion(colour: String, maneSize: Int) extends Feline {
  val sound = "roar"
}


case class Tiger(colour: String, maneSize: Int) extends Feline {
  val sound = "roar"
}


case class Panther(colour: String, maneSize: Int) extends Feline {
  val sound = "roar"
}

case class Cat(colour: String, food: String) extends Feline {
  val sound = "meow"
}

//4.1.4.2 & 4.1.4.3
sealed trait Shape {
  def sides: Int
  def perimeter: Double
  def area: Double
  def color: Color
}

case class Circle(radius: Double, color: Color) extends Shape {
  val sides = 1
  val perimeter = 2 * math.Pi * radius
  val area = math.Pi * radius * radius
}

sealed trait Rectangular extends Shape {
  def width: Double
  def height: Double
  val sides = 4
  val perimeter = 2 * height + 2 * width
  val area = height * width
}

case class Square(val size: Double, color: Color) extends Rectangular {
  val width = size;
  val height = size;
}

case class Rectangle(val height: Double, val width: Double, color: Color) extends Rectangular {
}

//4.2.2
object Draw {
  def apply(shape: Shape): String = shape match {
    case Circle(radius, color) =>
      s"A ${Draw(color)} circle of radius ${radius}cm"
    case Rectangle(height, width, color) =>
      s"A ${Draw(color)} rectangle of width ${width}cm and height ${height}cm"
    case Square(size, color) =>
      s"A ${Draw(color)} square of size ${size}cm"
  }

  def apply(color: Color): String = color match {
    // We deal with each of the predefined Colors with special cases:
    case Red    => "red"
    case Yellow => "yellow"
    case Pink   => "pink"
    case color  => if(color.isLight) "light" else "dark"
  }    
}

sealed trait Color {
  def red: Double
  def green: Double
  def blue: Double

  def isLight = (red + green + blue) / 3.0 > 0.5
  def isDark = !isLight
}

final case object Red extends Color {
  // Here we have implemented the RGB values as `vals`
  // because the values cannot change:
  val red = 1.0
  val green = 0.0
  val blue = 0.0
}

final case object Yellow extends Color {
  // Here we have implemented the RGB values as `vals`
  // because the values cannot change:
  val red = 1.0
  val green = 1.0
  val blue = 0.0
}

final case object Pink extends Color {
  // Here we have implemented the RGB values as `vals`
  // because the values cannot change:
  val red = 1.0
  val green = 0.0
  val blue = 1.0
}

final case class CustomColor(red: Double, green: Double, blue: Double) extends Color

sealed trait DivisionResult
final case class Finite(value: Int) extends DivisionResult
final case object Infinite extends DivisionResult

object divide {
  def apply(num: Int, den: Int): DivisionResult =
    if(den == 0) Infinite else Finite(num / den)
}

//4.4.4
//4.5.6
// sealed trait TrafficLight {
//   def next: TrafficLight
// }
// final case object RedLight extends TrafficLight {
//   def next: TrafficLight =
//     GreenLight
// }
// final case object GreenLight extends TrafficLight {
//   def next: TrafficLight =
//     YellowLight
// }
// final case object YellowLight extends TrafficLight {
//   def next: TrafficLight =
//     RedLight
// }

sealed trait TrafficLight {
  def next: TrafficLight =
    this match {
      case RedLight => GreenLight
      case GreenLight => YellowLight
      case YellowLight => RedLight
    }
}
final case object RedLight extends TrafficLight
final case object GreenLight extends TrafficLight
final case object YellowLight extends TrafficLight

sealed trait Calculation
final case class Success(result: Int) extends Calculation
final case class Failure(reason: String) extends Calculation

object Calculator {
  def +(calc: Calculation, operand: Int): Calculation =
    calc match {
        case Success(result) => Success(result + operand)
        case Failure(reason) => Failure(reason)
    }
  def -(calc: Calculation, operand: Int): Calculation =
    calc match {
      case Success(result) => Success(result - operand)
      case Failure(reason) => Failure(reason)
    }
  def /(calc: Calculation, operand: Int): Calculation =
    calc match {
      case Success(result) =>
        operand match {
          case 0 => Failure("Division by zero")
          case _ => Success(result / operand)
        }
      case Failure(reason) => Failure(reason)
    }
}

//4.6.3

// object Main {
//   def main(args: Array[String]): Unit = {
//     // val myCat = Cat("blue", "ferp")
//     // println(myCat)    

//     // val rect = Rectangle(3, 4, Yellow)
//     // println(rect)    

//     // println(Draw(Circle(10,Pink)))
//     // println(Draw(Rectangle(3, 4, CustomColor(0.4, 0.4, 0.6))))

//     // divide(1, 1) match {
//     //   case Finite(value) => println(s"It's finite: ${value}")
//     //   case Infinite      => println("It's infinite")
//     // }

//     // val myRed = RedLight
//     // println(myRed.next)

//     assert(Calculator.+(Success(1), 1) == Success(2))
//     assert(Calculator.-(Success(1), 1) == Success(0))
//     assert(Calculator.+(Failure("Badness"), 1) == Failure("Badness"))

//     assert(Calculator./(Success(4), 2) == Success(2))
//     assert(Calculator./(Success(4), 0) == Failure("Division by zero"))
//     assert(Calculator./(Failure("Badness"), 0) == Failure("Badness"))    

//   }
// }