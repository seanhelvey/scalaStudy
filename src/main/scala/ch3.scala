package chapter3

case class Person(first: String, last: String) {
  val firstName = first
  val lastName = last
  def name = firstName + " " + lastName
}

object Person {
    def apply(fullName: String): Person = { //now has two apply methods
        val parts = fullName.split(" ")
        new Person(parts(0), parts(1))
    }
}

case class Cat(name: String, colour: String, food: String) {

}

object ChipShop {
    def willServe(cat: Cat): Boolean =
        // if (cat.food == "Chips") true
        // else false

    cat match {
      case Cat(_, _, "Chips") => true
      case Cat(_, _, _) => false
    }

}

class Director(val firstName: String, val lastName: String, val yearOfBirth: Int) {
  def name = firstName + " " + lastName
}

object Director {
  def apply(firstName: String, lastName: String, yearOfBirth: Int): Director =
    new Director(firstName, lastName, yearOfBirth)

  def older(director1: Director, director2: Director): Director =
    if (director1.yearOfBirth < director2.yearOfBirth) director1 else director2


}

class Film(val name: String, val yearOfRelease: Int, val imdbRating: Double, val director: Director) {
  def directorsAge = yearOfRelease - director.yearOfBirth
  def isDirectedBy(directorParam: Director): Boolean =
    if (directorParam == director) true
    else false

  def copy(name: String = this.name, yearOfRelease: Int = this.yearOfRelease, imdbRating: Double = this.imdbRating, director: Director = this.director): Film =
    return new Film(name, yearOfRelease, imdbRating, director);
}

object Film {
  def apply(name: String, yearOfRelease: Int, imdbRating: Double, director: Director): Film =
    new Film(name, yearOfRelease, imdbRating, director)

  def highestRating(film1: Film, film2: Film): Film =
    if (film1.imdbRating > film2.imdbRating) film1 else film2

  def oldestDirectorAtTheTime(film1: Film, film2: Film): Director =
    if (film1.yearOfRelease - film1.director.yearOfBirth > film2.yearOfRelease - film2.director.yearOfBirth) film1.director else film2.director
}

// class Adder(amount: Int) {
//   def add(in: Int) = in + amount
// }

case class Counter(count: Int = 0) {
  def inc: Counter = inc()
  def inc(by: Int = 1): Counter = new Counter(count + by)

  def dec: Counter = dec()
  def dec(by: Int = 1): Counter = new Counter(count - by)

  //def adjust(addIn: Adder): Counter = new Counter(addIn.add(count))
}

class Adder(amount: Int) {
  def apply(in: Int): Int = in + amount
}

object Stormtrooper {
  def inspect(person: Person): String =
    person match {
      case Person("Luke", "Skywalker") => "Stop, rebel scum!"
      case Person("Han", "Solo") => "Stop, rebel scum!"
      case Person(first, last) => s"Move along, $first"
    }
}

// object Main {
//   def main(args: Array[String]): Unit = {
//     // val dave = new Person("Dave", "Gurnell")
//     // println(dave.firstName)

//     val cat = new Cat("Rascal", "Blue", "Cherries")
//     // println(cat.name)

//     println(ChipShop.willServe(cat))

//     //val eastwood          = new Director("Clint", "Eastwood", 1930)
//     //val mcTiernan         = new Director("John", "McTiernan", 1951)
//     //val nolan             = new Director("Christopher", "Nolan", 1970)
//     // val someBody          = new Director("Just", "Some Body", 1990)

//     //val memento           = new Film("Memento", 2000, 8.5, nolan)
//     //val darkKnight        = new Film("Dark Knight", 2008, 9.0, nolan)
//     // val inception         = new Film("Inception", 2010, 8.8, nolan)

//     //val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7, eastwood)
//     // val outlawJoseyWales  = new Film("The Outlaw Josey Wales", 1976, 7.9, eastwood)
//     // val unforgiven        = new Film("Unforgiven", 1992, 8.3, eastwood)
//     // val granTorino        = new Film("Gran Torino", 2008, 8.2, eastwood)
//     // val invictus          = new Film("Invictus", 2009, 7.4, eastwood)

//     // val predator          = new Film("Predator", 1987, 7.9, mcTiernan)
//     // val dieHard           = new Film("Die Hard", 1988, 8.3, mcTiernan)
//     // val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6, mcTiernan)
//     // val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8, mcTiernan)

//     //println(Director.older(eastwood, mcTiernan).name)
//     // println(dieHard.director.name)
//     // println(invictus.isDirectedBy(nolan))

//     // println(highPlainsDrifter.copy(name = "L'homme des hautes plaines").yearOfRelease)
//     // println(thomasCrownAffair.copy(yearOfRelease = 1968, director = new Director("Norman", "Jewison", 1926)).director.yearOfBirth)
//     // println(inception.copy().copy().copy().imdbRating)

//     // val myCounter = new Counter
//     // println(myCounter.inc.inc(10).dec)
//     //need to instantiate Adder for below to work
//     //println(myCounter.inc.inc(10).dec.adjust(7))

//     // val add3 = new Adder(3)
//     // println(add3(2))

//     //println(Person("John Doe").firstName)

//     //println(Film.oldestDirectorAtTheTime(darkKnight, highPlainsDrifter).firstName)

//     //println(Stormtrooper.inspect(Person("Han", "Solo")))
//   }
// }