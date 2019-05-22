package models

import play.api.libs.json.{Format, Json}

import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Person(var id: Int =0, var name: String="", var country: String="")
object Person {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[Person] = Json.format[Person]
  implicit val jsonWrites: Writes[Person] = Json.writes[Person]
}


