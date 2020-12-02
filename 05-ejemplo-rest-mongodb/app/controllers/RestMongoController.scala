package controllers

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import play.api._
import play.api.mvc._
import play.api.libs.json._
import reactivemongo.play.json._
import reactivemongo.play.json.collection._
import reactivemongo.bson.{BSONDocument, BSONObjectID}
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
  /*
  def add = Action.async(parse.json) {
    import play.api.libs.json.Reads._
    val transformer: Reads[JsObject] =
      Reads.jsPickBranch[JsString](__ \ "title") and
        Reads.jsPickBranch[JsString](__ \ "description")  reduce
        request.body.transform(transformer).map { result =>
          collection.flatMap(_.insert.one(result)).map { lastError =>
            Logger.debug(s"Successfully inserted with LastError: $lastError")
            Created
          }
        }.getOrElse(Future.successful(BadRequest("invalid json")))
    _.body.validate[Post].map{
      post => postRepository.create(post).map{
        post => Ok(Json.toJson(post))
      }
    }.getOrElse(Future.successful(BadRequest("Invalid format")))
  }

def read (id: BSONObjectID) = Action.async {
  postRepository.read(id).map {
    post => post.map{
      post => Ok(Json.toJson(post))
    }
  }.getOrElse(NotFound)
}

def update (id: BSONObjectID) = Action.async(parse.json) {
  _.body.validate[Post].map{
    post => postRepository.udpate(id,post).map{
      case Some(post) => Ok(Json.toJson(post))
      case _ => NotFound
    }
  }.getOrElse(Future.successful(BadRequest("Invalid format")))
}
def delete (id: BSONObjectID) = Action.async{
  postRepository.destroy(id).map{
    case Some(post) => Ok(Json.toJson(post))
    case _ => NotFound
  }
}

 */
}
