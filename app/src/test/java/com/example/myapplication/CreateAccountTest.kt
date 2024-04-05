import com.example.myapplication.*
import org.junit.Test
import org.junit.Assert.*


class CreateAccountTest {

    @Test
    fun `email validation returns true for valid email`() {
        // Arrange
        val validEmail = "sprint2@gmail.com"

        // Act
        val result = SignUpValidation.validateEmail(validEmail)

        // Assert
        assertTrue("The email should be valid", result)
    }

    @Test
    fun `email validation returns false for invalid email`() {
        // Arrange
        val badEmail4 = "@."

        // Act
        val result = SignUpValidation.validateEmail(badEmail4)

        // Assert
        assertFalse("Email should be invalid", result)
    }
    @Test

    fun `email validation returns false for email without user`() {
        // Arrange
        val badEmail = "gmail.com"

        // Act
        val result = SignUpValidation.validateEmail(badEmail)

        // Assert
        assertFalse("Email should be invalid", result)
    }

    @Test
    fun `password validation returns true for valid password`() {
        // Arrange
        val password = "abcd1234"

        // Act
        val result = SignUpValidation.validatePassword(password)

        // Assert
        assertTrue("Password is valid although weak", result)
    }

    @Test
    fun `password validation returns false for no-number password`() {
        // Arrange
        val password = "qwertyuiop"

        // Act
        val result = SignUpValidation.validatePassword(password)

        // Assert
        assertFalse("Password should be invalid (no numbers)", result)
    }

    @Test
    fun `password validation returns false for short password` () {
        // Arrange
        val password = "abcdef"

        // Act
        val result = SignUpValidation.validatePassword(password)

        // Assert
        assertFalse("Password is too short, should be at least 8 chars long", result)
    }

    @Test
    fun `postal code validation returns true for syntactically valid postal code` () {
        // Arrange
        val location = "A1A 1A1"

        // Act
        val result = SignUpValidation.validatePostalCode(location)

        // Assert
        assertTrue("Valid postal code", result)
    }

    @Test
    fun `postal code validation returns false for invalid postal code` () {
        // Arrange
        val location = "90210"

        // Act
        val result = SignUpValidation.validatePostalCode(location)

        // Assert
        assertFalse("Invalid postal code", result)
    }



}