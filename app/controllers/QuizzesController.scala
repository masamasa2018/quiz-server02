package controllers

import javax.inject._
import play.api.mvc._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.Json
import models.{Quiz, Quizzes}

import java.time.Instant

import java.sql.Timestamp
import java.time.Instant

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
//クイズテーブルの操作
@Singleton
class QuizzesController @Inject()(
                                   protected val dbConfigProvider: DatabaseConfigProvider,
                                   val controllerComponents: ControllerComponents
                                 )(implicit ec: ExecutionContext) extends BaseController with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  //レコード登録
  def create() = Action.async(parse.json) { implicit request =>
    val code: Option[Int] = (request.body \ "code").asOpt[Int]
    val category: Option[Int] = (request.body \ "category").asOpt[Int]
    val question: Option[String] = (request.body \ "question").asOpt[String]
    val info: Option[String] = (request.body \ "info").asOpt[String]
    val correctCode: Option[Int] = (request.body \ "correct_code").asOpt[Int]
    val answers = (request.body \ "answers").as[Map[String, String]]

    (code, category, question, info, correctCode) match {
      case (Some(code), Some(category), Some(question), Some(info), Some(correctCode)) =>
        val quiz = Quiz(0, code, category, question, info, correctCode, Some(answers))
        val action = (Quizzes.quizzes returning Quizzes.quizzes.map(_.id) into ((quiz, id) => quiz.copy(id = id))) += quiz
        db.run(action).map(id => Ok(s"Quiz created with id: $id"))
      case _ =>
        Future.successful(BadRequest("Missing field in the request body"))
    }
  }


  //取得(id)
  def read(id: Int) = Action.async { implicit request =>

  db.run(Quizzes.quizzes.filter(_.id === id).result.headOption).map {
      case Some(quiz) => {
        val json = Json.toJson(quiz)
        Ok(json)
      }
      case None => NotFound
    }
  }

  //全件取得
  def readAll() = Action.async { implicit request =>
    db.run(Quizzes.quizzes.result).map { quizzes =>
      val json = Json.toJson(quizzes)
      Ok(json)
    }
  }

  //データ更新
  def update(id: Int) = Action.async(parse.json) { implicit request =>
    val code: Option[Int] = (request.body \ "code").asOpt[Int]
    val category: Option[Int] = (request.body \ "category").asOpt[Int]
    val question: Option[String] = (request.body \ "question").asOpt[String]
    val info: Option[String] = (request.body \ "info").asOpt[String]
    val correctCode: Option[Int] = (request.body \ "correct_code").asOpt[Int]
    val answers = (request.body \ "answers").as[Map[String, String]]
    (code, category,question,info,correctCode) match {
      case (Some(code), Some(category), Some(question), Some(info),Some(correctCode)) =>

        // 現在の時刻を取得
        val currentTime = Timestamp.from(Instant.now())

        val updatedQuiz = Quiz(id, code, category,question,info,correctCode, Some(answers))
          val action = Quizzes.quizzes
            .filter(q => q.category === category && q.code === code )
            .update(updatedQuiz)

          db.run(action).map {
            case 0 => NotFound
            case _ => Ok("Quiz updated")
          }
      case _ =>
        Future.successful(BadRequest("Missing field in the request body"))
    }
  }

  //データ削除
    def delete(id: Int) = Action.async { implicit request =>
      val action = Quizzes.quizzes
        .filter(q => q.id === id )
        .delete
      db.run(action).map {
        case 0 => NotFound
        case _ => Ok("Quiz deteted")
      }
    }
}







