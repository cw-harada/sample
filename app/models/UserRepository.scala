package models

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import models.User
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import java.sql.Timestamp

@Singleton
class UserRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  class UserTable(tag: Tag) extends Table[User](tag, "user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def email = column[String]("email")

    def password = column[String]("password")

    def name = column[String]("name")

    def token = column[String]("token")

    def expired_at = column[Timestamp]("expired_at")

    def * = (id.?, email, password, name, token, expired_at) <> ((User.apply _).tupled, User.unapply)
  }

  private val users = TableQuery[UserTable]

  def insert(user: User): Future[Unit] = db.run(users += user).map { _ => () }

  def list(): Future[Seq[User]] = db.run(users.result)
}
