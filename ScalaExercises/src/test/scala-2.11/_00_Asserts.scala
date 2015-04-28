/**
 * Created by inieto on 24/04/15.
 */
import org.scalatest.{Matchers, FunSuite}

class _00_Asserts extends FunSuite with Matchers {
  test("ScalaTest makes three assertions available by default in any style trait") {
    //assert for general assertions;
    //assertResult to differentiate expected from actual values;
    //intercept to ensure a bit of code throws an expected exception.

    val left = 2
    val right = 1
    assert(left != right)
  }

  test("ScalaTest provides a domain specific language (DSL) for expressing assertions in tests using the word should") {
    val result = 3
    result should equal (3) // can customize equality
    result should === (3)   // can customize equality and enforce type constraints
    result should be (3)    // cannot customize equality, so fastest to compile
    result shouldEqual 3    // can customize equality, no parentheses required
    result shouldBe 3       // cannot customize equality, so fastest to compile, no parentheses required
  }

  test("true and false values can be compared with should matchers") {
    true should be(true)
  }

  test("Booleans in asserts can test equality") {
    val v1 = 4
    val v2 = 4
    v1 === v2 //=== is an assert. It is from ScalaTest, not from the Scala language.
  }

  test("Sometimes we expect you to fill in the values") {
    assert(2 == 1 + 1)
  }
}
