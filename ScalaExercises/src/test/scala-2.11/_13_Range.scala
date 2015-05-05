import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _13_Range extends FunSuite with Matchers {

   test("Range are not inclusive at end of range") {
     val someNumbers = Range(0, 10)
     val second = someNumbers(1)
     val last = someNumbers.last

     someNumbers.size should be(10)
     second should be(1)
     last should be(9)
   }

   test("Range can specify a step for an increment") {
     val someNumbers = Range(2, 10, 3)
     val second = someNumbers(1)
     val last = someNumbers.last

     someNumbers.size should be(3)
     second should be(5)
     last should be(8)
   }

   test("Range does not include the last item, even in a step increment") {
     val someNumbers = Range(0, 34, 2)
     someNumbers.contains(33) should be(false)
     someNumbers.contains(32) should be(true)
     someNumbers.contains(34) should be(false)
   }

   test("Range can specify to include the last value") {
     val someNumbers = Range(0, 34).inclusive
     someNumbers.contains(34) should be(true)
   }

   /* A Range is an ordered sequence of integers that are equally spaced apart.
      For example, "1, 2, 3," is a range, as is "5, 8, 11, 14."

      To create a range in Scala, use the predefined methods `to` and `by`.
      "1 `to` 3" generates Range(1, 2, 3) and "5 `to` 14 `by` 3" generates Range(5, 8, 11, 14).

      If you want to create a range that is exclusive of its upper limit,
      then use the convenience method `until` instead of `to`: "1 `until` 3" generates Range(1, 2).

      Ranges are represented in constant space, because they can be defined by just three numbers:
      their start, their end, and the stepping value.
      Because of this representation, most operations on ranges are extremely fast.
   */
 }
