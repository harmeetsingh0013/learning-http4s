package in.harmeetsingh.examples

import cats.effect.IO
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random


object Example44 extends App {

    // Execute Eagerly
    val hello = Future { println("Hello") }
    val world = Future { println("World") }

//    hello.flatMap(_ => world)

    // Referential transparent
    for {
        _ <- Future { println(s"Future Execution: ${Random.nextInt(13)} ") }
    } yield ()


    val ioResult = for {
        _ <- IO { println(s"IO Execution: ${Random.nextInt(13)} ") }
    } yield ()
}
