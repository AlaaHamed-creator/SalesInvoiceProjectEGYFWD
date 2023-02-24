
package Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Eng.Alaa Hamed
 */
public class RowsTableModel extends AbstractTableModel{
private ArrayList<InvoiceDetails> invoiceDetails;
private String[] coulmns={"No","Item Name","Item Price","Count","Item Total"};

    public RowsTableModel(ArrayList<InvoiceDetails> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }


    @Override
    public int getRowCount() {
        return invoiceDetails.size();
    }

    @Override
    public int getColumnCount() {
    return coulmns.length;    
    }
    
     @Override
    public String getColumnName(int coulmn) {
    return coulmns[coulmn];    
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       InvoiceDetails invoice=invoiceDetails.get(rowIndex);
       
       switch(columnIndex)
       {
           case 0:return invoice.getInvoice().getInvoiceNo();
           case 1:return invoice.getItemName();    
           case 2:return invoice.getItemPrice();
           case 3:return invoice.getCount();
           case 4:return invoice.getItemsTotalPrice();
           default :return " ";
       }
       
    }
    
}
