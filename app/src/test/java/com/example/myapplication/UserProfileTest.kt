import com.example.myapplication.ProfileValidator
import org.junit.Assert.*
import org.junit.Test

class ProfileValidatorTest {

    @Test
    fun `name validation returns true for non-empty name`() {
        // Arrange
        val name = "Ma Teng"

        // Act
        val result = ProfileValidator.isValidName(name)

        // Assert
        assertTrue("Name should be valid", result)
    }

    @Test
    fun `name validation returns false for empty name`() {
        // Arrange
        val name = ""

        // Act
        val result = ProfileValidator.isValidName(name)

        // Assert
        assertFalse("Name should be invalid", result)
    }

    @Test
    fun `email validation returns true for valid email`() {
        // Arrange
        val email = "d22lu@uwaterloo.ca"

        // Act
        val result = ProfileValidator.isValidEmail(email)

        // Assert
        assertTrue("Email should be valid", result)
    }

    @Test
    fun `email validation returns false for invalid email`() {
        // Arrange
        val email = "iNvAliD.com"

        // Act
        val result = ProfileValidator.isValidEmail(email)

        // Assert
        assertFalse("Email should be invalid", result)
    }

    @Test
    fun `location validation returns true for non-empty location`() {
        // Arrange
        val location = "200 University Avenue Waterloo, ON"

        // Act
        val result = ProfileValidator.isValidLocation(location)

        // Assert
        assertTrue("Location should be valid", result)
    }

    @Test
    fun `location validation returns false for empty location`() {
        // Arrange
        val location = ""

        // Act
        val result = ProfileValidator.isValidLocation(location)

        // Assert
        assertFalse("Location should be invalid", result)
    }
}
