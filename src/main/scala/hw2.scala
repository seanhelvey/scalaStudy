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
}

case class PlayerState(loc: Location, inv: List[Item])

case class GameState(player: PlayerState, map: WorldMap)

sealed trait Command
case object LookAround extends Command
case object Quit extends Command

object Application {
  def parse(text: String): Either[String, Command] = ???

  final case class ActResult(text: List[String], state: Option[GameState])

  def act(command: Command, oldState: GameState): ActResult = command match {
    case Quit => ActResult(text = Nil, state = None)
    case _ => ???
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
                  // _ <- io
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
    Console.putStrLn("hello world!")
}

object Main {
  /**
   * The main function of the application, which performs the effects
   * described by the application's IO monad.
   */
  def main(args: Array[String]): Unit = Application.start.unsafePerformIO()
}