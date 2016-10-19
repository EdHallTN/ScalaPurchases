import java.io.{File, FileWriter, PrintWriter}

import scala.collection.mutable
import scala.io.Source

/**
  * Created by EdHall on 10/18/16.
  */
object FileIO {

  val items = mutable.MutableList[(String, String, String, String, String)]()

  def prompt(s: String) = {
    println(s)
    io.StdIn.readLine()
  }

  def main(args: Array[String]): Unit = {

    Source
      .fromFile("be594d3c-purchases.csv")
      .getLines
      .drop(1)
      .foreach(line => {
        val item = {
          val Array(id, date, cc, cvv, category) = line.split(",").map(_.trim)
          (id, date, cc, cvv, category)
        }
        items += item
      })

    def sort(category: String) = {
      val pw = new PrintWriter(new File(s"filtered-${category}-purchases.prn"))
      val list = items.filter(p => p._5.equals(category.toLowerCase().capitalize))
      list.foreach(line => {
        println(s"Customer: ${line._1}, Date: ${line._2.slice(0, 10)}")
        pw.write(s"Customer: ${line._1} | Date: ${line._2.slice(0, 10)}\n")
      })

      pw.close()

    }

    def category = {
      val seq = Seq("Furniture", "Alcohol", "Toiletries", "Shoes", "Food", "Jewelry").mkString("\n")
      prompt(s"\nPlease enter a category:\n${seq}\n").toLowerCase()
    }
    var resp = category match {
      case "furniture" => sort("furniture")
      case "alcohol" => sort("alcohol")
      case "toiletries" => sort("toiletries")
      case "shoes" => sort("shoes")
      case "food" => sort("food")
      case "jewelry" => sort("jewelry")
    }
  }
}
