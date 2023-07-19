package controllers

import models.{AccessHistories, AccessHistory, Quizzes}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

//アクセス履歴テーブルの操作
@Singleton
class AccessHistoriesController @Inject()(
                                           protected val dbConfigProvider: DatabaseConfigProvider,
                                           val controllerComponents: ControllerComponents
                                         )(implicit ec: ExecutionContext) extends BaseController with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  //レコード作成
  def create() = Action.async(parse.json) { implicit request =>
    val uri: Option[String] = (request.body \ "uri").asOpt[String]
    val host: Option[String] = (request.body \ "host").asOpt[String]
    val uid: Option[String] = (request.body \ "uid").asOpt[String]

    (uri, host, uid) match {
      case (Some(uri), Some(host), Some(uid)) =>
        val accessHistory = AccessHistory(0, uri, host, uid)
        val action = (AccessHistories.accessHistories returning AccessHistories.accessHistories.map(_.id) into ((accessHistory, id) => accessHistory.copy(id = id))) += accessHistory
        db.run(action).map(id => Ok(s"accessHistory created with id: $id"))
      case _ =>
        Future.successful(BadRequest("Missing field in the request body"))
    }
  }

}








