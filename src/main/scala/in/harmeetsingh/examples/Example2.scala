package in.harmeetsingh.examples

import fs2.StreamApp.ExitCode
import fs2.{Stream, StreamApp}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeBuilder

class Example2 extends Http4sDsl[Task] {

    private val PathPrefix = "/"

    val httpRoutes : HttpService[Task] = HttpService[Task] {
        case GET -> Root / "hello" / name =>
            println("Executed via monix task")
            Ok(s"Hello $name")
    }
}

object Example2 extends StreamApp[Task] {

    val example = new Example2
    import example._

    override def stream(args : List[String], requestShutdown : Task[Unit]) : Stream[Task, ExitCode] = {
        BlazeBuilder[Task]
            .bindHttp(8080, "localhost")
            .mountService(httpRoutes, PathPrefix)
            .serve
    }
}
