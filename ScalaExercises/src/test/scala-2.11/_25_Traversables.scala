import org.scalatest.{FunSuite, Matchers}

import scala.collection._

/**
 * Created by inieto on 27/04/15.
 */
class _25_Traversables extends FunSuite with Matchers {

  /* At the top of the collection hierarchy is trait Traversable. Its only abstract operation is 'foreach'

      def foreach[U](f: Elem => U)

    Collection classes that implement 'Traversable' just need to define this method; all other methods can be inherited from 'Traversable'.

    The `foreach` method is meant to traverse all elements of the collection, and apply the given operation, `f`, to each element.
    The type of the operation is `Elem => U`, where `Elem` is the type of the collection’s elements and `U` is an arbitrary result type.
    The invocation of 'f' is done for its side effect only; in fact any function result of 'f' is discarded by 'foreach'.  */

  test("Traversables are the superclass of `Lists`, `Arrays`, `Maps`, `Sets`, `Streams`, and more." +
    "The methods involved can be applied to each other in a different type. `++` appends two Traversables together.") {
    val set = Set(1, 9, 10, 22)
    val list = List(3, 4, 5, 10)

    val resultSet = set ++ list
    val resultList = list ++ set

    resultSet should be (Set(1, 9, 10, 22, 3, 4, 5)) //it transforms in a Set, and 10 was duplicated
    resultList should be (List(3, 4, 5, 10, 1, 9, 10, 22))

    resultSet.size should be (7)
    resultList.size should be (8)
  }

  test("`map` will apply the given function on all elements of a 'Traversable' and return a new collection of the result") {
    val set = Set(1, 3, 4, 6)
    val result = set.map(_ * 4)   //or set map (_ * 4)
    result.last should be (24)
  }

  test("`flatten` will smash all child 'Traversables' within a 'Traversable'") {
    val list = List(List(1), List(2, 3, 4), List(5, 6, 7), List(8, 9, 10))
    list.flatten should be (List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
  }

  test("`flatMap` will not only apply the given function on all elements of a 'Traversable', " +
       " but all elements within the elements and 'flatten' the results") {
    val list = List(List(1), List(2, 3, 4), List(5, 6, 7), List(8, 9, 10))
    val result = list flatMap(_.map(_ * 4)) //applies a map to each List "_" and multiplies 4 times each element "_"
    result should be (List(4, 8, 12, 16, 20, 24, 28, 32, 36, 40))
  }

  test("`flatMap` of `Options` will filter out all `Nones` and Keep the `Some`s") {
    val list = List(1, 2, 3, 4, 5)
    val result = list.flatMap(it => if (it % 2 == 0) Some (it) else None)
    result should be (List(2, 4))
  }

  test("~Mine~ [functions|lambdas] on [maps|flatMaps] on Lists") {
    val list = List(1, 2, 3, 4, 5)
    /* Keep in mind that def 'f' will execute the calculation each time, and val only once.
       def f = System.nanoTime()  //f: Long
       f                          //res1: Long = 41847861510051
       f                          //res2: Long = 41849122659780  --different

       val f = System.nanoTime()  //f: Long = 41857126375938
       f                          //res3: Long = 41857126375938
       f                          //res4: Long = 41857126375938  ==same
     */
    val g = (it: Int) => if (it % 2 == 0) Some (it) else None   //could have been "def g = (it: Int)... too"
    // "g" is a lambda (function as an object). It can be defined with "def" (like functions, evaluated each time) or
    // with "val" (the lambda will be instantiated once and stored with a reference)

    list.flatMap(g(_)) should be (List(2, 4))
    list.flatMap(f(_)) should be (List(2, 4)) //same as "g"
    list.flatMap(f) should be (List(2, 4))    //compiler can infer types with functions,
    // therefore it knows that "list" is a list of Int, "flatMap" takes each Int and "f" receives each Int at a time.

    def f (it: Int) = if (it % 2 == 0) Some (it) else None
    // "f" is a function. It must be declared with "def", not val. Functions can be declared anywhere within the scope

    //Same meaning, different syntax
    list.map(it => if (it % 2 == 0) Some (it) else None) should equal (list.map(x => f(x)))
    list.map(x => f(x)) should equal (list.map(f(_)))
    list.map(f(_)) should equal (list.map(f))

    list.map(it => if (it % 2 == 0) Some (it) else None) should equal (list.map(x => g(x)))
    list.map(x => g(x)) should equal (list.map(g(_)))
    list.map(g(_)) should equal (list.map(g))

    list.flatMap(it => if (it % 2 == 0) Some (it) else None) should equal (list.flatMap(x => f(x)))
    list.flatMap(x => f(x)) should equal (list.flatMap(f(_)))
    list.flatMap(f(_)) should equal (list.flatMap(f))

    list.flatMap(it => if (it % 2 == 0) Some (it) else None) should equal (list.flatMap(x => g(x)))
    list.flatMap(x => g(x)) should equal (list.flatMap(g(_)))
    //list.flatMap(g(_)) should equal (list.flatMap(g))  --not this. It can't infer types with flatMap as it does with simple map
  }

  /* ~Mine~
  Consider:
      List(41, "cat") map { case i: Int => i + 1 }
      > scala.MatchError: cat (of class java.lang.String)
  As expected this crashes, because the pattern match doesn’t know what to do when the string “cat” is passed to it.

  On the other hand, this example doesn’t crash:
      List(41, "cat") collect { case i: Int => i + 1 }
      > res1: List[Int] = List(42)

  So what’s the difference? The difference is that map expects a "Total Function" and collect expects a "Partial Function"
  (see: http://en.wikipedia.org/wiki/Partial_function)
  "map" expects a function that maps all values, and "collect" expects a "partial function" defined only for a subset of the input
  like "sqrt(x)" is not defined for x<0 or "a/b" is not defined for b==0

  We could have defined a total function for "map", and use a default value for the default case:
      List(41, "cat") map { case i: Int => Some(i + 1); case _ => None }
      > List[Option[Int]] = List(Some(42), None)
  Or we could have relied on "flatMap" with it's intelligence on Some and None to achieve the same result:
      List(41, "cat") flatMap { case i: Int => Some(i + 1); case _ => None }
      > List[Int] = List(42)
  We could have filtered the input list before mapping it too:
      List(41, "cat") filter (_.isInstanceOf[Int]) map { case i: Int => i + 1 }
      > List[Int] = List(42)
  But it's more concise with collect and partial functions:
      List(41, "cat") collect { case i: Int => i + 1 }
      > List[Int] = List(42)

  src: http://blog.bruchez.name/2011/10/scala-partial-functions-without-phd.html
    && https://gist.github.com/manjuraj/8c767ac4d6814be2813e */

  test("`collect` will apply a partial function to all elements of a Traversable and will return a different collection." +
       "In this exercise, a case fragment is a partial function") {
    val list = List(4, 6, 7, 8, 9, 13, 14)
    val result = list.collect {
      case x: Int if (x % 2 == 0) => x * 3
    }
    result should be (List(12, 18, 24, 42))
  }
  /* Now if you think about it you will notice lots of situations like this in your programs, where functions are
     expected to work properly only for some input values. If the function is called with a disallowed value,
     it will typically crash, yield a special return value, or throw an exception (and this should better be documented).
     In short, partial function are very common in real-life programs even if you don’t know about it. */

  test("`collect` will apply a partial function to all elements of a Traversable and will return a different collection." +
       "In this exercise, two case fragments are chained to create a more robust result") {
    val list = List(4, 6, 7, 8, 9, 13, 14)

    val partialFunction1: PartialFunction[Int, Int] = {
      case x: Int if x % 2 == 0 => x * 3
    }
    val partialFunction2: PartialFunction[Int, Int] = {
      case x: Int if x % 2 != 0 => x * 4
    }
    val result = list collect (partialFunction1 orElse partialFunction2)
    result should be (List(12, 18, 28, 24, 36, 52, 42))
  }

  test("`foreach` will apply a function to all elements of a 'Traversable', but unlike the `map` function, " +
    "it will not return anything since the return type is Unit, which is like a void return type in Java, C++") {
    val list = List(4, 6, 7, 8, 9, 13, 14)
    list.foreach(num => println(num * 4))
    list should be (List(4, 6, 7, 8, 9, 13, 14))
  }

  test("~Mine~ `foreach` is only used for it's side efects") {
    List(1, 2) foreach (_ * 2) should be ( /*Nothing*/ )   //<(), the Unit value> is returned by `foreach`
  }

  test("`toArray` will convert any 'Traversable' to an 'Array', which is a special wrapper around a primitive Java array.") {
    val set = Set(4, 6, 7, 8, 9, 13, 14)
    val result = set.toArray
    result.isInstanceOf[Array[Int]] should be (true)
  }

  test("`toList` will convert any 'Traversable' to a 'List'") {
    val set = Set(4, 6, 7, 8, 9, 13, 14)
    val result = set.toList
    result.isInstanceOf[List[_]] should be (true)
  }

  test("`toList`, as well as other conversion methods like `toSet`, `toArray`, will not convert if the collection type is the same") {
    val list = List(5, 6, 7, 8, 9)
    val result = list.toList
    result eq list should be (true)
    result should equal (list)
  }

  test("`toIterable` will convert any Traversable to an Iterable. This is a base trait for all Scala collections that" +
       " define an iterator method to step through one-by-one the collection's elements.") {
    val set = Set(4, 6, 7, 8, 9, 13, 14)
    val result = set.toSeq
    result.isInstanceOf[Iterable[_]] should be (true)
  }

  test("`toSeq` will convert any 'Traversable' to a 'Seq' which is an ordered 'Iterable' and is the superclass to 'List', 'Queues'" +
       " and 'Vectors'. 'Sequences' provide a method apply for indexing. Indices range from 0 up the the length of a sequence.") {
    val set = Set(4, 6, 7, 8, 9, 13, 14)
    val result = set.toSeq
    result.isInstanceOf[Seq[_]] should be (true)
  }

  test("`toIndexedSeq` will convert any 'Traversable' to an 'IndexedSeq' which is an indexed sequence used in 'Vectors' and 'Strings'") {

  }
}
