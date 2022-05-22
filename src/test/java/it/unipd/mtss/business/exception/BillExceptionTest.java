////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BillExceptionTest {
    @Test
    public void testExceptionMessageTheCorrectOne() {
        String expectedMessage = "The correct message";
        BillException exception = new BillException(expectedMessage);

        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
