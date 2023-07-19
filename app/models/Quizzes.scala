package models

import play.api.libs.json.Json
import slick.jdbc.H2Profile.BaseColumnType
import slick.lifted.{ProvenShape, Rep, TableQuery, Tag}
import slick.lifted.{TableQuery, Tag}

import slick.jdbc.MySQLProfile.api._
import slick.lifted.{MappedProjection, ProvenShape}

// Quizテーブルの定義
class Quizzes(tag: Tag) extends Table[Quiz](tag, "QUIZZES") {
  // Map[String, String]型をJSON文字列に変換するためのカスタムカラムタイプの定義
  implicit val mappedJsonColumnType: BaseColumnType[Option[Map[String, String]]] =
    MappedColumnType.base[Option[Map[String, String]], String](
      optMap => Json.toJson(optMap.getOrElse(Map.empty)).toString(),
      str => Json.parse(str).asOpt[Map[String, String]]
    )
  // テーブルのカラム定義
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def code: Rep[Int] = column[Int]("CODE")
  def category: Rep[Int] = column[Int]("CATEGORY")
  def question: Rep[String] = column[String]("QUESTION")
  def info: Rep[String] = column[String]("INFO")
  def correct_code: Rep[Int] = column[Int]("CORRECT_CODE")
  def answers: Rep[Option[Map[String, String]]] = column[Option[Map[String, String]]]("ANSWERS")(mappedJsonColumnType)
  // テーブルのシェイプ定義
  def * : ProvenShape[Quiz] = (id, code, category, question, info, correct_code, answers) <> ((Quiz.apply _).tupled, Quiz.unapply)
}
object Quizzes {
  // クイズテーブルへのクエリを表すTableQueryを定義
  // この定義により、quizzes変数を使用してクイズテーブルに対するクエリ操作を行うことができます。
  val quizzes = TableQuery[Quizzes]
}