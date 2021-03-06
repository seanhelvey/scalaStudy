/**
 * The world's simplest possible implementation of the IO monad.
 * A pure way to represent effectful operations as a thunk.
 */
case class IO[A](unsafePerformIO: () => A) {
  def map[B](ab: A => B): IO[B] =
    IO(() => ab(unsafePerformIO()))
  def flatMap[B](afb: A => IO[B]): IO[B] =
    IO(() => afb(unsafePerformIO()).unsafePerformIO())
  def tryIO(ta: Throwable => A): IO[A] =
    IO(() => IO.tryIO(unsafePerformIO()).unsafePerformIO() match {
      case Left(t) => ta(t)
      case Right(a) => a
    })
}
object IO {
  def point[A](a: => A): IO[A] = IO(() => a)

  def tryIO[A](a: => A): IO[Either[Throwable, A]] =
    IO(() => try { Right(a) } catch { case t : Throwable => Left(t) })
}

object Console {
  /**
   * Writes a line of text to the console.
   */
  def putStrLn(line: String): IO[Unit] = IO(unsafePerformIO = () => println(line))

  /**
   * Reads a line of text from the console.
   */
  def getStrLn: IO[String] = IO(unsafePerformIO = () => readLine())
}

trait Describable {
  def describe: String
}

sealed trait Item extends Describable

case class Sword() extends Item {
  def describe = "An ordinary metallic sword"
}

case class Location(x: Int, y: Int)

case class Cell(inv: List[Item], baseDesc: String) extends Describable {
  def describe = baseDesc
}

case class WorldMap(private val value: Vector[Vector[Cell]]) {
  def cellAt(loc: Location): Option[Cell] = ???

  def updateAt(loc: Location, f: Cell => Cell): Option[WorldMap] = ???

  def display: String =
    value.apply(0).apply(0).toString()
}

case class PlayerState(loc: Location, inv: List[Item])

case class GameState(player: PlayerState, map: WorldMap)

sealed trait Command
case object LookAround extends Command
case object Quit extends Command
case object Help extends Command

object Application {
  def parse(text: String): Either[String, Command] =    
    text match {
      case "l" => Right(LookAround)
      case "q" => Right(Quit)
      case "h" => Right(Help)
      case _ => Left("command not found")
    }


  final case class ActResult(text: List[String], state: Option[GameState])

  def act(command: Command, oldState: GameState): ActResult = command match {
    case LookAround => ActResult(text = List(oldState.map.display), state = Some(oldState))
    case Quit => ActResult(text = List("quit"), state = None)
    case Help => ActResult(text = List("l -> look around, q -> quit, h -> help"), state = Some(oldState))
    case _ => ActResult(text = List("unknown"), state = None)
  }

  def loop(oldState: GameState): IO[GameState] =  
    for {
      line <- Console.getStrLn

      ActResult(text, newStOpt) =

        parse(line) match {
          case Left(error) =>
            ActResult("Sorry, that command was not recognized:" :: error :: Nil, Some(oldState))

          case Right(command) =>
            act(command, oldState)
        }

      _ <-  text.foldLeft[IO[Unit]](IO.point(())) {
              case (io, line) =>
                for {
                  _ <- io
                  _ <- Console.putStrLn(line)
                } yield ()
            }

      finalState  <-  newStOpt match {
                        case None => IO.point(oldState)
                        case Some(newState) => loop(newState)
                      }
    } yield finalState
    

  /**
   * A pure IO value representing the application.
   */
  def start: IO[Unit] = 
    {
      val initialState = 
        GameState(
          PlayerState(Location(0,0), List()),
          WorldMap(Vector(Vector(Cell(List[Item](), "Home"))))
        )
      for { 
        _ <- Console.putStrLn("You are standing in a field of green") 
        _ <- Application.loop(initialState) 
      } yield ()
    }



}

object Main {
  /**
   * The main function of the application, which performs the effects
   * described by the application's IO monad.
   */
  def main(args: Array[String]): Unit = Application.start.unsafePerformIO()
}