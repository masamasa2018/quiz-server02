package models

import play.api.libs.json.Json
import slick.jdbc.H2Profile.BaseColumnType
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ProvenShape, Rep, TableQuery, Tag}

// Quizテーブルの定義
class AccessHistories(tag: Tag) extends Table[AccessHistory](tag, "access_histories") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def uri: Rep[String] = column[String]("URI")
  def host: Rep[String] = column[String]("HOST")
  def uid: Rep[String] = column[String]("UID")

  override def * : ProvenShape[AccessHistory] = (id, uri, host, uid) <> ((AccessHistory.apply _).tupled, AccessHistory.unapply)
}

object AccessHistories {
  // クイズテーブルへのクエリを表すTableQueryを定義
  // この定義により、quizzes変数を使用してクイズテーブルに対するクエリ操作を行うことができます。
  val accessHistories = TableQuery[AccessHistories]
}



//uri = body.get("uri")
//host = body.get("host")
//uid = body.get("uid")