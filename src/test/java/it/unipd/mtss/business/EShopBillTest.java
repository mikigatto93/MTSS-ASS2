////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;


public class EShopBillTest {
    private final static double DELTA = 0.0001;
    
    private static EShopBill shop;
    private static User user;
    private ArrayList<EItem> orderList;
    private static EItem mouse;
    private static EItem processor;
    private static EItem motherboard;
    private static EItem keyboard, keyboard2;

    
    
    @BeforeClass
    public static void beforeClass() {
        shop = new EShopBill();
        user = new User("nome", "cognome");
        
        mouse = new EItem(EItemType.MOUSE, "Mouse 1", 10);
        processor = 
                new EItem(EItemType.PROCESSOR, "Processore 1", 20);
        motherboard = 
                new EItem(EItemType.MOTHERBOARD, 
                        "Scheda Madre 1", 25);
        keyboard = new 
                EItem(EItemType.KEYBOARD, "Tastiera 1", 12.5);
        keyboard2 = new 
                EItem(EItemType.KEYBOARD, "Tastiera 2", 10.5);
    }  
        
    
    @Before
    public void beforeTests() {
        
        orderList = new ArrayList<EItem>();
        orderList.add(motherboard);
        orderList.add(mouse);
        orderList.add(processor);
        orderList.add(keyboard);
        orderList.add(keyboard2);
    }
    
    //1
    @Test
    public void testGetOrderPriceWithBasicOrderNoDiscount() 
            throws BillException {
        assertEquals(78, shop.getOrderPrice(orderList, user), DELTA);
    }
    
    
    @Test(expected = BillException.class)
    public void testGetOrdePriceThrowsExcepionOnEmptyList() 
            throws BillException
    {
        ArrayList<EItem> emptyOrderList = new ArrayList<EItem>();
        
        try{
            shop.getOrderPrice(emptyOrderList, user);
        }
        catch(BillException e) {
            assertEquals("The order list cannot be empty", e.getMessage());
            throw e;
        }
    }
    
}