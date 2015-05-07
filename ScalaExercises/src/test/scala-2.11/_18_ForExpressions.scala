import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _18_ForExpressions extends FunSuite with Matchers {

   test("For loops can be simple") {
     val someNumbers = Range(0, 10)
     var sum = 0
     for (i <- someNumbers)
       sum += i
     sum should equal(45)
   }

   test("For loops can contain additional logic") {
     val someNumbers = Range(0, 10)
     var sum = 0
     for (i <- someNumbers)
       if (i % 2 == 0) sum += i
     sum should equal(20)
   }

   test("For expressions can nest, with later generators varying more rapidly than earlier ones") {
     val xValues = Range(1, 5)
     val yValues = Range(1, 3)
     val coordinates = for {
       x <- xValues
       y <- yValues} yield (x, y)
     coordinates(4) should be(3, 1)
   }

   test("Using 'for' we can make more readable code") {
     val nums = List(List(1), List(2), List(3), List(4), List(5))

     val result = for {
       numList <- nums
       num <- numList
       if (num % 2 == 0)
     } yield (num)

     result should be (List(2, 4))

     // Which is the same as
     nums.flatMap(numList => numList).filter(_ % 2 == 0) should be(result)
   }

}
