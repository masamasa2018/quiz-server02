package models

import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ProvenShape, Rep, TableQuery, Tag}

// Quizテーブルの定義
class Inquiries(tag: Tag) extends Table[Inquiry](tag, "inquiries") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def email: Rep[String] = column[String]("EMAIL")
  def name: Rep[String] = column[String]("NAME")
  def message: Rep[String] = column[String]("MESSAGE")

  override def * : ProvenShape[Inquiry] = (id, email, name, message) <> ((Inquiry.apply _).tupled, Inquiry.unapply)
}

object Inquiries {
  // クイズテーブルへのクエリを表すTableQueryを定義
  // この定義により、quizzes変数を使用してクイズテーブルに対するクエリ操作を行うことができます。
  val inquiries = TableQuery[Inquiries]
}





//uri = body.get("uri")
//host = body.get("host")
//uid = body.get("uid")