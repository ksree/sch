import org.scalatest.{BeforeAndAfter, FunSpec}

class SchedulerTest extends FunSpec with BeforeAndAfter {

  val testRawSchedule = "Writing Fast Tests Against Enterprise Rails 60min Overdoing it in Python 45min Lua for the Masses 30min Ruby Errors from Mismatched Gem Versions 45min Common Ruby Errors 45min Rails for Python Developers lightning Communicating Over Distance 60min Accounting-Driven Development 45min Woah 30min Sit Down and Write 30min Pair Programming vs Noise 45min Rails Magic 60min Ruby on Rails: Why We Should Move On 60min Clojure Ate Scala (on my project) 45min Programming in the Boondocks of Seattle 30min Ruby vs. Clojure for Back-End Development 30min Ruby on Rails Legacy App Maintenance 60min A World Without HackerNews 30min User Interface CSS in Rails Apps 30min"
  var rawSchedule: Seq[(String, String)] = _
  var formattedSchedule: Seq[(String, Int)] = _
  var organizedSchedule: Seq[(String, Int)] = _
  var formatOrganizedSchedule: Seq[(String, String)] = _

  describe("Raw schedule reader") {
    rawSchedule = Scheduler.readRawSchedule(testRawSchedule)
    it("it should return a Map of schedule of size 19") {
      assert(rawSchedule.size == 19)
    }
  }

  describe("Format schedule") {
    formattedSchedule = Scheduler.formatRawSchedule(rawSchedule)
    it("Should return a list of tuples with talk and the duration in minutes") {
      assert(formattedSchedule.head._1 == "Writing Fast Tests Against Enterprise Rails")
      assert(formattedSchedule.head._2 == 60)
    }
    it("Should sort the talks based on the talk duration, ascending order ") {
      assert(formattedSchedule.head._2 == 60)
    }
  }

  describe("Organize schedule") {
    organizedSchedule = Scheduler.organizeSchedule(formattedSchedule)

    it("Should return the organized schedule") {
      assert(organizedSchedule.size == 20)
    }
  }

  describe("Format organized schedule") {
    formatOrganizedSchedule = Scheduler.formatOrganizedSchedule(organizedSchedule)
    it("Should return the final formatted organized schedule") {
      assert(formatOrganizedSchedule.size == 9)
    }
    it("Should return a list of tuples with talk and schedule time ") {
      assert(formatOrganizedSchedule.head._1 == "Writing Fast Tests Against Enterprise Rails")
      assert(formatOrganizedSchedule.head._2 == "09:00 AM")
    }
  }

}
