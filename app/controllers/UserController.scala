package controllers

import javax.inject._

import models.UserRepository
import models.User
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.http._
import play.api.libs.json._
import concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */

//case class UserRegisterForm(name: String, email: String, password: String)
case class UserRegisterForm(name: String, password: String)

class UserController @Inject()(cc: ControllerComponents, userRepository: UserRepository) extends AbstractController(cc) {

  private val form: Form[UserRegisterForm] = {
    import play.api.data.Forms._

    Form(
      mapping(
        "name" -> text(maxLength = 10),
        //"email" -> email,
        "password" -> text(minLength = 10)
      )(UserRegisterForm.apply)(UserRegisterForm.unapply)
    )

    /*
    val userForm = Form(
      mapping(
        "id" -> Option[Long],
        "name" -> text,
        "age" -> number
      )(User.apply)(User.unapply)
    )
    */
  }

  implicit val userWrites = new Writes[User] {
    def writes(user: User) = Json.obj(
      "id" -> user.id,
      "name" -> user.name,
      "age" -> user.age
    )
  }

  def list = Action.async { implicit request =>
    userRepository.list().map(user => Ok(Json.toJson(user)))
  }

  def register() = Action { implicit request =>
    form.bindFromRequest().fold(
      fail => {
        BadRequest(Json.obj("status" -> "NG"))
      },
      success => {
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }

  /*
  def register = Action[AnyContent] = ActionBuilder.async { implicit request =>

    def failure(badForm: Form[UserRegisterForm]) = {
      Ok(Json.obj("status" -> "NG", "message" -> ("Invalid Register.")))
    }

    def success(input: UserRegisterForm) = {
      Ok(Json.obj("status" -> "NG", "message" -> ("Invalid Register.")))
    }

    form.bindFromRequest().fold(failure, success)
  }
  */
}
