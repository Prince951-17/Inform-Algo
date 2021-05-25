package org.informalgo.portfolio

import org.informalgo.portfolio.shared.SharedMessages
import org.querki.jquery._
import org.scalajs.dom._
import scala.scalajs.js

object ScalaJSExample {
  case class AjaxSuccess(data: js.Any, textStatus: String, jqXHR: JQueryXHR)
  def resCreator: (js.Any, String, JQueryXHR) => AjaxSuccess = (a, b, c) => AjaxSuccess(a, b, c)
  def ajaxSettings(url: String, data: String): JQueryAjaxSettings = {
    def handleAjaxError(jqXHR: JQueryXHR, textStatus: String, errorThrow: String): Unit = {
      println("Error while performing AJAX POST")
    }

    def handleAjaxSuccess(data: js.Any, textStatus: String, jqXHR: JQueryXHR): Unit = {
      val jsonFromServer = jqXHR.responseText
      println(s"Got from server: $jsonFromServer")
    }

    js.Dynamic.literal(
      url = url,
      contentType = "application/json",
      data = data,
      `type` = "POST",
      success = handleAjaxSuccess _,
      error = handleAjaxError _).asInstanceOf[JQueryAjaxSettings]
  }

  def main(args: Array[String]): Unit = {
    val preloader = $("#preloader")
    window.onload = (_: Event) =>
      if (!preloader.length.isNaN) {
        preloader.delay(100).fadeOut(
          JQueryAnimationSettings.duration(1000)
            .complete { elem =>
              elem.parentNode.removeChild(elem)
            })
      }

    val header = document.getElementById("header")
    window.onscroll = (_: UIEvent) =>
      if (document.documentElement.scrollTop > 100) {
        header.classList.add("header-scrolled")
        $(".back-to-top").fadeIn(JQueryAnimationSettings.duration("slow"))
      } else {
        header.classList.remove("header-scrolled")
        $(".back-to-top").fadeOut(JQueryAnimationSettings.duration("slow"))
      }

    if (document.documentElement.scrollTop > 100) header.classList.add("header-scrolled")
    val companyName = document.getElementById("company-name")

    companyName.textContent = SharedMessages.companyName
    companyName.classList.add("text-white")

    $("#postButton").click { (_: Event) =>
      val name = $("#name").valueString
      $.ajax(ajaxSettings("/contact-us", s"""{"name": "$name"}"""))
        .done { (_: js.Any, _: String, res: JQueryXHR) =>
          println(res.responseText)
        }
    }
  }
}
