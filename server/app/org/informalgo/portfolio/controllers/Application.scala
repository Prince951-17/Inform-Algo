package org.informalgo.portfolio.controllers

import com.typesafe.scalalogging.LazyLogging
import org.informalgo.portfolio.shared.SharedMessages
import org.webjars.play.WebJarsUtil
import play.api.mvc._
import views.html.index

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class Application @Inject()(val controllerComponents: ControllerComponents,
                            indexTemplate: index,
                            implicit val webJarsUtil: WebJarsUtil)
                           (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {

  def index: Action[AnyContent] = Action {
    Ok(indexTemplate())
  }

}
