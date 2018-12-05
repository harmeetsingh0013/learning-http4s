package in.harmeetsingh.examples

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object Example4 extends App {

    // Execute Eagerly
    val hello = Future { println("Hello") }
    val world = Future { println("World") }

//    hello.flatMap(_ => world)

}
