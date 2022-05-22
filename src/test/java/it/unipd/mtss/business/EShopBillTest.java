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
    
}