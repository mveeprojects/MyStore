import org.scalajs.dom.raw
import org.scalajs.dom.{document, window}

object Main {
  def main(args: Array[String]): Unit = {
    val title: raw.Element = document.createElement("h1")
    title.textContent = "Welcome"
    document.body.appendChild(title)
  }
}