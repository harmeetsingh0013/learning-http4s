package in.harmeetsingh.examples

import cats.effect.IO
import org.http4s.{Method, Request, Response, Status, Uri}
import org.scalatest.{Matchers, WordSpecLike}

class Example1Spec extends WordSpecLike with Matchers {

    "Request" should {
        "should return greeting with Hello " in {
            val mockRequest = Request[IO](method = Method.GET, uri = Uri.uri("/hello/Singh") )
            val response = new Example1().httpRoutes.run(mockRequest).value.unsafeRunSync()
            response.get.status should === (Status.Ok)
            response.get.as[String].unsafeRunSync() should === ("Hello Singh")
        }
    }
}
