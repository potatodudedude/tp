//package seedu.address.model.person;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//public class TelegramHandleTest {
//
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new TelegramHandle(null));
//    }
//
//    @Test
//    public void constructor_invalidTelegramHandle_throwsIllegalArgumentException() {
//        String invalidTelegramHandle = "";
//        assertThrows(IllegalArgumentException.class, () -> new TelegramHandle(invalidTelegramHandle));
//    }
//
//    @Test
//    public void isValidTelegramHandle() {
//        // null telegram handle
//        assertThrows(NullPointerException.class, () -> TelegramHandle.isValidTelegramHandle(null));
//
//        // invalid telegram handle
//        assertFalse(TelegramHandle.isValidTelegramHandle("")); // empty string
//        assertFalse(TelegramHandle.isValidTelegramHandle(" ")); // spaces only
//        assertFalse(TelegramHandle.isValidTelegramHandle("peter*")); // contains non-alphanumeric characters
//        assertFalse(TelegramHandle.isValidTelegramHandle("peter")); // does not start with @
//        assertFalse(TelegramHandle.isValidTelegramHandle("@pete")); // less than 5 characters
//        assertFalse(TelegramHandle.isValidTelegramHandle("@peter*")); // contains non-alphanumeric characters
//        assertFalse(TelegramHandle.isValidTelegramHandle("@peter ")); // trailing space
//
//        // valid telegram handle
//        assertTrue(TelegramHandle.isValidTelegramHandle("@peter")); // alphabets only
//        assertTrue(TelegramHandle.isValidTelegramHandle("@12345")); // numbers only
//        assertTrue(TelegramHandle.isValidTelegramHandle("@peter123")); // alphanumeric characters
//        assertTrue(TelegramHandle.isValidTelegramHandle("@peter_parker")); // with underscore
//        assertTrue(TelegramHandle.isValidTelegramHandle("@CapitalTan")); // with capital letters
//        assertTrue(TelegramHandle.isValidTelegramHandle("@DavidRogerJacksonRayJr2nd")); // long telegram handle
//    }
//
//    @Test
//    public void equals() {
//        TelegramHandle telegramHandle = new TelegramHandle("@ValidHandle");
//
//        // same values -> returns true
//        assertTrue(telegramHandle.equals(new TelegramHandle("@ValidHandle")));
//
//        // same object -> returns true
//        assertTrue(telegramHandle.equals(telegramHandle));
//
//        // null -> returns false
//        assertFalse(telegramHandle.equals(null));
//
//        // different types -> returns false
//        assertFalse(telegramHandle.equals(5.0f));
//
//        // different values -> returns false
//        assertFalse(telegramHandle.equals(new TelegramHandle("@OtherValidHandle")));
//    }
//}
