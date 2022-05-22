////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EItemTest {
    private final static double DELTA = 0.0001;
    private EItem item;
    
    @Before
    public void beforeTests() {
        item = new EItem(EItemType.MOUSE, "Mouse 1", 9.5);
    }
    
    @Test
    public void testGetItemType(){
        assertEquals(EItemType.MOUSE, item.getItemType());
    }
    
    @Test
    public void testGetName(){
        assertEquals("Mouse 1", item.getName());
    }
    
    @Test
    public void testGetPrice(){
        assertEquals(9.5, item.getPrice(), DELTA);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testExceptionWhenPriceIsNegative() {
        item = new EItem(EItemType.PROCESSOR, "Processore 1", -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testExceptionWhenPriceIsZero() {
        item = new EItem(EItemType.PROCESSOR, "Processore 2", 0);
    }
}
