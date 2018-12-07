package in.harmeetsingh.examples

import pureconfig.error.ConfigReaderFailures
import pureconfig.generic.auto._

object Example9 extends App {

    sealed trait Database
    final case class Settings(driver: String, url: String, user: String, password: String) extends Database

    case class ApplicationConf(database: Database)

    val databaseConfig : Either[ConfigReaderFailures, ApplicationConf] = pureconfig.loadConfig[ApplicationConf]
    databaseConfig.foreach(println)

}
