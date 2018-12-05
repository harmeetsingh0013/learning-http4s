package in.harmeetsingh.examples

import cats.effect.IO

object Example4 extends App {

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
}
