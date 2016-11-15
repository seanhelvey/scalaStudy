// package main

// import scala.util.Try
// import scala.annotation.tailrec

// object Main {
//   case class Cols[A](left: A, middle: A, right: A) {
//     def horizontal = left :: middle :: right :: Nil
//   }
//   case class Rows[A](top: Cols[A], middle: Cols[A], bottom: Cols[A]) {
//     def horizontals = top.horizontal :: middle.horizontal :: bottom.horizontal :: Nil

//     def verticals = makeVertical(_.left) :: makeVertical(_.middle) :: makeVertical(_.right) :: Nil

//     def diagonals = (top.left :: middle.middle :: bottom.right :: Nil) ::
//                     (top.right :: middle.middle :: bottom.left :: Nil) :: Nil

//     private def makeVertical(f: Cols[A] => A): List[A] = (top :: middle :: bottom :: Nil).map(f)
//   }

//   case class Board(matrix: Rows[Option[PlayerChoice]]) {
//     def winLines = matrix.horizontals ++ matrix.verticals ++ matrix.diagonals

//     def won : Option[PlayerChoice] = winLines.foldLeft(Option.empty[PlayerChoice]) {
//       case (_, Some(Xs) :: Some(Xs) :: Some(Xs) :: Nil) => Some(Xs)
//       case (_, Some(Os) :: Some(Os) :: Some(Os) :: Nil) => Some(Os)
//       case (v, _) => v
//     }
//   }

//   sealed trait PlayerChoice
//   case object Xs extends PlayerChoice
//   case object Os extends PlayerChoice

//   case class GameState(board: Board, player: PlayerChoice)

//   sealed trait UserError
//   case object InvalidMove extends UserError
//   case object GameOver extends UserError
//   case object ExitGame extends UserError

//   trait PolyLens[F[_]] {
//     def get[A](matrix: F[A]): A
//     def set[A](matrix: F[A], value: A): F[A]
//   }

//   sealed trait UserInput
//   case object Quit extends UserInput
//   case object UnknownInput extends UserInput
//   sealed trait MakeMove extends UserInput with PolyLens[Rows]

//   def update(userInput: UserInput, state: GameState): Either[UserError, GameState] = {
//     userInput match {
//       case Quit => Left(ExitGame)
//       case UnknownInput => Left(InvalidMove)
//       case move : MakeMove =>
//         move.get(state.board.matrix) match {
//           case Some(_) => Left(InvalidMove)
//           case None =>
//             Right(
//               state.copy(board = state.board.copy(matrix = move.set(state.board.matrix, Some(state.player))))
//             )
//         }
//     }
//   }

//   def getInput: UserInput = {
//     println("""Enter "quit" or row number (1 - 3):""")

//     readLine().toLowerCase match {
//       case "quit" => Quit
//       case "exit" => Quit
//       case rowStr =>
//         println("Enter column number (1 - 3):")

//         (for {
//           row <- Try(rowStr.toInt).toOption.map(v => (v - 1) % 3)
//           col <- Try(readLine().toInt).toOption.map(v => (v - 1) % 3)
//         } yield {
//           val colsLens: PolyLens[Cols] = col match {
//             case 0 => new PolyLens[Cols]{
//               def get[A](cols: Cols[A]): A = cols.left
//               def set[A](cols: Cols[A], v: A): Cols[A] = cols.copy(left = v)
//             }
//             case 1 => new PolyLens[Cols]{
//               def get[A](cols: Cols[A]): A = cols.middle
//               def set[A](cols: Cols[A], v: A): Cols[A] = cols.copy(middle = v)
//             }
//             case _ => new PolyLens[Cols]{
//               def get[A](cols: Cols[A]): A = cols.right
//               def set[A](cols: Cols[A], v: A): Cols[A] = cols.copy(right = v)
//             }
//           }

//           row match {
//             case 0 => new MakeMove {
//               def get[A](rows: Rows[A]): A = colsLens.get(rows.top)
//               def set[A](rows: Rows[A], v: A): Rows[A] = rows.copy(top = colsLens.set(rows.top, v))
//             }
//             case 1 => new MakeMove {
//               def get[A](rows: Rows[A]): A = colsLens.get(rows.middle)
//               def set[A](rows: Rows[A], v: A): Rows[A] = rows.copy(middle = colsLens.set(rows.middle, v))
//             }
//             case _ => new MakeMove {
//               def get[A](rows: Rows[A]): A = colsLens.get(rows.bottom)
//               def set[A](rows: Rows[A], v: A): Rows[A] = rows.copy(bottom = colsLens.set(rows.bottom, v))
//             }
//           }}).getOrElse(UnknownInput)
//     }
//   }

//   def changeTurn(state: GameState): GameState =
//     state.copy(player = state.player match { case Xs => Os; case Os => Xs })

//   def printBoard(state: GameState): Unit = {
//     def printDivider = println("-|-|-")

//     def showCell(cell: Option[PlayerChoice]): String = cell match {
//       case None => " "
//       case Some(Xs) => "X"
//       case Some(Os) => "O"
//     }

//     println(state.board.won match {
//       case Some(Xs) => "X won the game!"
//       case Some(Os) => "O won the game!"
//       case None =>
//         state.player match {
//           case Xs => "It is time for X to move!"
//           case Os => "It is time for O to move!"
//         }
//     })

//     state.board.matrix.horizontals.zipWithIndex.foreach {
//       case (horizontal, index) =>
//         if (index == 1) printDivider

//         println(horizontal.map(showCell).mkString("|"))

//         if (index == 1) printDivider
//     }
//   }

//   @tailrec
//   def loop(state: GameState): Unit = {
//     printBoard(state)

//     update(getInput, state) match {
//       case Left(InvalidMove) => println("Your move was not valid!"); loop(state)
//       case Left(GameOver)    => println("The game is already over!"); loop(state)
//       case Left(ExitGame)    => println("Goodbye!")

//       case Right(state) => loop(changeTurn(state))
//     }
//   }

//   def main(args: Array[String]): Unit = {
//     println("Enter 1 - 9 to choose a position")

//     val initialState =
//       GameState(Board(Rows[Option[PlayerChoice]](
//         Cols(None, None, None),
//         Cols(None, None, None),
//         Cols(None, None, None))), Xs)

//     loop(initialState)
//   }
// }