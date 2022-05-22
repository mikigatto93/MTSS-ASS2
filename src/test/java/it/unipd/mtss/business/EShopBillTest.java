////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
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
        user = new User("nome", "cognome", 20);
        
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
    
    //2
    @Test
    public void testApplyProcessorDiscountIs0IfLessOrEqualThan5Processors()
    {
        //3 processors
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc2", 40));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc3", 45));
        assertEquals(0.0, shop.applyProcessorDiscount(orderList), DELTA);
        
        
        //5 processors
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc4", 30));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc5", 55));
        assertEquals(0.0, shop.applyProcessorDiscount(orderList), DELTA);
        
    }
    
    
    @Test
    public void 
    testApplyProcessorDiscountIsCorrectIfMoreThan5Processors()
    {
        //6 processors
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc2", 40));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc3", 45));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc4", 30));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc5", 55));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc6", 20));
        assertEquals(10, shop.applyProcessorDiscount(orderList), DELTA);

    }
    
    @Test
    public void testGetOrderPriceDiscountIsAppliedIfMoreThan5Processors()
            throws BillException
    {
        //6 processors
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc2", 40));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc3", 45));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc4", 30));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc5", 55));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc6", 20));
        assertEquals(258, shop.getOrderPrice(orderList, user), DELTA);

    }
    
    @Test
    public void 
    testGetOrderPriceDiscountIsNotAppliedIfLessOrEqualThan5Processors()
            throws BillException
    {
        //4 processors
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc2", 40));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc3", 45));
        orderList.add(new EItem(EItemType.PROCESSOR, "Proc4", 30));
        assertEquals(193, shop.getOrderPrice(orderList, user), DELTA);

    }
    
    
    //3
    @Test
    public void testApplyMouseGiftIs0IfLessOrEqualThan10Mouses() {
        
        // 1 mouse
        assertEquals(0.0, shop.applyMouseGift(orderList), DELTA);
        
        //10 mouses
        for (int i = 0; i < 9; i++) {
            orderList.add(new EItem(EItemType.MOUSE, "Mouse"+i, 5+i));
        }
        
        assertEquals(0.0, shop.applyMouseGift(orderList), DELTA);
        
    }
    
    
    @Test
    public void testApplyMouseGiftIsGiftedIfMoreThan10Mouses() {
        
        
        //11 mouses
        for (int i = 0; i < 10; i++) {
            orderList.add(new EItem(EItemType.MOUSE, "Mouse"+i, 5+i));
        }
        
        assertEquals(5.0, shop.applyMouseGift(orderList), DELTA);
        
    }
    
    
    @Test
    public void testGetOrderPriceMouseGiftIsAppliedIfMoreThan10Mouses()
            throws BillException
    {
        //11 mouses
        for (int i = 0; i < 10; i++) {
            orderList.add(new EItem(EItemType.MOUSE, "Mouse"+i, 5+i));
        }
        assertEquals(168, shop.getOrderPrice(orderList, user), DELTA);

    }
    
    @Test
    public void 
    testGetOrderPriceMouseGiftIsNotAppliedIfLessOrEqualThan10Mouses()
            throws BillException
    {
        //4 mouses
        orderList.add(new EItem(EItemType.MOUSE, "Mouse2", 5));
        orderList.add(new EItem(EItemType.MOUSE, "Mouse3", 5.5));
        orderList.add(new EItem(EItemType.MOUSE, "Mouse4", 5));
        
        assertEquals(93.5, shop.getOrderPrice(orderList, user), DELTA);

    }
    
  //4
    @Test
    public void 
    testApplyGiftSameMouseAndKeyboardIs0IfMouseAndKeyboardDifferentQuantity() {
        //3 mouses and 2 keyboard
        orderList.add(new EItem(EItemType.MOUSE, "Mouse2", 5));
        orderList.add(new EItem(EItemType.MOUSE, "Mouse3", 7));
        
        assertEquals(0.0, shop.applyGiftSameMouseAndKeyboard(orderList), DELTA);
    }
    
    @Test
    public void 
    testApplyGiftSameMouseAndKeyboardIsGiftedIfMouseAndKeyboardSameQuantity() {
      //2 mouses and 2 keyboard
        orderList.add(new EItem(EItemType.MOUSE, "Mouse2", 30));
        
        assertEquals(10.0, shop.applyGiftSameMouseAndKeyboard(orderList), 
                DELTA);
    }
    
    @Test
    public void 
    testGetOrderPriceIsNotGiftedCheapItemIfMouseAndKeyboardDifferentQuantity() 
            throws BillException {
        //3 mouses and 2 keyboard
        orderList.add(new EItem(EItemType.MOUSE, "Mouse2", 5));
        orderList.add(new EItem(EItemType.MOUSE, "Mouse3", 7));
        
        assertEquals(90, shop.getOrderPrice(orderList, user), DELTA);
    }
    
    @Test
    public void 
    testGetOrderPriceIsGiftedCheapItemIfMouseAndKeyboardSameQuantity() 
            throws BillException {
        //3 mouses and 3 keyboard
        orderList.add(new EItem(EItemType.MOUSE, "Mouse2", 5));
        orderList.add(new EItem(EItemType.MOUSE, "Mouse3", 7));
        orderList.add(new EItem(EItemType.KEYBOARD, "Mouse3", 4.5));
        
        assertEquals(90, shop.getOrderPrice(orderList, user), DELTA);
    }
    
    //5
    @Test
    public void 
    testGetOrderPrice10DiscountAppliedIfTotalMoreThan1000() 
            throws BillException {
      
        orderList.add(new EItem(EItemType.MOTHERBOARD, "MB2", 800));
        orderList.add(new EItem(EItemType.PROCESSOR, "MB3", 200));
        
        assertEquals(970.2, shop.getOrderPrice(orderList, user), DELTA);
    }
    
    
    @Test
    public void 
    testGetOrderPrice10DiscountNotAppliedIfTotalLessOrEqualThan1000() 
            throws BillException {
        
        orderList.add(new EItem(EItemType.MOTHERBOARD, "MB2", 800));
        assertEquals(878, shop.getOrderPrice(orderList, user), DELTA);
        
        orderList.add(new EItem(EItemType.PROCESSOR, "MB3", 122));
        assertEquals(1000, shop.getOrderPrice(orderList, user), DELTA);
    }
    
    //6
    @Test(expected = BillException.class)
    public void testGetOrdePriceThrowsExcepionOnOrderListMoreThanThirtyItems() 
            throws BillException
    {
        for(int i=0; i<30;i++) {
            orderList.add(keyboard);
        }
        
        try{
            
            shop.getOrderPrice(orderList, user);
        }
        catch(BillException e) {
            
            assertEquals("The order list cannot contain more than thirty items", 
                    e.getMessage());
            
            throw e;
        }
    }
    
    //7
    @Test
    public void testGetOrderPrice2EurCommissionIsAppliedIfTotLessThan10()
        throws BillException
    {
        ArrayList<EItem> orderList = new ArrayList<EItem>();
        orderList.add(new EItem(EItemType.MOUSE, "Mouse1", 3));
        orderList.add(new EItem(EItemType.MOTHERBOARD, "k1", 5));
        
        assertEquals(10, shop.getOrderPrice(orderList, user), DELTA);
    }
    
    @Test
    public void 
    testGetOrderPrice2EurCommissionIsNotAppliedIfTotMoreOrEqualThan10()
            throws BillException
    {
            // tot = 10
            ArrayList<EItem> orderList = new ArrayList<EItem>();
            orderList.add(new EItem(EItemType.MOTHERBOARD, "MB1", 3));
            orderList.add(new EItem(EItemType.KEYBOARD, "k1", 7));
            assertEquals(10, shop.getOrderPrice(orderList, user), DELTA);
            
            //tot = 11
            orderList.add(new EItem(EItemType.MOTHERBOARD, "MB2", 1));
            assertEquals(11, shop.getOrderPrice(orderList, user), DELTA);
    }
    
    //8
    @Test
    public void testIsGiftOrderApplicableTimeAndAgeConditions() {
        
        User u = new User("nome2", "cognome2", 17);

        shop.setCustomTime(LocalTime.of(17,50));
        assertFalse(shop.isGiftOrderApplicable(u));
        assertFalse(shop.isGiftOrderApplicable(user));
        
        shop.setCustomTime(LocalTime.of(18,0));
        assertFalse(shop.isGiftOrderApplicable(u));
        assertFalse(shop.isGiftOrderApplicable(user));
        
        shop.setCustomTime(LocalTime.of(18,1));
        assertTrue(shop.isGiftOrderApplicable(u));
        assertFalse(shop.isGiftOrderApplicable(user));

        shop.setCustomTime(LocalTime.of(18,59));
        assertTrue(shop.isGiftOrderApplicable(u));
        assertFalse(shop.isGiftOrderApplicable(user));

        shop.setCustomTime(LocalTime.of(19,00));
        assertFalse(shop.isGiftOrderApplicable(u));
        assertFalse(shop.isGiftOrderApplicable(user));

        shop.turnCustomTimeModeOff();
    }
    
    
    @Test
    public void testGetOrderPriceIfOrdersGiftIsAppliedWith10Users() 
            throws BillException{
        
        User[] users ={
            new User("a", "b", 5),
            new User("c", "d", 10),
            new User("e", "f", 8),
            new User("g", "h", 10),
            new User("i", "l", 12),
            new User("m", "n", 15),
            new User("o", "p", 9),
            new User("q", "r", 10),
            new User("s", "t", 3),
            new User("u", "v", 16),
            new User("w", "z", 14)
        };

        shop.setCustomTime(LocalTime.of(18,15));
        shop.setRandomSeed(44);

        
        double price = 78;

        assertEquals(0, shop.getOrderPrice(orderList, users[0]), DELTA);
        assertEquals(price, shop.getOrderPrice(orderList, user), DELTA);
        assertEquals(0, shop.getOrderPrice(orderList, users[1]), DELTA);
        assertEquals(0, shop.getOrderPrice(orderList, users[2]), DELTA);
        assertEquals(0, shop.getOrderPrice(orderList, users[3]), DELTA);
        assertEquals(0, shop.getOrderPrice(orderList, users[4]), DELTA);
        assertEquals(price, shop.getOrderPrice(orderList, users[2]), DELTA);
        assertEquals(price, shop.getOrderPrice(orderList, users[5]), DELTA);
        assertEquals(0, shop.getOrderPrice(orderList, users[5]), DELTA);
        assertEquals(0, shop.getOrderPrice(orderList, users[6]), DELTA);
        assertEquals(0, shop.getOrderPrice(orderList, users[7]), DELTA);
        assertEquals(0, shop.getOrderPrice(orderList, users[8]), DELTA);
        assertEquals(0, shop.getOrderPrice(orderList, users[9]), DELTA);
        assertEquals(price, shop.getOrderPrice(orderList, users[10]), DELTA);

        shop.turnCustomTimeModeOff();
        shop.resetRandom();
        shop.resetGiftOrders();
    }
    
}
