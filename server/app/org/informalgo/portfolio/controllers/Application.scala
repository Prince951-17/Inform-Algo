package org.informalgo.portfolio.controllers

import com.typesafe.scalalogging.LazyLogging
import org.informalgo.portfolio.shared.SharedMessages
import org.webjars.play.WebJarsUtil
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import views.html.index

import javax.inject._
import scala.concurrent.ExecutionContext
import play.filters.csrf._
import play.filters.csrf.CSRF.Token
@Singleton
class Application @Inject()(val controllerComponents: ControllerComponents,
                            indexTemplate: index,
                            implicit val webJarsUtil: WebJarsUtil,
                            addToken: CSRFAddToken, checkToken: CSRFCheck)
                           (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {

  def index: Action[AnyContent] = Action {
    Ok(indexTemplate())
  }

  def contactUs: Action[JsValue] = Action(parse.json) { implicit request =>
    val name = (request.body \ "name").as[String]
    println(name)
    Ok(Json.toJson("OK"))
  }

}
