package models

import play.api.libs.json._
import java.sql.Timestamp

case class User(id: Long, email: String, password: String, name:String, token: String, expired_at:Timestamp)

object User{
  implicit val userWrites = Json.format[User]
}
