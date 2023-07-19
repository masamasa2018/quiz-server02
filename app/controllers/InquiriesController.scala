package controllers

import models.{Inquiries, Inquiry}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{BaseController, ControllerComponents}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

//問い合わせテーブルの操作
@Singleton
class InquiriesController @Inject()(
                                           protected val dbConfigProvider: DatabaseConfigProvider,
                                           val controllerComponents: ControllerComponents
                                         )(implicit ec: ExecutionContext) extends BaseController with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  //レコード作成
  def create() = Action.async(parse.json) { implicit request =>
    val email: Option[String] = (request.body \ "email").asOpt[String]
    val name: Option[String] = (request.body \ "name").asOpt[String]
    val message: Option[String] = (request.body \ "message").asOpt[String]

    (email, name, message) match {
      case (Some(email), Some(name), Some(message)) =>
        val inquery = Inquiry(0, email, name, message)
        val action = (Inquiries.inquiries returning Inquiries.inquiries.map(_.id) into ((inquery, id) => inquery.copy(id = id))) += inquery
        db.run(action).map(id => Ok(s"Inquery created with id: $id"))
      case _ =>
        Future.successful(BadRequest("Missing field in the request body"))
    }
  }

}








