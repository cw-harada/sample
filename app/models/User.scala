package models

import play.api.libs.json._
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.{Locale, TimeZone}

case class User(id: Option[Long], email: String, password: String, name:String, token: String, expired_at:Timestamp)

object User{
  implicit object timestampFormat extends Format[Timestamp] {
    var formatJST = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatJST.setTimeZone(TimeZone.getTimeZone("JST"));

    var formatLocaleGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatLocaleGMT.setTimeZone(TimeZone.getDefault)

    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(formatJST.parse(str).getTime))
    }

    def readsValue(date: String): Timestamp =
      new Timestamp(formatJST.parse(date).getTime)

    def writes(ts: Timestamp) = JsString(formatLocaleGMT.format(ts))
  }

  implicit val userJsonFormat = Json.format[User]
}
