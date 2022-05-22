////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;
    
    @Before
    public void beforeTests() {
        user = new User("nome", "cognome");
    }
    
    @Test
    public void testGetName(){
        assertEquals("nome", user.getName());
    }
    
    @Test
    public void testGetSurname(){
        assertEquals("cognome", user.getSurname());
    }
     
}
