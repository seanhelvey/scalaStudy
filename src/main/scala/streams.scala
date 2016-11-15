// package streams

// import util.Random

// object Main {

//   def main(args: Array[String]): Unit = {
//     println("streams")

//     def fibFrom(a: Int, b: Int): Stream[Int] = 
//       a #:: fibFrom(b, a + b)

//     //println(fibFrom(1,1).take(7).toList)

//     def primeStream(s: Stream[Int]): Stream[Int] =
//       Stream.cons(s.head, primeStream(s.tail filter { _ % s.head != 0 }))
    
//     val primes = primeStream(Stream.from(2)).take(5).toList
//     //println(primes)

//     def numsFrom(n: Int): Stream[Int] = 
//       Stream.cons(n, numsFrom(n+1))

//     lazy val N = numsFrom(0)
//     //N take 10 print
//     //println(N.take(10))

//     val stream = 1 #:: 2 #:: 3 #:: Stream.empty
//     //println(stream)
//     //println(stream(1))
//     //println(stream.map { _ * 2 }.toList)

//     val output = List(0,7,19,25,40,41,42,13,0) dropWhile { _ < 20 } takeWhile { _ > 20 } toList

//     println("output: " + output)
//   }
// }












