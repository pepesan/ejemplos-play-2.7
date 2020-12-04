package models


import reactivemongo.play.json._
import play.api.libs.json._
import reactivemongo.bson.{BSONDocument, BSONObjectID}
case class Post (
  _id: Option[BSONObjectID],
  title: String,
  description: String
)
object Post {
  implicit val format: OFormat[Post] = {
    Json.format[Post]
  }
}
