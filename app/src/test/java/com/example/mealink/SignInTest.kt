import com.example.mealink.AuthValidator
import org.junit.Test
import org.junit.Assert.*


class SignInTest {

    @Test
    fun `email validation returns true for valid email`() {
        // Arrange
        val validEmail = "sprint2@gmail.com"

        // Act
        val result = AuthValidator.isValidEmail(validEmail)

        // Assert
        assertTrue("The email should be valid", result)
    }

    @Test
    fun `email validation returns false for invalid email`() {
        // Arrange
        val invalidEmail = "invalidemail.com"

        // Act
        val result = AuthValidator.isValidEmail(invalidEmail)

        // Assert
        assertFalse("The email should be invalid", result)
    }

    @Test
    fun `password validation returns true for non-empty password`() {
        // Arrange
        val validPassword = "password123"

        // Act
        val result = AuthValidator.isValidPassword(validPassword)

        // Assert
        assertTrue("The password should be valid", result)
    }

    @Test
    fun `password validation returns false for empty password`() {
        // Arrange
        val invalidPassword = ""

        // Act
        val result = AuthValidator.isValidPassword(invalidPassword)

        // Assert
        assertFalse("The password should be invalid", result)
    }

}
