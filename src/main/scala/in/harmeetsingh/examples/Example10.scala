package in.harmeetsingh.examples

import cats.implicits._
import doobie._
import doobie.hikari.HikariTransactor
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.http4s.HttpService
import org.http4s.circe.jsonOf
import org.http4s.dsl.io.{->, /, Ok, POST, Root, _}
import org.http4s.server.blaze.BlazeBuilder
import scala.concurrent.ExecutionContext.Implicits.global
import cats.effect.IO
import fs2.StreamApp.ExitCode
import fs2.{Stream, StreamApp}
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.headers.`Content-Type`


case class User1(name : String)

object User1 {
    implicit val encoder = jsonOf[IO, User1]

}

case class Hello1(greeting: String)

class Example10(userRepo : UserRepo) {

    private val PathPrefix = "/"

    val httpRoutes : HttpService[IO] = HttpService[IO] {
            case req @ POST -> Root / "user" => for {
                user <- req.as[User1]
                updatedUser <- userRepo.addUser(user)
                resp <- Ok(Hello1(updatedUser.name).asJson)
            } yield resp

            case GET -> Root / "users" =>
                Ok(
                    Stream("[") ++
                    userRepo.getUsers.map(_.asJson.noSpaces).intersperse(",") ++ Stream("]"),
                    `Content-Type`(MediaType.`application/json`)
                )
        }
}

class UserRepo(transactor : Transactor[IO]) {

    def dropTable : ConnectionIO[Int] =
        sql"""
            DROP TABLE IF EXISTS user
        """.update.run

    def createTable : ConnectionIO[Int] =
        sql"""
           CREATE TABLE user (
             name VARCHAR(20) NOT NULL UNIQUE
           )
        """.update.run

    def addUser(user : User1) : IO[User1] =
        sql"""INSERT INTO user (name) VALUES (${user.name})""".update.run
            .transact(transactor).map(_ => user)

    def getUsers : Stream[IO, User1] = sql"""
            SELECT name FROM user
        """.query[User1].stream.transact(transactor)
}

object Example10 extends StreamApp[IO] {

    val transactor : IO[Transactor[IO]] = HikariTransactor.newHikariTransactor[IO](
        "org.h2.Driver",
        "jdbc:h2:mem:todo;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "sa", ""
    )

    override def stream(args : List[String], requestShutdown : IO[Unit]) : Stream[IO, ExitCode] = for {
        tr <- Stream.eval(transactor)
        userRepo = new UserRepo(tr)
        _ <- Stream.eval((userRepo.dropTable, userRepo.createTable).mapN(_ + _).transact(tr))
        example = new Example10(userRepo)
        exitCode <- BlazeBuilder[IO]
            .bindHttp(8080, "localhost")
            .mountService(example.httpRoutes, example.PathPrefix)
            .serve
    } yield exitCode
}
