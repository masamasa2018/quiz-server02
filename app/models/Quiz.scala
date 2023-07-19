package models

import play.api.libs.json._
//import play.api.libs.json.JodaReads._
//import play.api.libs.json.JodaWrites._
import java.sql.Timestamp

object Quiz {
  implicit val timestampReads: Reads[Timestamp] = Reads.of[Long].map(new Timestamp(_))
  implicit val timestampWrites: Writes[Timestamp] = Writes { timestamp =>
    Json.toJson(timestamp.getTime)
  }


  implicit val reads: Reads[Quiz] = Json.reads[Quiz]
  implicit val writes: Writes[Quiz] = Json.writes[Quiz]



}

case class Quiz(id: Int, code: Int, category: Int, question: String, info: String, correct_code: Int, answers: Option[Map[String, String]])
