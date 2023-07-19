package models
import play.api.libs.json.{Json, Reads, Writes}

// Quizクラスに対するJSONシリアライゼーション/デシリアライゼーションの定義
object Inquiry {
  implicit val reads: Reads[Inquiry] = Json.reads[Inquiry]
  implicit val writes: Writes[Inquiry] = Json.writes[Inquiry]
}

// Quizクラスの定義
case class Inquiry(id: Int, email: String, name: String, message : String)
