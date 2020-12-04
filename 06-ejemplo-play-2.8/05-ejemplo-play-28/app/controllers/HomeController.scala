package controllers

import javax.inject._
import models.Person
import play.api._
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.ExecutionContext
import scala.collection.mutable
import repositories.PostRepository

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(
                              implicit ec: ExecutionContext,
                                cc: ControllerComponents,
                                postRepo: PostRepository
                              ) extends AbstractController(cc) with Logging {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.obj("swaggerui_url"-> "http://localhost:9003/docs/swagger-ui/index.html?url=/assets/swagger.json"))
  }
  def mapa() = Action { implicit request: Request[AnyContent] =>
    var result = new mutable.HashMap[String,String]()
    result.put("param","value")
    result.put("param2","value2")
    Ok(Json.toJson(result));
  }
  def objeto () = Action {  implicit request: Request[AnyContent] =>
    var persona = new Person()
    Ok(Json.toJson(persona))
  }
  def cogeObjeto () = Action {  implicit request: Request[AnyContent] =>
    val jsonBody: Option[JsValue] = request.body.asJson
    jsonBody
      .map { json =>
        Ok("Got: " + (json \ "name").as[String])
      }
      .getOrElse {
        BadRequest("Expecting application/json request body")
      }
    //var persona = new Person()
    //Ok(jsonBody)
  }
  def cogePersona () = Action {  implicit request: Request[AnyContent] =>
    val jsonBody: Option[JsValue] = request.body.asJson
    var persona= Json.fromJson[Person](jsonBody.get)
    var person: Person=persona.asOpt.get
    Ok(Json.toJson(person))
  }
}
