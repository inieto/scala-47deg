import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _09_Sets extends FunSuite with Matchers {

  /* Sets are Iterables that contain no duplicate elements.
  The operations on sets are summarized in the following table for general sets and in the table after that for mutable sets.
  They fall into the following categories:
    * Tests: `contains`, `apply`, `subsetOf`. The `contains` method asks whether a set contains a given element.
      The `apply` method for a set is the same as `contains`, so `set(elem)` is the same as `set contains elem`.
      That means sets can also be used as test functions that return true for the elements they contain.
    * Additions: `+` and `++`, which add one or more elements to a set, yielding a new set.
    * Removals: `-`, `--`, which remove one or more elements from a set, yielding a new set.
    * Set operations: for union, intersection, and set difference. Each of these operations exists in two forms: alphabetic and symbolic.
      The alphabetic versions are `intersect`, `union`, and `diff`, whereas the symbolic versions are `&`, `|`, and `&~`.
      In fact, the `++` that Set inherits from 'Traversable' can be seen as yet another alias of `union` or `|`,
      except that `++` takes a 'Traversable' argument whereas `union` and `|` take sets.
  */

   test("Sets can be created easily") {
     val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
     mySet.size should be(4)
   }

   test("Sets contain distinct values") {
     val mySet = Set("Michigan", "Ohio", "Wisconsin", "Michigan")
     mySet.size should be(3)
   }

   test("Sets can be added to easily") {
     val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
     val aNewSet = mySet + "Illinois"

     aNewSet.contains("Illinois") should be(true)
     mySet.contains("Illinois") should be(false)
   }

   test("Sets may be of mixed type") {
     val mySet = Set("Michigan", "Ohio", 12)

     mySet.contains(12) should be(true)
     mySet.contains("MI") should be(false)
   }

   test("Sets can be checked for member existence") {
     val mySet = Set("Michigan", "Ohio", 12)

     mySet(12) should be(true)
     mySet("MI") should be(false)
   }

   test("Set elements can be removed easily") {
     val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
     val aNewSet = mySet - "Michigan"

     aNewSet.contains("Michigan") should be(false)
     mySet.contains("Michigan") should be(true)
   }

   test("Set elements can be removed in multiple") {
     val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
     val aNewSet = mySet -- List("Michigan", "Ohio")

     aNewSet.contains("Michigan") should be(false)
     aNewSet.contains("Wisconsin") should be(true)
     aNewSet.size should be(2)
   }

   test("Set elements can be removed with a tuple") {
     val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
     val aNewSet = mySet - ("Michigan", "Ohio") // Notice: single '-' operator for tuples

     aNewSet.contains("Michigan") should be(false)
     aNewSet.contains("Wisconsin") should be(true)
     aNewSet.size should be(2)
   }

   test("Attempted removal of nonexistent elements from a set is handled gracefully") {
     val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
     val aNewSet = mySet - "Minnesota"

     aNewSet.equals(mySet) should be(true)
   }

  test("Sets can be iterated easily") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan", "Minnesota")
    val aNewSet = mySet1 intersect mySet2
    // NOTE: Scala 2.7 used **, deprecated for & or intersect in Scala 2.8

    aNewSet.equals(Set("Michigan", "Wisconsin")) should be(true)
  }

  test("Two sets can be joined as their union easily") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan", "Minnesota")
    val aNewSet = mySet1 union mySet2 // NOTE: You can also use the "|" operator

    aNewSet.equals(Set("Michigan", "Wisconsin", "Ohio", "Iowa", "Minnesota")) should be(true)
  }

  test("A set is either a subset of another set or it isn't") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan", "Minnesota")
    val mySet3 = Set("Wisconsin", "Michigan")

    mySet2 subsetOf mySet1 should be(false)
    mySet3 subsetOf mySet1 should be(true)
  }

  test("The difference between two sets can be obtained easily") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan")
    val aNewSet = mySet1 diff mySet2 // Note: you can use the "&~" operator if you *really* want to.

    aNewSet.equals(Set("Ohio", "Iowa")) should be(true)
  }

  test("Set equivalency is independent of order") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan", "Ohio", "Iowa")

    mySet1.equals(mySet2) should be(true)
  }
 }
