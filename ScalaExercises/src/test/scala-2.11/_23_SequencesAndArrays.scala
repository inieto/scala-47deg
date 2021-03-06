import org.scalatest.{FunSuite, Matchers}

import scala.collection._

/**
  * Created by inieto on 27/04/15.
  */
class _23_SequencesAndArrays extends FunSuite with Matchers {

  /* Scala provides a data structure, the array, which stores a fixed-size sequential collection of elements of the same type.
     An array is used to store a collection of data, but it is often more useful to think of an array as a collection of variables of the same type.*/

  test("A list can be converted to an array") {
    val l = List(1, 2, 3)
    val a = l.toArray
    a should equal(Array(1, 2, 3))
  }

  test("Sequences are special cases of iterable collections of class 'Iterable'." +
    "Unlike iterables, sequences always have a defined order of elements. Any sequence can be converted to a list.") {
    val a = Array(1, 2, 3)
    val s = a.toSeq
    val l = s.toList
    l should equal (List(1, 2, 3))
  }

  test("You can create a sequence from a for comprehension") {
    val s = for (v <- 1 to 4) yield v
    s.toList should be (List(1, 2, 3, 4))
  }

  test("You can create a sequence from a for comprehension with a condition") {
    val s = for (v <- 1 to 10 if v % 3 == 0) yield v
    s.toList should be (List(3, 6, 9))
  }

  test("You can filter any sequence based on a predicate") {
    val s = Seq("hello", "to", "you")
    val filtered = s.filter(_.length > 2)
    filtered should be (Seq("hello", "you"))
  }

  test("You can also filter Arrays in the same way") {
    val a = Array("hello", "to", "you", "again")
    val filtered = a.filter(_.length > 3)
    filtered should be (Array("hello", "again"))
  }

  test("You can map values in a sequence through a funciton") {
    val s = Seq("hello", "world")
    val r = s map(_.reverse)
    r should be (Seq("olleh", "dlrow"))
  }
}
