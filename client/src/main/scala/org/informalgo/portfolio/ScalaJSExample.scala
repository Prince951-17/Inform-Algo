package org.informalgo.portfolio

import org.informalgo.portfolio.shared.SharedMessages
import org.querki.jquery._
import org.scalajs.dom._

object ScalaJSExample {

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
  }
}
