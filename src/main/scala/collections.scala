// package object collections {
//   def length[A](list: List[A]): Int =
//     list.foldLeft[Int](0)((len, _) => len + 1)

//   sealed trait IList[A] {
//     def foldLeft[S](initial: S)(f: (S, A) => S): S = this match {
//       case ICons(head, tail) => tail.foldLeft[S](f(initial, head))(f)
//       case INil() => initial
//     }
  
//     def foldRight[S](initial: S)(f: (A, S) => S): S = 
//       reverse.foldLeft(initial)((a, b) => f(b, a))
  
//     def filter(f: A => Boolean): IList[A] = 
//       foldRight[IList[A]](INil[A]()) { (filteredList, a) =>
//         if (f(a)) ICons(a, filteredList)
//         else filteredList
//       }
    
//     def partition(f: A => Boolean): (IList[A], IList[A]) = 
//       foldRight[(IList[A], IList[A])]((INil(), INil())) { (t, a) =>
//         val (yes, no) = t
      
//         if (f(a)) (ICons(a, yes), no)
//         else (yes, ICons(a, no))
//       }
    
//     def concat(v: IList[A]): IList[A] = 
//       foldRight[IList[A]](v)((c, a) => ICons(a, c))
    
//     def sort(implicit o: Ordering[A]): IList[A] = this match {
//       case INil() => INil()
//       case ICons(head, tail) =>
//         val (lessThan, notLessThan) = tail.partition(_ < head)
      
//         val lessThanS    = sort(lessThan)
//         val notLessThanS = sort(notLessThan)
      
//         concat(lessThanS, ICons(head, notLessThanS))
//     }
    
//     def reverse: IList[A] = foldLeft(INil())((list, a) => ICons(a, list))
//   }
//   case class ICons[A](head: A, tail: IList[A]) extends IList[A]
//   case class INil[A]() extends IList[A]
// }