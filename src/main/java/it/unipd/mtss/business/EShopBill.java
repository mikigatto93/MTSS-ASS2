////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;

public class EShopBill implements Bill {
    private final static LocalTime startTimeGiftOrder = LocalTime.of(18,00);
    private final static LocalTime endTimeGiftOrder = LocalTime.of(19,00);
    
    private HashSet<User> giftedOrderUsers = new HashSet<User>();
    private int giftedOrdersCounter = 10;
    private Random rand = new Random();
    private boolean customTimeMode = false;
    private LocalTime customTime;
    
    //8
    public void setCustomTime(LocalTime myTime){
        customTimeMode = true;
        customTime = myTime;
    }

    public void turnCustomTimeModeOff(){
        customTimeMode = false;
    }

    public final LocalTime getTime(){
        
        if (customTimeMode) {
            return customTime;
        } else {
            return LocalTime.now();
        }
    }
    
    public void setRandomSeed(long l){
        rand.setSeed(l);
    }

    public void resetRandom(){
        rand=new Random();
    }

    public void resetGiftOrders() {
        giftedOrderUsers = new HashSet<User>();
        giftedOrdersCounter = 10;
    }
    
    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user) 
            throws BillException 
    {
        
        if (!itemsOrdered.isEmpty()) {
            
            if(itemsOrdered.size() > 30) {
                throw new BillException(
                        "The order list cannot contain more than thirty items"
                        );
            } else {
            
                double tot = 0.0;
                for (EItem item : itemsOrdered) {    
                    tot += item.getPrice();
                }
                
                tot -= applyProcessorDiscount(itemsOrdered) + 
                        applyGiftSameMouseAndKeyboard(itemsOrdered) +
                        applyMouseGift(itemsOrdered);
                
                
                if (tot < 10) {
                    tot += 2;
                }
                
                if (tot > 1000) {
                    tot -= (tot*0.1);
                }
                
                if (isGiftOrderApplicable(user)) {
                    if(rand.nextBoolean()){
                        tot = 0;
                        giftedOrderUsers.add(user);
                        giftedOrdersCounter--;
                    }
                    
                }
                
                return tot;
                
            
            }
        
        } else {
            throw new BillException("The order list cannot be empty");
        }
        
    }
    
    public double applyMouseGift(List<EItem> itemsOrdered) {
        int mouse_number = 0;
        double pmin = Double.POSITIVE_INFINITY;
        for(EItem item : itemsOrdered) {
            if(EItemType.MOUSE == item.getItemType() ) {
                if(pmin > item.getPrice()) {
                    pmin = item.getPrice();
                }
                mouse_number++;
            }
        }
        
        if (mouse_number > 10) {
            return pmin;   
        } else {
            return 0.0;
        }
            
        
        
    }

    public double applyProcessorDiscount(List<EItem> itemsOrdered) {
        double discount = 0.0;
        int processor_number = 0;
        double pmin = Double.POSITIVE_INFINITY;;
        for(EItem item : itemsOrdered) {
            if(EItemType.PROCESSOR == item.getItemType() ) {
                if(pmin > item.getPrice()) {
                    pmin = item.getPrice();
                }
                processor_number++;
            }
        }
        
        if(processor_number > 5) {
            discount = pmin*0.5;
        }
        
        return discount;
    }
    
    public double applyGiftSameMouseAndKeyboard(List<EItem> itemsOrdered) {
        double pmin=Double.POSITIVE_INFINITY;;
        int art_number=0;
        for(EItem item : itemsOrdered) {
            if(EItemType.MOUSE == item.getItemType() || 
               EItemType.KEYBOARD == item.getItemType() ) {
                
                    art_number = EItemType.MOUSE == item.getItemType() ? 
                            (art_number - 1) : (art_number + 1);
            }
               
            if(pmin > item.getPrice())
            {
                pmin = item.getPrice();
            }
            
            
        }
        
        if (art_number == 0) {
            return pmin;
        } else {
            return 0.0;
        }
    }
    
    
    public boolean isGiftOrderApplicable(User user){
        LocalTime now= getTime();
        
        boolean timeFlag = now.isAfter(startTimeGiftOrder) && 
                now.isBefore(endTimeGiftOrder);
        
        boolean userFlag = user.getAge() < 18 && 
                !giftedOrderUsers.contains(user);
        
        return  timeFlag && giftedOrdersCounter > 0 && userFlag;
    }
    
    
}
