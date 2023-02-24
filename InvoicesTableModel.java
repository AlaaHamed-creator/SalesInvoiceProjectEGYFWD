/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Eng.Alaa Hamed
 */
public class InvoicesTableModel extends AbstractTableModel{
private ArrayList<Invoice> invoices;
private String[] coulmns={"No","Date","Customer","Total"};

    public InvoicesTableModel(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }


    @Override
    public int getRowCount() {
         return invoices.size();
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
        Invoice invoice=invoices.get(rowIndex);
        
        switch(columnIndex)
        {
            case 0: return invoice.getInvoiceNo();
            case 1: return invoice.getInvoiceDate();
            case 2: return invoice.getCustomerName();
            case 3: return invoice.getInvoiceTotal();
            default: return "";
        }   
    }
    
}
