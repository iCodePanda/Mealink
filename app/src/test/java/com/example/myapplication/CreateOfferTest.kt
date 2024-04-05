import com.example.myapplication.*
import org.junit.Test
import org.junit.Assert.*

class CreateOfferTest {
    @Test
    fun `offer validation returns true for valid offer parameters`() {
        val emptyName = "" // arrange
        val emptyNameResult = offerValidation.testEmptyString(emptyName) // act
        assertTrue(emptyNameResult) // assert

        val nonEmptyName = "hello" // arrange
        val nonEmptyNameResult = offerValidation.testEmptyString(nonEmptyName) // act
        assertFalse(nonEmptyNameResult) // assert

        val portion = "125" // arrange
        val portionResult = offerValidation.validatePortion(portion) // act
        assertFalse(portionResult) // assert

        val badPortion = "1oo" // arrange
        val badPortionResult = offerValidation.validatePortion(badPortion) // act
        assertTrue(badPortionResult) // assert

        val time = System.currentTimeMillis() - 10000 // arrange
        val timeResult = offerValidation.validateTime(time) // act
        assertTrue(timeResult) // assert

        val badTime = System.currentTimeMillis() + 10000 // arrange
        val badTimeResult = offerValidation.validateTime(badTime) // act
        assertFalse(badTimeResult) // assert
    }
}