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
   }

   test("== tests equality (same content)") {
   }

   test("Nil lists are identical, even of different types") {
   }

   test("Lists are easily created") {
   }

   test("Lists can be accessed via head and tail") {
   }

   test("Lists can be accessed by position") {
   }

   test("Lists are immutable") {
   }

   test("Lists have many useful methods") {
   }
 }
