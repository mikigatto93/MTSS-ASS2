////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;


import java.util.List;
import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
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
            
            
            return tot;
                

        } else {
            throw new BillException("The order list cannot be empty");
        }
        
    }



    
    
}
