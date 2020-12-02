package repositories

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.collection.JSONCollection
import models.Post
import play.api.libs.json._
import play.api.libs.functional.syntax._
import reactivemongo.play.json._
import reactivemongo.bson.{BSONDocument}
import reactivemongo.api.bson.BSONObjectID
import reactivemongo.api.{ReadPreference, Cursor}
import reactivemongo.api.commands.WriteResult


class PostRepository @Inject()(
  implicit ec: ExecutionContext,
  reactiveMongoAPI: ReactiveMongoApi
) {
  private def collection: Future[JSONCollection] = reactiveMongoAPI.database.map(_.collection("posts"))

  def list (limit: Int = 100): Future[Seq[Post]] = {
    collection.flatMap(
      _.find(Json.obj(),Option.empty[JsObject])
        .cursor[Post](ReadPreference.primary)
        .collect[Seq](limit,Cursor.FailOnError[Seq[Post]]())
      )
  }
  def create (post: Post): Future[WriteResult] ={
    collection.flatMap(_.insert(ordered = false).one(post))
  }
  def createJson (post: JsObject) ={
    collection.flatMap(_.insert.one(post))
  }
  def read (id: BSONObjectID): Future[Option[Post]] = {
    collection.flatMap(_.find(Json.obj("_id" -> id.stringify),Option.empty[JsObject]).one[Post])
  }
  def update(id: BSONObjectID, post:JsObject): Future[Option[Post]] = {
    collection.flatMap(_.findAndUpdate(
        BSONDocument("_id" -> id.stringify),
        BSONDocument(
          f"$$set" -> post
        ),
      true
      ).map(_.result[Post])
    )
  }

  def destroy(id: BSONObjectID): Future[Option[Post]] = {
    collection.flatMap(
      _.findAndRemove(BSONDocument("_id" -> id.stringify),Option.empty[JsObject]).map(_.result[Post])
    )
  }

}