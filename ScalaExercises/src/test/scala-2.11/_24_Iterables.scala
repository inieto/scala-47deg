import org.scalatest.{FunSuite, Matchers}

import scala.collection._

/**
 * Created by inieto on 27/04/15.
 */
class _24_Iterables extends FunSuite with Matchers {

  /* The next trait from the top in the collections hierarchy is 'Iterable'.
    All methods in this trait are defined in terms of an abstract method, 'iterator', which yields the collection's elements one by one.
    The 'foreach' method from trait 'Traversable' is implemented in 'Iterable' in terms of iterator. Here is the actual implementation:

      def foreach[U](f: Elem => U): Unit = {
        val it = iterator
        while (it.hasNext) f(it.next())
      }
    Quite a few subclasses of 'Iterable' override this standard implementation of foreach in 'Iterable', because they can provide a more efficient implementation.
    Remember that 'foreach' is the basis of the implementation of all operations in 'Traversable', so its performance matters.
    Some known iterators are 'Sets', 'Lists', 'Vectors', 'Stacks', and 'Streams' */

  test("Iterator has two important methods: `hasNext`, which answers whether the iterator has another element available." +
    "`next` which will return the next element in the iterator") {
    val list = List(3, 5, 9, 11, 15, 19, 21)
    val it = list.iterator
    if (it.hasNext) {
      it.next should be(3)
    }
  }

  test("'grouped' will return an fixed sized Iterable chucks of an Iterable") {
    val list = List(3, 5, 9, 11, 15, 19, 21, 24, 32)
    val it = list grouped 3
    it.next() should be (List( 3, 5, 9))
    it.next() should be (List(11,15,19))
    it.next() should be (List(21,24,32))
  }

  test("'sliding' will return an Iterable that shows a sliding window of an Iterable") {
    val list = List(3, 5, 9, 11, 15, 19, 21)
    val it = list sliding 3
    it.next() should be (List( 3, 5, 9))
    it.next() should be (List( 5, 9,11))
    it.next() should be (List( 9,11,15))
  }

  test("`sliding` can take the size of the window as well the size of the step during each iteration") {
    val list = List(3, 5, 9, 11, 15, 19, 21, 24, 32)
    val it = list sliding (3, 3)
    it.next() should be (List( 3, 5, 9))
    it.next() should be (List( 11, 15, 19))
    it.next() should be (List( 21, 24, 32))
  }

  test("`takeRight` is the opposite of 'take' in Traversable. It retrieves the last elements of an Iterable") {
    val list = List(3, 5, 9, 11, 15, 19, 21, 24, 32)
    (list takeRight 3) should be (List(21, 24, 32))
  }

  test("`dropRight` will drop the number of elements from the right") {
    val list = List(3, 5, 9, 11, 15, 19, 21, 24, 32)
    (list dropRight 3) should be (List(3, 5, 9, 11, 15, 19))
  }

  test("`zip` will stitch two iterables into an iterable of pairs of corresponding elements from both iterables") {
    //E.g. Iterable(x1, x2, x3) zip Iterable(y1, y2, y3) will return ((x1,y1), (x2, y2), (x3, y3))
    val xs = List(3, 5, 9)
    val ys = "Bob" :: "Ann" :: "Stella" :: Nil  //different notation for lists
    (xs zip ys) should be (List((3, "Bob"), 5 -> "Ann", (9, "Stella"))) //different notation for tuples
  }

  test("If two Iterables aren't the same size, then `zip` will only zip what can only be paired") {
    //E.g. Iterable(x1, x2, x3) zip Iterable(y1, y2) will return ((x1,y1), (x2, y2))
    val xs = List(3, 5, 9)
    val ys = List("Bob", "Ann")
    (xs zip ys) should be (3 -> "Bob" :: 5 -> "Ann" :: Nil)
  }

  test("If two Iterables aren't the same size, then zipAll can provide fillers for what it couldn't find a complement for") {
    //E.g. Iterable(x1, x2, x3) zipAll (Iterable(y1, y2), x, y) will return ((x1,y1), (x2, y2, y))
    val xs = List(3, 5, 9)
    val ys = List("Bob", "Ann")
    val zl = List("Bob", "Ann", "Stella", "Nacho")
    val x = -1  //x completes the left side if xs is shorter than ys
    val y = "?" //y completes the right side if xs is larger than ys
    (xs zipAll (ys, x, y)) should be (List((3,"Bob"), (5,"Ann"), (9,"?")))
    (xs zipAll (zl, x, y)) should be (List((3,"Bob"), (5,"Ann"), (9,"Stella"), (-1,"Nacho")))
  }

  test("`zipWithIndex` will zip an Iterable with it's integer index") {
    val xs = List("Manny", "Moe", "Jack")
    xs.zipWithIndex should be (List(("Manny", 0), ("Moe", 1), ("Jack", 2)))
    (xs zipWithIndex) should be ("Manny" -> 0 :: "Moe" -> 1 :: "Jack" -> 2 :: Nil)
  }

  test("`sameElements` will return true if the two iterables produce the same elements in the same order") {
    val xl = List("Manny", "Moe", "Jack")
    val yl = List("Manny", "Moe", "Jack")
    xl sameElements yl should be (true)

    val zl = List("Manny", "Jack", "Joe")
    xl sameElements zl should be (false) //order matters

    val xs = Set(3, 2, 1, 4, 5, 6, 7)
    val ys = Set(7, 2, 1, 4, 5, 6, 3)
    xs sameElements ys should be (true) //order does not matter with Set

    val vs = Set(1, 2, 3)
    val us = Set(3, 2, 1)
    vs sameElements us should be (false) //caution - see below!
    /* Note that very small Sets (containing up to 4 elements) are implemented differently to larger Sets;
       as a result, their iterators produce the elements in the order that they were originally added.
       This causes the surprising (and arguably incorrect) behaviour in the final example above */
  }
}
