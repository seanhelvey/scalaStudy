// package chapter6

// case class Film(
//   name: String,
//   yearOfRelease: Int,
//   imdbRating: Double)

// case class Director(
//   firstName: String,
//   lastName: String,
//   yearOfBirth: Int,
//   films: Seq[Film])

// object Main {

//   def main(args: Array[String]): Unit = {
//     println("ch6")

//     val animals = Seq("cat", "dog", "penguin")

//     println(animals.+:("cheese"))

//     println(animals :+ (1))

//     println(animals)

//     val memento           = new Film("Memento", 2000, 8.5)
//     val darkKnight        = new Film("Dark Knight", 2008, 9.0)
//     val inception         = new Film("Inception", 2010, 8.8)

//     val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7)
//     val outlawJoseyWales  = new Film("The Outlaw Josey Wales", 1976, 7.9)
//     val unforgiven        = new Film("Unforgiven", 1992, 8.3)
//     val granTorino        = new Film("Gran Torino", 2008, 8.2)
//     val invictus          = new Film("Invictus", 2009, 7.4)

//     val predator          = new Film("Predator", 1987, 7.9)
//     val dieHard           = new Film("Die Hard", 1988, 8.3)
//     val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6)
//     val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8)

//     val eastwood = new Director("Clint", "Eastwood", 1930,
//       Seq(highPlainsDrifter, outlawJoseyWales, unforgiven, granTorino, invictus))

//     val mcTiernan = new Director("John", "McTiernan", 1951,
//       Seq(predator, dieHard, huntForRedOctober, thomasCrownAffair))

//     val nolan = new Director("Christopher", "Nolan", 1970,
//       Seq(memento, darkKnight, inception))

//     val someGuy = new Director("Just", "Some Guy", 1990,
//       Seq())

//     val directors = Seq(eastwood, mcTiernan, nolan, someGuy)

//     def findNumberOfFilms(numberOfFilms: Int): Seq[Director] = 
//       directors.filter(_.films.length > numberOfFilms)

//     println(findNumberOfFilms(4))

//     def findBornBefore(year: Int): Option[Director] = 
//       directors.find(_.yearOfBirth < year)

//     println(findBornBefore(1960))

//     def findBornBeforeAndNumFilms(year: Int, numberOfFilms: Int): Seq[Director] = 
//       {
//        val byAge   = directors.filter(_.yearOfBirth < year)
//        val byFilms = directors.filter(_.films.length > numberOfFilms)
//        byAge.filter(byFilms.contains)
//       }

//     println(findBornBeforeAndNumFilms(1960, 3))

//     def sortByAge(ascending: Boolean = true): Seq[Director] = 
//       directors.sortWith((a, b) => a.yearOfBirth < b.yearOfBirth)

//     println(sortByAge(true))    

//     //list of films by nolan
//     //type we have: Director
//     //type we want: List
//     println(nolan.films.map(_.name))

//     //names of films by all directors
//     //type we have: Seq[Director]
//     //type we want: List[String]
//     println(directors.flatMap(director => director.films.map(film => film.name)))

//     //find the date of the earliest McTiernan film
//     //we have: Director
//     //we want: Int
//     println(mcTiernan.films.foldLeft(Int.MaxValue) { (current, film) =>
//       math.min(current, film.yearOfRelease)
//     })

//     //Starting with directors, find all films sorted by descending IMDB rating
//     //from: Seq[Director]
//     //to: List[Film]
//     println(directors.flatMap(_.films).sortWith { (a, b) =>
//       a.imdbRating > b.imdbRating 
//     }) // Seq[Film]

//     //Starting with directors again, find the average score across all films
//     //from: Seq[Director]
//     //to: Int
//     val filmRatings = directors.flatMap(_.films).map(_.imdbRating)
//     println(filmRatings.foldLeft(0.0) ((current, next) => current + next) / filmRatings.length)  

//     //Starting with directors, print the following for every film: "Tonight only! FILM NAME by DIRECTOR!"
//     //from: Seq[Director]
//     //to: Unit
//     directors.foreach(a => println("blah: " + a.lastName + " and a blah."))

//     directors.foreach { director =>
//       director.films.foreach { film =>
//         println(s"Tonight! ${film.name} by ${director.firstName}!")
//       }
//     }

//     //Finally, starting with directors again, find the earliest film by any director:
//     //from: Seq[Director]
//     //to: Film
//     println(directors.flatMap(_.films).sortWith { (a, b) =>
//       a.yearOfRelease < b.yearOfRelease
//     }.headOption)

//     //Write a method to find the smallest element of a Seq[Int]
//     def myMin(ints: Seq[Int]): Int = {
//       ints.foldLeft(Int.MaxValue) { (a, b) =>
//         math.min(a, b)
//       }
//     }

//     def smallest(seq: Seq[Int]): Int =
//       seq.foldLeft(Int.MaxValue)(math.min)

//     println(myMin(Seq(1,2,3)))
//     println(smallest(Seq(1,2,3)))

//     //Write a function that reverses the elements of a sequence. Your output does not have to use the same concrete implementation as the input. Hint: use foldLeft.
//     //from: Seq[A]
//     //to: Seq[A]
//     def reverse[A](seq: Seq[A]): Seq[A] = 
//       seq.foldLeft(Seq.empty[A]){ (seq, elt) => elt +: seq }

//     println(reverse(Seq(1,2,3)))  

//     //Write map in terms of foldRight.
//     def myMap[A, B](seq: Seq[A], f: A => B): Seq[B] =
//       seq.foldRight(Seq.empty[B]) { (elt, seq) => f(elt) +: seq } //Note how seq on right

//     println(myMap(Seq(1,2,3), (x: Int) => x + 1))

//     //6.3
//     //List the names of the films directed by Christopher Nolan w/out map or flatMap
//     for {
//       x <- nolan.films
//     } println(x.name)

//     //List the names of all films by all directors.
//     for {
//       x <- directors
//       y <- x.films
//     } println(y.name)

//     //Find all films sorted by descending IMDB rating:
//     val films = for {
//       director <- directors
//       film     <- director.films
//     } yield film

//     println(films.sortWith { (a, b) =>
//       a.imdbRating > b.imdbRating
//     })

//     //Print the following for every film: "Tonight only! FILM NAME by DIRECTOR!"
//     for {
//       x <- directors
//       y <- x.films
//     } println(y.name + " by " + x.lastName)

//     //6.5.1
//     //Write a method addOptions that accepts two parameters of type Option[Int] and adds them together. Use a for comprehension to structure your code.
//     def addOptions(a: Option[Int], b: Option[Int]): Option[Int] =
//       for {
//         x <- a
//         y <- b
//       } yield x + y

//     print(addOptions(Some(2), Some(2)))

//     //Write a second version of your code using map and flatMap instead of a for comprehension.
//     def addOptionsMap(optionA: Option[Int], optionB: Option[Int]): Option[Int] =
//       optionA.flatMap(a => optionB.map(b => a + b))

//     println(addOptionsMap(Some(2), Some(2)))

//     def divide(a: Int, b: Int): Option[Int] = 
//       if (b < 1) None else Some (a / b)

//     println(divide(4, 2))

//     def divideOptions(optionA: Option[Int], optionB: Option[Int]) = 
//       for {
//         a <- optionA
//         b <- optionB
//         c <- divide(a, b)
//       } yield c

//     println(divideOptions(Some(9),Some(3)))

//     def readInt(str: String): Option[Int] =
//       if(str matches "\\d+") Some(str.toInt) else None

//     def calculator(operand1: String, operator: String, operand2: String): Unit =
//       {
//         val int1 = readInt(operand1).getOrElse(0)
//         val int2 = readInt(operand2).getOrElse(0)
//         if (operator == "+") 
//           println(int1 + int2)
//         else
//           println(0)
//       }


//     println(calculator("2", "+", "7"))

//     def calculator2(operand1: String, operator: String, operand2: String): Unit = {
//       val result = for {
//         a   <- readInt(operand1)
//         b   <- readInt(operand2)
//         ans <- operator match {
//                  case "+" => Some(a + b)
//                  case "-" => Some(a - b)
//                  case "*" => Some(a * b)
//                  case "/" => divide(a, b)
//                  case _   => None
//                }
//       } yield ans

//       println("result: " + result)

//       result match {
//         case Some(number) => println(s"The answer is $number!")
//         case None         => println(s"Error calculating $operand1 $operator $operand2")
//       }
//     }  

//     println(calculator2("2", "+", "8"))  

//     import scala.util.Try

//     val opt1 = Some(1)
//     val opt2 = Some(2)
//     val opt3 = Some(3)

//     val seq1 = Seq(1)
//     val seq2 = Seq(2)
//     val seq3 = Seq(3)

//     val try1 = Try(1)
//     val try2 = Try(2)
//     val try3 = Try(3)    

//     for {
//      x <- opt1
//      y <- opt2
//      z <- opt3
//     } println(x + y + z)

//     for {
//      x <- seq1
//      y <- seq2
//      z <- seq3
//     } println(x + y + z)

//     for {
//      x <- try1
//      y <- try2
//      z <- try3
//     } println(x + y + z)

//     for(x <- Seq(-2, -1, 0, 1, 2) if x > 0) println(x) //notice no parens around if

//     for {
//       x <- Seq(1, 2, 3)
//       y <- Seq(4, 5, 6)
//     } yield x + y
//     // res: Seq[Int] = List(5, 6, 7, 6, 7, 8, 7, 8, 9)    

//     Seq(1, 2, 3).zip(Seq(4, 5, 6))
//     // res: Seq[(Int, Int)] = List((1,4), (2,5), (3,6))    

//     for {
//       x <- Seq(1, 2, 3).zip(Seq(4, 5, 6))
//     } println ({ 
//       val (a, b) = x; 
//       a + b 
//     })
//     // res: Seq[Int] = List(5, 7, 9)

//     for((a, b) <- Seq(1, 2, 3).zip(Seq(4, 5, 6))) yield a + b
//     // res: Seq[Int] = List(5, 7, 9)    

//     for(x <- Seq(1, 2, 3).zipWithIndex) yield x
//     // res: Seq[(Int, Int)] = List((1,0), (2,1), (3,2))

//     for {
//       x     <- Seq(1, 2, 3)
//       square = x * x
//       y     <- Seq(4, 5, 6)
//     } yield square * y
//     // res: Seq[Int] = List(4, 5, 6, 16, 20, 24, 36, 45, 54)

//     //MAPS & SETS
//     val example = Map("a" -> 1, "b" -> 2, "c" -> 3)
//     // res: scala.collection.immutable.Map[java.lang.String,Int] = Map(a -> 1, b -> 2, c -> 3)
//     println(example)  
//     println(example("a"))        

//     //apply attempts to look up a key and throws an exception if it is not found. 
//     //By contrast, get returns an Option, forcing you to handle the not found case in your code.

//     example.getOrElse("d", -1)
//     // res: Int = -1

//     println(example + ("d" -> 4) - "c")

//   }
// }












