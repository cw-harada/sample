package controllers

import javax.inject._

import models.UserRepository
import play.api.mvc._
import play.api.libs.json._
import models.User
import play.api.data.Form
import play.api.data.Forms._
import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */

//case class UserRegisterForm(email: String, password: String, name: String)
case class UserForm(email: String, password: String, name: String)

class UserController @Inject()(cc: ControllerComponents, userRepository: UserRepository) extends AbstractController(cc) {

  val userForm: Form[UserForm] = Form(
    mapping(
      "email" -> email,
      "password" -> text(minLength = 10),
      "name" -> text(maxLength = 10),
    )(UserForm.apply)(UserForm.unapply)
  )

  def list = Action.async { implicit request =>
    userRepository.list().map(user => Ok(Json.toJson(user)))
  }

  def register = Action.async(parse.json) { implicit request =>
    userForm.bindFromRequest().fold(
      error => (Future {
        BadRequest(Json.obj("status" -> "NG"))
      }),
      userInput => {
        val user: User = User(None, userInput.email, userInput.password, userInput.name)
        userRepository.insert(user).map{ _ =>
           Ok(Json.toJson("status" -> "OK", user))
        } recoverWith {
          case _ => Future {BadRequest(Json.obj("status" -> "NG", "message" -> "register fail"))}
        }
      }
    )
  }

}
