package models
import play.api.libs.json.{Json, Reads, Writes}

// Quizクラスに対するJSONシリアライゼーション/デシリアライゼーションの定義
object AccessHistory {
  implicit val reads: Reads[AccessHistory] = Json.reads[AccessHistory]
  implicit val writes: Writes[AccessHistory] = Json.writes[AccessHistory]
}

// Quizクラスの定義
case class AccessHistory(id: Int, uri: String, host: String, uid: String)
