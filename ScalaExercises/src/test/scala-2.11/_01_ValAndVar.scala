import org.scalatest.{FunSuite, Matchers}

/**
 * Created by inieto on 27/04/15.
 */
class _01_ValAndVar extends FunSuite with Matchers {

  test("Immutable variables are declared with the keyword val") {
    val age: Int = 22 //inmutable
  }

  test("Mutable variables are declared with the keyword var. " +
    "But they have to be initialised at the time of declaration.") {
    var age: Int = 22 //mutable
    age = 35
  }

  test("Your turn. Remember, var's may be reassigned. And val's may not be reassigned") {
    var a = 5
    a should be(5)
    a = 7
    a should be(7)

    val b = 5
    b should be(5)

    // What happens if you uncomment these lines?
    // b = 7  //error: "Reassignment to val"
    // b should be (7)
  }
}
