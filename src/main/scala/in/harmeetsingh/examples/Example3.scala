package in.harmeetsingh.examples

import cats.effect.IO
import cats.implicits._

object Example3 extends App {

    val hello = IO { println("Hello") }
    val world = IO { println("World") }

    val result1 = (hello *> world)
    val result2 = hello.flatMap(_ => world)

    result1.unsafeRunSync()
    result2.unsafeRunSync()
}
