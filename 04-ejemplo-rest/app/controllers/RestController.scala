package controllers

import javax.inject._
import models.Person
import play.api._
import play.api.libs.json._
import play.api.mvc._

import scala.collection.mutable
import javax.inject._
import models.{User, UserForm}
import play.api.Logging
import play.api.mvc._
import services.UserService
import models.Formatters._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class RestController @Inject()(
                                       cc: ControllerComponents,
                                       userService: UserService)
  extends AbstractController(cc) with Logging {

  def index() = Action.async { implicit request: Request[AnyContent] =>
    userService.listAllUsers map { users =>
      Ok(Json.toJson(users))
    }
  }
  def add() = Action.async { implicit request: Request[AnyContent] =>
    val jsonBody: Option[JsValue] = request.body.asJson
    var usuarioJson= Json.fromJson[User](jsonBody.get)
    var user: User=usuarioJson.asOpt.get
    userService.addUser(user).map( _ => {
      Ok(Json.toJson(user))
    })
  }
  def addUser() = Action.async { implicit request: Request[AnyContent] =>
    UserForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => {
        logger.warn(s"Form submission with error: ${errorForm.errors}")
        Future.successful(Ok(views.html.users.index(errorForm, Seq.empty[User])))
      },
      data => {
        val newUser = User(0, data.firstName, data.lastName, data.mobile, data.email)
        userService.addUser(newUser).map( _ => {
          Redirect(routes.RestController.index())
        })
      })
  }
  /*
    def getUser(id: Long) = Action.async { implicit request: Request[AnyContent] =>
      val jsonBody: Option[JsValue] = request.body.asJson
      var usuarioJson= Json.fromJson[User](jsonBody.get)
      var user: User=usuarioJson.asOpt.get
      userService.(user).map( _ => {
        Ok(Json.toJson(user))
      })
      userService.getUser(id) map {
        res => {
          Ok(Json.toJson(res))
        }
      }
    }
     */
  def getUser(id: Long) = Action.async { implicit request: Request[AnyContent] =>
      userService.getUser(id) map {
        res => {
          Ok(Json.toJson(res))
        }
      }
  }

  def deleteUser(id: Long) = Action.async { implicit request: Request[AnyContent] =>
    userService.getUser(id) map {
      res => {
        userService.deleteUser(id) map {
          res =>{
            Ok(Json.toJson(res))
            //Redirect(routes.RestController.index())
          }
        }
        Ok(Json.toJson(res))
      }
    }
  }

}
