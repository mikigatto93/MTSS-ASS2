////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;


import java.util.List;
import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;

public class EShopBill implements Bill {

   
    
    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user) 
            throws BillException 
    {
        
        if (!itemsOrdered.isEmpty()) {
            
            
            double tot = 0.0;
            for (EItem item : itemsOrdered) {    
                tot += item.getPrice();
            }
            
            tot -= applyProcessorDiscount(itemsOrdered) + 
                    applyMouseGift(itemsOrdered) + 
                    applyGiftSameMouseAndKeyboard(itemsOrdered);
            
            if (tot > 1000) {
                tot -= (tot*0.1);
            }
            
            return tot;
                

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
    
}
