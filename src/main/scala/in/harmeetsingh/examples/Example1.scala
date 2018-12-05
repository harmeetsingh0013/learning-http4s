package in.harmeetsingh.examples

import cats.effect._
import fs2.StreamApp.ExitCode
import fs2.{Stream, StreamApp}
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.blaze.BlazeBuilder
import scala.concurrent.ExecutionContext.Implicits.global

class Example1 {
    private val PathPrefix = "/"

    val httpRoutes : HttpService[IO] = HttpService[IO] {
        case GET -> Root / "hello" / name =>
            println("Executed via IO monad")
            Ok(s"Hello $name")
    }
}

object Example1 extends StreamApp[IO]
{
    val example = new Example1
    import example._

    override def stream(args : List[String], requestShutdown : IO[Unit]) : Stream[IO, ExitCode] =
        BlazeBuilder[IO]
            .bindHttp(8080, "localhost")
            .mountService(httpRoutes, PathPrefix)
            .serve
}
