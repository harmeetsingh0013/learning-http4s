package in.harmeetsingh.examples

import cats.effect.IO
import scala.concurrent.ExecutionContext
import cats.effect._
import cats.implicits._

object Example6 extends App {

    // def async[A](k: (Either[Throwable, A] => Unit) => Unit): IO[A]
    val asyncResult : IO[String] = IO async {cb =>
        new Thread {
            start()
            override def run() =
                cb(Right(Thread.currentThread().getName))
        }
    }

    println(s" ######  ${asyncResult.unsafeRunSync()}  ######")
    println(s" ######  ${Thread.currentThread().getName}  ######")


    implicit def executionContext: ExecutionContext = ExecutionContext.Implicits.global
    implicit val timer: cats.effect.Timer[IO] = IO.timer(executionContext)
    // Sample
    val source = IO.shift *> IO {
        Thread.sleep(1000)
        println(s"Inside shifting thread ${Thread.currentThread().getName}")
        1
    }
    // Describes execution
    val start = source.runAsync {
       case Left(e) => IO(e.printStackTrace())
       case Right(value) => IO.pure(value)
    }
     // Safe, because it does not block for the source to finish
    println(start.unsafeRunSync)
    Thread.sleep(2000)
}
