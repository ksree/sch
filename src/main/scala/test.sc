import scala.util.matching.Regex
val testRawSchedule = "Writing Fast Tests Against Enterprise Rails 60min Overdoing it in Python 45min Lua for the Masses 30min Ruby Errors from Mismatched Gem Versions 45min Common Ruby Errors 45min Rails for Python Developers lightning Communicating Over Distance 60min Accounting-Driven Development 45min Woah 30min Sit Down and Write 30min Pair Programming vs Noise 45min Rails Magic 60min Ruby on Rails: Why We Should Move On 60min Clojure Ate Scala (on my project) 45min Programming in the Boondocks of Seattle 30min Ruby vs. Clojure for Back-End Development 30min Ruby on Rails Legacy App Maintenance 60min A World Without HackerNews 30min User Interface CSS in Rails Apps 30min"
val testRawSchedule1 = "Writing Fast Tests Against Enterprise Rails 60min"
val rawScheduleRegExpr: Regex = """(.*?) (\d{2}min|lightning)""".r
//  val rawScheduleRegExpr(talk, duration) = testRawSchedule

val schedule: Map[String, String] = testRawSchedule1 match {
  case rawScheduleRegExpr(talk, duration) => Map(talk -> duration)
  case _ => Map("error"-> "error")
}

schedule.size


rawScheduleRegExpr.findAllMatchIn(testRawSchedule).map(_.toString()).toArray.size

for(m <- rawScheduleRegExpr.findAllMatchIn(testRawSchedule)) {
  println (s"Talk: ${m.group(1)} and duration: ${m.group(2)}" )
  Map(m.group(1) -> m.group(2))
}

List((3, "s"), (1,"t")).sortBy(_._1)
