import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _15_PartialFunctions extends FunSuite with Matchers {

  /* ~Mine~ A partial function is a function that is only defined for a subset of it's input. | A
     They come from maths: http://en.wikipedia.org/wiki/Partial_function                      |   \---> 1
     For instance, sqrt(x) is only defined for x >= 0 and it's not defined for x < 0.         | B ----> 2
     def /(divident: Int, divisor: Int) is only for divisor != 0.                             |      -> 3
     Therefore, sqrt(x) and /(a, b) can be defined as a PartFunctions.                        | C-x / (not defined on C)
     They will not operate on non defined ranges and therefore won't crash on them.           |    /
     source: http://blog.bruchez.name/2011/10/scala-partial-functions-without-phd.html        | D /
  */

  /* A partial function is a 'trait' that when implemented can be used as building blocks to determine a solution */

   test("The trait PartialFunction requires that the method 'isDefinedAt' and 'apply' be implemented.") {
     val doubleEvens: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
       //States that this partial function will take on the task
       def isDefinedAt(x: Int) = x % 2 == 0

       //What we do if this does partial function matches
       def apply(v1: Int) = v1 * 2
     }

     val tripleOdds: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
       def isDefinedAt(x: Int) = x % 2 != 0
       def apply(v1: Int) = v1 * 3
     }
     val whatToDo = doubleEvens orElse tripleOdds //Here we chain the partial functions together
     whatToDo(3) should be(9)
     whatToDo(4) should be(8)
   }

   test("Case statements are a quick way to create partial functions. When you create a case statement," +
        "the apply and 'isDefinedAt' is created for you") {
     //The case statements are called case statements with guards
     val doubleEvens: PartialFunction[Int, Int] = {
       case x: Int if (x % 2) == 0 => x * 2
     }
     val tripleOdds: PartialFunction[Int, Int] = {
       case x: Int if (x % 2) != 0 => x * 3
     }
     val whatToDo = doubleEvens orElse tripleOdds //Here we chain the partial functions together
     whatToDo(3) should be(9)
     whatToDo(4) should be(8)
   }

   test("The result of partial functions can have an 'andThen' function added to the end of the chain") {
     //These are called case statements with guards
     val doubleEvens: PartialFunction[Int, Int] = {
       case x: Int if (x % 2) == 0 => x * 2
     }
     val tripleOdds: PartialFunction[Int, Int] = {
       case x: Int if (x % 2) != 0 => x * 3
     }
     val addFive = (x: Int) => x + 5
     val whatToDo = doubleEvens orElse tripleOdds andThen addFive //Here we chain the partial functions together
     whatToDo(3) should be(14)
     whatToDo(4) should be(13)
   }

   test("The result of partial functions can have an 'andThen' function added to the end of the chain used to continue onto another chain of logic") {
     val doubleEvens: PartialFunction[Int, Int] = {
       case x: Int if (x % 2) == 0 => x * 2
     }
     val tripleOdds: PartialFunction[Int, Int] = {
       case x: Int if (x % 2) != 0 => x * 3
     }
     val printEven: PartialFunction[Int, String] = {
       case x: Int if (x % 2) == 0 => "Even"
     }
     val printOdd: PartialFunction[Int, String] = {
       case x: Int if (x % 2) != 0 => "Odd"
     }
     val whatToDo = doubleEvens orElse tripleOdds andThen (printEven orElse printOdd)

     whatToDo(3) should be("Odd")
     whatToDo(4) should be("Even")
   }
 }
