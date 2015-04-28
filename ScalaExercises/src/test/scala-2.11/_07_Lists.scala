import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _07_Lists extends FunSuite with Matchers {

  //Scala Lists are quite similar to arrays, all the elements of a list have the same type but there are two differences
  // First, lists are immutable, which means elements of a list cannot be changed by assignment.
  // Second, lists represent a linked list whereas arrays are flat. The type of a list that has elements of type T is written as List[T].

   test("`eq` tests identity (same object)") {
     val a = List(1, 2, 3)
     val b = List(1, 2, 3)
     (a eq b) should be(false)
   }

   test("`==` tests equality (same content)") {
     val a = List(1, 2, 3)
     val b = List(1, 2, 3)
     (a == b) should be(true)
   }

   test("'Nil' lists are identical, even of different types") {
     val a: List[String] = Nil
     val b: List[Int] = Nil

     (a == Nil) should be(true)
     (a eq Nil) should be(true)

     (b == Nil) should be(true)
     (b eq Nil) should be(true)

     (a == b) should be(true)
     (a eq b) should be(true)
   }

   test("Lists are easily created") {
     val a = List(1, 2, 3)
     a should equal(List(1, 2, 3))
   }

   test("Lists can be accessed via head and tail") {
     val a = List(1, 2, 3)
     a.head should equal(1)
     a.tail should equal(List(2, 3))
   }

   test("Lists can be accessed by position") {
     val a = List(1, 3, 5, 7, 9)
     a(0) should equal(1)
     a(2) should equal(5)
     a(4) should equal(9)

     intercept[IndexOutOfBoundsException] {
       println(a(5))
     }
   }

   test("Lists are immutable") {
     val a = List(1, 3, 5, 7, 9)
     val b = a.filterNot(v => v == 5) // remove where value is 5

     a should equal(List(1, 3, 5, 7, 9))
     b should equal(List(1, 3, 7, 9))
   }

   test("Lists have many useful methods") {
     val a = List(1, 3, 5, 7, 9)

     // get the length of the list
     a.length should equal(5)

     // reverse the list
     a.reverse should equal(List(9, 7, 5, 3, 1))

     // convert the list to a string representation
     a.toString should equal("List(1, 3, 5, 7, 9)")

     // map a function to double the numbers over the list
     a.map {v => v * 2} should equal(List(2, 6, 10, 14, 18))

     // filter any values divisible by 3 in the list
     a.filter {v => v % 3 == 0} should equal(List(3, 9))
   }

   test("Functions over lists can use _ as shorthand") {
     val a = List(1, 2, 3)

     a.map {_ * 2} should equal(List(2, 4, 6))

     a.filter {_ % 2 == 0} should equal(List(2))
   }

   test("Functions over lists can use () instead of {}") {
     val a = List(1, 2, 3)
     a.map(_ * 2) should equal(List(2, 4, 6))
     a.filter(_ % 2 != 0) should equal(List(1, 3))
   }

   test("Lists can be reduced with a mathematical operation") {
     val a = List(1, 3, 5, 7)
     // note the two _s below indicate the first and second args respectively
     a.reduceLeft(_ + _) should equal(16)
     a.reduceLeft(_ * _) should equal(105)
   }

   test("Foldleft is like reduce, but with an explicit starting value\n\nDone") {
     val a = List(1, 3, 5, 7)
     // NOTE: foldLeft uses a form called currying that we will explore later
     a.foldLeft(0)(_ + _) should equal(16)
     a.foldLeft(10)(_ + _) should equal(26)
     a.foldLeft(1)(_ * _) should equal(105)
     a.foldLeft(0)(_ * _) should equal(0)
   }

   test("You can create a list from a range") {
     val a = (1 to 5).toList
     a should be(List(1, 2, 3, 4, 5))
   }

   test("Lists reuse their tails") {
     val d = Nil
     val c = 3 :: d
     val b = 2 :: c
     val a = 1 :: b

     a should be(List(1, 2, 3))
     a.tail should be(List(2, 3))
     b.tail should be(List(3))
     c.tail should be(Nil)
   }
 }
