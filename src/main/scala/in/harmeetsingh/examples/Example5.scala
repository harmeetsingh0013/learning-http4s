package in.harmeetsingh.examples

import cats.effect.IO
import cats.implicits._
import scala.concurrent.ExecutionContext

object Example5 extends App {

    implicit def executionContext: ExecutionContext = ExecutionContext.Implicits.global
    implicit val timer: cats.effect.Timer[IO] = IO.timer(executionContext)

    val source = IO.shift *> IO {
        Thread.sleep(1000)
        println(s"Inside shifting thread ${Thread.currentThread().getName}")
        1
    }

    val start = source.runAsync {
        case Left(e) => IO(e.printStackTrace())
        case Right(_) => IO.unit
    }
    println(s" #####  ${start.unsafeRunSync}  ####")
    Thread.sleep(2000)
}
