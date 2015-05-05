import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _15_PartialFunctions extends FunSuite with Matchers {

  /* A method with implicit parameters can be applied to arguments just like a normal method. In this case the implicit label has no effect.
     However, if such a method misses arguments for its implicit parameters, such arguments will be automatically provided.

    The actual arguments that are eligible to be passed to an implicit parameter fall into two categories:
    · First, eligible are all identifiers x that can be accessed at the point of the method call without a
      prefix and that denote an implicit definition or an implicit parameter.
    · Second, eligible are also all members of companion modules of the implicit parameter’s type that are labeled implicit.
  */

   test("In the following example we define a method sum which computes the sum of a list of elements using the monoid’s add and unit operations." +
        "Please note that implicit values can not be top-level, they have to be members of a template.") {
     abstract class SemiGroup[A] {
       def add(x: A, y: A): A
     }
     abstract class Monoid[A] extends SemiGroup[A] {
       def unit: A
     }

     implicit object StringMonoid extends Monoid[String] {
       def add(x: String, y: String): String = x concat y
       def unit: String = ""
     }
     implicit object IntMonoid extends Monoid[Int] {
       def add(x: Int, y: Int): Int = x + y
       def unit: Int = 0
     }
     def sum[A](xs: List[A])(implicit m: Monoid[A]): A =
       if (xs.isEmpty) m.unit
       else m.add(xs.head, sum(xs.tail))
     sum(List(1, 2, 3)) should be (6)
     sum(List("a", "b", "c")) should be ("abc")
   }

   test("Implicits wrap around existing classes to provide extra functionality (like monkey patching in Ruby, and Meta-Programming in Groovy)." +
        "Creating a method isOdd for Int, which doesn't exist:") {
     class KoanIntWrapper(val original: Int) {
       def isOdd = original % 2 != 0
     }

     implicit def thisMethodNameIsIrrelevant(value: Int) = new KoanIntWrapper(value)

     19.isOdd should be(true)
     20.isOdd should be(false)
   }

   test("Implicits rules can be imported into your scope with an import") {
     object MyPredef {

       class KoanIntWrapper(val original: Int) {
         def isOdd = original % 2 != 0
         def isEven = !isOdd
       }
       implicit def thisMethodNameIsIrrelevant(value: Int) = new KoanIntWrapper(value)
     }

     import MyPredef._
     //imported implicits come into effect within this scope
     19.isOdd should be(true)
     20.isOdd should be(false)
   }

   test("Implicits can be used to automatically convert one type to another") {
     import java.math.BigInteger
     implicit def Int2BigIntegerConvert(value: Int): BigInteger = new BigInteger(value.toString)

     def add(a: BigInteger, b: BigInteger) = a.add(b)

     add(Int2BigIntegerConvert(3), Int2BigIntegerConvert(6)) == Int2BigIntegerConvert(9)  should be(true)

     add(3, 6) == 9  should be(false)
     add(3, 6) == Int2BigIntegerConvert(9)  should be(true)

     add(3, 6) == (9:BigInteger)  should be(true)
     add(3, 6).intValue == 9  should be(true)
   }

   test("Implicits can be used declare a value to be provided as a default as long as an implicit value is set with in the scope." +
        "These are called implicit function parameters") {
     def howMuchCanIMake_?(hours: Int)(implicit dollarsPerHour: BigDecimal) = dollarsPerHour * hours

     implicit var hourlyRate = BigDecimal(34.00)

     howMuchCanIMake_?(30) should be(1020.0)

     hourlyRate = BigDecimal(95.00)
     howMuchCanIMake_?(95) should be(9025.0)
   }

   test("Implicit Function Parameters can contain a list of implicits") {
     def howMuchCanIMake_?(hours: Int)(implicit amount: BigDecimal, currencyName: String) =
       (amount * hours).toString() + " " + currencyName

     implicit var hourlyRate = BigDecimal(34.00)
     implicit val currencyName = "Dollars"

     howMuchCanIMake_?(30) should be("1020.0 Dollars")

     hourlyRate = BigDecimal(95.00)
     howMuchCanIMake_?(95) should be("9025.0 Dollars")
   }

   test("Default arguments though are preferred to Implicit Function Parameters") {
     def howMuchCanIMake_?(hours: Int, amount: BigDecimal = 34, currencyName: String = "Dollars") =
       (amount * hours).toString() + " " + currencyName

     howMuchCanIMake_?(30) should be("1020 Dollars")
     howMuchCanIMake_?(95, 95) should be("9025 Dollars")
   }


 }
