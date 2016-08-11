package chapter2

object House {
    val number : Int = 221
    val streetName : String = "Baker Street" // street name is an element of the set of all possible strings

    def livesAt(name: String): Boolean = // body of method is expression that can use params and things in object
        if (name == "Sherlock Holmes") true
        else false
}

object calc {
    def square(input: Double): Double = input * 2
    def cube(input: Double): Double = input * square(input)
}

object cal2 {
    def square(input: Int): Int = input * 2
    def square(input: Double): Double = input * 2
    def cube(input: Int): Int = input * square(input)
    def cube(input: Double): Double = input * square(input)
}

object person {
    val firstName : String = "Baker Street"
    val lastName : String = "Baker Street"
}

object alien {
    def greet(input: person.type): String = "Hello " + person.firstName
}

// object Main {
//   def main(args: Array[String]): Unit = {
//     println(House.streetName)
//     println(House.livesAt("Sherlock Holmes"))
//     println(calc.square(2))
//     println(calc.square(2.0))
//     println(calc.cube(2))
//     println(calc.cube(2.0))
//     println(alien.greet(person))
//     assert(calc.square(2.0) == 4.0)
//   }
// }