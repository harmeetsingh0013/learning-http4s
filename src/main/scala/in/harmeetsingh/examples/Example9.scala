package in.harmeetsingh.examples

import pureconfig.error.ConfigReaderFailures
import pureconfig.generic.auto._

object Example9 extends App {

    sealed trait Database
    final case class Settings(url: String, username: String, password: String) extends Database

    case class ApplicationConf(database: Database)

    val databaseConfig : Either[ConfigReaderFailures, ApplicationConf] = pureconfig.loadConfig[ApplicationConf]
    databaseConfig.map(println)

}
