import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _08_Maps extends FunSuite with Matchers {

  /* A Map is an Iterable consisting of pairs of keys and values (also named mappings or associations).

  Scala’s Predef class offers an implicit conversion that lets you write key -> value as an alternate syntax for the pair (key, value).
    For instance                  Map("x" -> 24, "y" -> 25, "z" -> 26)
    means exactly the same as     Map(("x", 24), ("y", 25), ("z", 26)), but reads better.

  The fundamental operations on maps are similar to those on sets. They are summarized in the following table and fall into the following categories:

    * Lookup operations `apply`, `get`, `getOrElse`, `contains`, and `isDefinedAt`. These turn maps into partial functions from keys to values.
      The fundamental lookup method for a map is: `def get(key): Option[Value]`.
      The operation `m get key` tests whether the map contains an association for the given key.
      If so, it returns the associated value in a 'Some'. If no key is defined in the map, get returns 'None'.
      Maps also define an `apply` method that returns the value associated with a given key directly, without wrapping it in an 'Option'.
      If the key is not defined in the map, an exception is raised.

    * Additions and updates `+`, `++`, `updated`, which let you add new bindings to a map or change existing bindings.
    * Removals `-`, `--`, which remove bindings from a map.
    * Subcollection producers `keys`, `keySet`, `keysIterator`, `values`, `valuesIterator`, which return a map’s keys and values separately in various forms.
    * Transformations `filterKeys` and `mapValues`, which produce a new map by filtering and transforming bindings of an existing map.
  */

   test("Maps can be created easily") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
     myMap.size should be(4)
   }

   test("Maps contain distinct pairings") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Michigan")
     myMap.size should be(3)
   }

   test("Nil lists are identical, even of different types") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Michigan")
     val aNewMap = myMap + ("IL" -> "Illinois")
     aNewMap.contains("IL") should be(true)
   }

   test("Map values can be iterated") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Michigan")

     val mapValues = myMap.values
     mapValues.size should be(3)
     mapValues.head should be("Michigan") //Failed presumption: The order in maps is not guaranteed

     val lastElement = mapValues.last
     lastElement should be("Wisconsin") //Failed presumption: The order in maps is not guaranteed
   }

   test("Maps insertion with duplicate key updates previous entry with subsequent value") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Meechigan")
     val mapValues = myMap.values
     mapValues.size should be(3)
     myMap("MI") should be("Meechigan")
   }

   test("Map keys may be of mixed type") {
     val myMap = Map("Ann Arbor" -> "MI", 49931 -> "MI")
     myMap("Ann Arbor") should be("MI")
     myMap(49931) should be("MI")
   }

   test("Mixed type values can be added to a map") {
     val myMap = scala.collection.mutable.Map.empty[String, Any]
     myMap("Ann Arbor") = (48103, 48104, 48108)
     myMap("Houghton") = 49931

     myMap("Houghton") should be(49931)
     myMap("Ann Arbor") should be((48103, 48104, 48108))
   }

   test("Maps may be accessed") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
     myMap("MI") should be("Michigan")
     myMap("IA") should be("Iowa")
   }

   test("Map elements can be removed easily") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
     val aNewMap = myMap - "MI"
     aNewMap.contains("MI") should be(false)
     myMap.contains("MI") should be(true)
   }

   test("Accessing a map by key results in an exception if key is not found") {
     val myMap = Map("OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
     var blewWithException = true
     intercept[NoSuchElementException] {
       myMap("MI")
       blewWithException = false
     }
     blewWithException should be(true)
   }

   test("Map elements can be removed in multiple") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
     val aNewMap = myMap -- List("MI", "OH")

     aNewMap.contains("MI") should be(false)
     myMap.contains("MI") should be(true)

     aNewMap.contains("WI") should be(true)
     aNewMap.size should be(2)
     myMap.size should be(4)
   }

   test("Map elements can be removed with a tuple") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
     val aNewMap = myMap - ("MI", "WI") // Notice: single '-' operator for tuples

     aNewMap.contains("MI") should be(false)
     myMap.contains("MI") should be(true)
     aNewMap.contains("OH") should be(true)
     aNewMap.size should be(2)
     myMap.size should be(4)
   }

   test("Attempted removal of nonexistent elements from a map is handled gracefully") {
     val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
     val aNewMap = myMap - "MN"

     aNewMap.equals(myMap) should be(true)
   }

   test("Map equivalency is independent of order") {
     val myMap1 = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
     val myMap2 = Map("WI" -> "Wisconsin", "MI" -> "Michigan", "IA" -> "Iowa", "OH" -> "Ohio")

     myMap1.equals(myMap2) should be(true)
   }
 }
