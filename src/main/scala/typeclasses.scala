
/*

Type classes are bundles of types, operations on values of the types, and 
laws that govern the operations and help to reason about use of the type 
class.

In Scala, type classes are modeled by traits with type parameters.

For a given type, you can construct a instance of a type class for the type
by creating an implementation of the type class trait.

Every type can have its own instance for a type class, but a type should never
have more than one instance of the same class, since that will confuse both
Scala and other programmers.

Type class instances should be stored in the companion object of the type class
(if you do not control the type) or the companion object of the type (if you
control the type).

Type class instances should always be made implicit, so they can be implicitly
passed to methods that require them and as constraints.

It's common for a type class to have an `apply` method to summon an implicit 
instance for a given type, e.g. `Ord[A].compare(a, b)`.

Type classes can be made very easy to use by defining syntax classes. These are
implicit "wrapper" classes with syntax methods. The Scala compiler will insert 
calls to the constructor and wrap our types for us if we use a method that does
not exist on a given type, and if an implicit class is in scope that provides 
the required method. Syntax classes are similar to extension methods in other 
object-oriented programming languages, allowing you to "add" methods to something
without actually changing that thing.

*/

// trait Semigroup[A] {
//   def append(a1: A, a2: A): A
// }
// trait Monoid[A] extends Semigroup[A] {
//   def zero: A
// }
// object Monoid {
//   implicit def MonoidList[A]: Monoid[List[A]] = new Monoid[List[A]] {
//     def append(a1: List[A], a2: List[A]): List[A] = a1 ++ a2
    
//     def zero: List[A] = Nil
//   }
  
//   object syntax {
//     implicit class MonoidSyntax[A: Monoid[A]](self: A) {
//       def <> (that: A): A = implicitly[Monoid[A]].append(self, that)
//     }
  
//     def zero[A: Monoid[A]] = implicitly[Monoid[A]].zero
//   }
// }

// sealed trait Order
// case object EQ extends Order
// case object LT extends Order
// case object GT extends Order

// trait Ord[A] {
//   def compare(a1: A, a2: A): Order
  
//   // a < b && b < c ==> a < c
//   // a == b ==> b == a
// }
// object Ord {
//   implicit val OrdInt: Ord[Int] = new Ord[Int] {
//     def compare(a1: Int, a2: Int): Order = ???
//   }
//   def apply[A](implicit O: Ord[A]): Ord[A] = O
  
//   implicit class OrdSyntax[A](value: A)(implicit O: Ord[A]) {
//     def > (that: A): Order = ???
//     def < (that: A): Order = ???
//     def == (that: A): Order = ???
//   }
// }

// class Rectangle()
// object Rectangle {
//   implicit val OrdRectangle: Ord[Rectangle] = new Ord[Rectangle] {
//     def compare(a1: Rectangle, a2: Rectangle): Order = ???
//   }  
// }

// object util {
//   import Ord._
  
//   def sort[A: Ord[A]](as: List[A]): List[A] = {
//     ...
//     if (new OrdSyntax(a1).<(a2)) ... a1
//     else ...
//     // Ord[A].compare(a1, a2) : Order
//   }
  
//   sort(1 :: 2 :: 3 :: Nil)
// }