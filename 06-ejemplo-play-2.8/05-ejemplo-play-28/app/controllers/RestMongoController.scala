package controllers

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import scala.collection.mutable
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import reactivemongo.play.json._
import reactivemongo.play.json.collection._
import reactivemongo.bson.{BSONDocument}
import reactivemongo.api.bson.BSONObjectID
import repositories.PostRepository
import models.Post



class RestMongoController @Inject()(
      implicit ec: ExecutionContext,
      cc: ControllerComponents,
      postRepository: PostRepository,
      ) extends AbstractController(cc) with Logging {

  def listPost = Action.async {
    postRepository.list().map  {
      posts => Ok(Json.toJson(posts))
    }
  }

    def add = Action.async(parse.json) {
      import play.api.libs.json.Reads._
      /*
       * request.body is a JsValue.
       * There is an implicit Writes that turns this JsValue as a JsObject,
       * so you can call insert.one() with this JsValue.
       * (insert.one() takes a JsObject as parameter, or anything that can be
       * turned into a JsObject using a Writes.)
       */
      val transformer: Reads[JsObject] =
        Reads.jsPickBranch[JsString](__ \ "title") and
          Reads.jsPickBranch[JsString](__ \ "description") reduce

      _.body.transform(transformer).map { result =>
        postRepository.createJson(result).map { lastError =>
          //Logger.debug(s"Successfully inserted with LastError: $lastError")
          Created
        }
      }.getOrElse(Future.successful(BadRequest(Json.toJson("invalid format"))))
    }

 def read (id: BSONObjectID) = Action.async {
   postRepository.read(id).map{
    post => Ok(Json.toJson(post))
   }
 }

 def update (id: BSONObjectID) = Action.async(parse.json) {
   import play.api.libs.json.Reads._
   /*
    * request.body is a JsValue.
    * There is an implicit Writes that turns this JsValue as a JsObject,
    * so you can call insert.one() with this JsValue.
    * (insert.one() takes a JsObject as parameter, or anything that can be
    * turned into a JsObject using a Writes.)
    */
   val transformer: Reads[JsObject] =
     Reads.jsPickBranch[JsString](__ \ "title") and
       Reads.jsPickBranch[JsString](__ \ "description") reduce

   _.body.transform(transformer).map { result =>
     postRepository.update(id, result).map { lastError =>
       //Logger.debug(s"Successfully inserted with LastError: $lastError")
       Ok(Json.toJson(result))
     }
   }.getOrElse(Future.successful(BadRequest(Json.toJson("invalid format"))))

 }

 def delete (id: BSONObjectID) = Action.async{
   postRepository.destroy(id).map{
     case Some(post) => Ok(Json.toJson(post))
     case _ => NotFound
   }
 }
}
