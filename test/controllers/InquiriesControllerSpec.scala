package controllers

import akka.stream.Materializer
import controllers.InquiriesController
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

class InquiriesControllerSpec extends PlaySpec with GuiceOneAppPerTest {

  "InquiriesController" should {

    "create a new inquiry" in {
      val controller = app.injector.instanceOf[InquiriesController]

      val request = FakeRequest(POST, "/inquiries")
        .withJsonBody(Json.obj(
          "email" -> "test@example.com",
          "name" -> "John Doe",
          "message" -> "Hello, World!"
        ))

      implicit val mat: Materializer = app.materializer
      val result = controller.create()(request)
      print("setaseta===" + result)
      val expectedResponse = "Inquiry created with id: 1"

      status(result) mustBe OK
      contentAsString(result) mustBe expectedResponse
    }

    "return BadRequest when missing fields" in {
      val controller = app.injector.instanceOf[InquiriesController]

      val request = FakeRequest(POST, "/inquiries")
        .withJsonBody(Json.obj(
          "email" -> "test@example.com",
          "name" -> "John Doe"
        ))

      implicit val mat: Materializer = app.materializer
      val result = controller.create()(request)

      status(result) mustBe BAD_REQUEST
    }

  }

}
