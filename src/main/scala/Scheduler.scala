import java.time.LocalTime
import java.time.format.DateTimeFormatter

import scala.io.Source
import scala.util.matching.Regex
object Scheduler extends App{

  val rawScheduleRegExpr: Regex = """(.*?) (\d{2}min|lightning)""".r
  val durationRegExpr: Regex = """(\d{2})(min)""".r
  val LIGHTNING: (String, Int) = ("lightning", 5)
  val morningSessionStart = 9
  val morningSessionEnd = 12
  val eveningSessionStart = 1
  val eveningSessionEnd = 5
  val rawConferenceSource = "src/main/resource/raw_conference_events.txt"
  /**
   *
   * @param input
   * @return
   */
  def readRawSchedule(input: String): Seq[(String, String)] = {
    val result: Seq[(String, String)] = (for (m <- rawScheduleRegExpr.findAllMatchIn(input))
      yield (m.group(1), m.group(2))).toSeq
    result.toList
  }

  def formatRawSchedule(rawSchedule: Seq[(String, String)]): Seq[(String, Int)] = {
    //Convert the duration string to Integer.Also convert lightning to 5
    val transSchedule: Seq[(String, Int)] = rawSchedule.map(e => {
      val talk: String = e._1
      val duration: Int = e._2 match {
        case LIGHTNING._1 => LIGHTNING._2
        case durationRegExpr(x, _) => {
          Option(x.trim.toInt) match {
            case Some(d: Int) => d
            case _ => 0
          }
        }
      }
      (talk, duration)
    })
    //Sorted list
    transSchedule.sortWith(_._2 > _._2)
  }

  def organizeSchedule(convertedSchedule: Seq[(String, Int)]): Seq[(String, Int)] = {
    val me = convertedSchedule.foldLeft((List.empty[(String, Int)], List.empty[(String, Int)]))((r, e) => {
      r match {
        case (List(), List()) => (r._1 ::: List(e), List.empty)
        case (mor, eve) => {
          if (mor.map(_._2).sum < (morningSessionEnd - morningSessionStart) * 60) {
            (mor ::: List(e), eve)
          } else if (eve.map(_._2).sum < (eveningSessionEnd - eveningSessionStart) * 60) {
            (mor, eve ::: List(e))
          } else (mor, eve)
        }
      }
    })
    val re = me._1 ::: List(("Lunch", 60)) ::: me._2
    re
  }

  def formatOrganizedSchedule(organizedSchedule: Seq[(String, Int)]): Seq[(String, String)] = {
    var startTime: LocalTime = LocalTime.parse("09:00 AM", DateTimeFormatter.ofPattern("hh:mm a"))
    organizedSchedule.map(e => {
      val r = (e._1, startTime.format(DateTimeFormatter.ofPattern("hh:mm a")))
      startTime =  startTime.plusMinutes(e._2)
      r
    })
  }

  def createSchedule(input: String ): String ={
    val schedule: Seq[(String, String)] = formatOrganizedSchedule(organizeSchedule(formatRawSchedule(readRawSchedule(input))))
    val finalSchedule = schedule.map(e => e._1 + " " + e._2).mkString("\n")
    finalSchedule
  }

  //This peice of code is to execute
  val scheduleraw = Source.fromFile(rawConferenceSource).getLines().mkString(" ")
  val lines: Iterator[String] = for(l <- Source.fromFile(rawConferenceSource).getLines())
    yield l

  println(s"Raw Schedule ${lines.mkString("")}")
  println(createSchedule(scheduleraw))
}

