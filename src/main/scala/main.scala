package main

import scala.annotation.tailrec
import scala.io.StdIn.{readLine}

object Main {
  case class Row[A](left: A, middle: A, right: A)
  case class Col[A](top: Row[A], middle: Row[A], bottom: Row[A])

  case class Board(matrix: Col[Option[PlayerChoice]])

  sealed trait PlayerChoice
  case object Xs extends PlayerChoice
  case object Os extends PlayerChoice

  case class GameState(board: Board, player: PlayerChoice)

  sealed trait UserError
  case object InvalidMove extends UserError
  case object GameOver extends UserError

  case class CurrentInput(row: Int, col: Int)

  // def update(userInput: CurrentInput, state: GameState): Either[UserError, GameState] = {

  def update(userInput: CurrentInput, state: GameState): Either[UserError, GameState]  = {
    if (state.board.matrix.top.left != None) 
      Left(InvalidMove) 
    else 
      Right(GameState(Board(Col[Option[PlayerChoice]](
        Row(Some(Xs), None, None),
        Row(None, None, None),
        Row(None, None, None))), Xs))
  }

  def getInput: CurrentInput = 
    {
      println("getInput called")
      val row = readLine("Row 1, 2, or 3\n").toInt
      val col = readLine("Col 1, 2, or 3\n").toInt   
      CurrentInput(row, col)
    }

  def showError(err: UserError): Unit = ???

  def printBoard(state: GameState): Unit = 
    println(state.board)

  @tailrec
  def loop(state: GameState): Unit = {
    //println(state.board.matrix.top.left)
    val userInput = getInput

    update(userInput, state) match {
      case Left(error) => showError(error); loop(state)
      case Right(state) => printBoard(state); loop(state)
    }
  }

  def main(args: Array[String]): Unit = {
    val initialState =
      GameState(Board(Col[Option[PlayerChoice]](
        Row(None, None, None),
        Row(None, None, None),
        Row(None, None, None))), Xs)
    printBoard(initialState)
    loop(initialState)
  }
}