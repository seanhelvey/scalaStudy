package chapter5

trait Functor [F[_]] {
  def map[A, B](fa: F[A])(ab: A => B): F[B]
}

trait Modad[F[_]] {
  def point[A](a: A): F[A]
  def join[A](ffa: F[F[A]]): F[A]
}
// sealed trait IntList {
//   def fold(end: Int, f: (Int, Int) => Int): Int =
//     this match {
//       case End => end
//       case Pair(hd, tl) => f(hd, tl.fold(end, f))
//     }  

//   def length: Int =
//     fold(0, (_, tl) => 1 + tl)
//   def product: Int =
//     fold(1, (hd, tl) => hd * tl)
//   def sum: Int =
//     fold(0, (hd, tl) => hd + tl)    
// }
// final case object End extends IntList
// final case class Pair(head: Int, tail: IntList) extends IntList

// sealed trait Result[A]
// case class Success[A](result: A) extends Result[A]
// case class Failure[A](reason: String) extends Result[A]

sealed trait Sum[A, B] {
  def fold[C](left: A => C, right: B => C): C =
    this match {
      case Left(a) => left(a)
      case Right(a) => right(a) 
    }  
}
final case class Left[A, B](value: A) extends Sum[A, B]
final case class Right[A, B](value: B) extends Sum[A, B]

sealed trait Maybe[A] {
  def fold[B](full: A => B, empty: B): B =
    this match {
      case Full(v) => full(v)
      case Empty() => empty
    }    
}
final case class Full[A](value: A) extends Maybe[A]
final case class Empty[A]() extends Maybe[A]

// sealed trait LinkedList[A] {
//   def length: Int = 
//    this match {
//     case Pair(hd, tl) => 1 + tl.length
//     case End() => 0
//    }

//   def contains(item: A): Boolean = 
//     this match {
//       case Pair(hd, tl) => if (hd == item) true else tl.contains(item)
//       case End() => false
//     }

//   def apply(index: Int): Result[A] =
//     this match {
//       case Pair(hd, tl) =>
//         if(index == 0)
//           Success(hd)
//         else
//           tl(index - 1)
//       case End() =>
//         Failure("Index out of bounds")
//     }

// }
// final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
// final case class End[A]() extends LinkedList[A]

// case class Pair[A, B](one: A, two: B)

// object Main {

//   def main(args: Array[String]): Unit = {
//     println("ch5")

    // val example = Pair(1, Pair(2, Pair(3, End())))
    // assert(example.length == 3)
    // assert(example.tail.length == 2)
    // assert(End().length == 0)

    // val example = Pair(1, Pair(2, Pair(3, End())))
    // assert(example.contains(3) == true)
    // assert(example.contains(4) == false)
    // assert(End().contains(0) == false)

    // val example = Pair(1, Pair(2, Pair(3, End())))
    // assert(example(0) == 1)
    // assert(example(1) == 2)
    // assert(example(2) == 3)
    // assert(try {
    //   example(3)
    //   false
    // } catch {
    //   case e: Exception => true
    // })

    // assert(example(0) == Success(1))
    // assert(example(1) == Success(2))
    // assert(example(2) == Success(3))
    // assert(example(3) == Failure("Index out of bounds"))

//   }

// }

/*

map(fa)(identity) == fa

f.andThen(g)

always preserves structure

*/