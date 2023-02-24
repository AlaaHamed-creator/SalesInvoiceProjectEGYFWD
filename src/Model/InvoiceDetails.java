/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Eng.Alaa Hamed
 */
public class InvoiceDetails {
     //private int InvNo;
    private String itemName;
    private double itemPrice;
    private int count;
    private Invoice invoice;

    public InvoiceDetails() {}

    public InvoiceDetails( String itemName, double itemPrice, int count, Invoice invoice) {
        //this.InvNo = InvNo;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.invoice = invoice;
    }

   

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "InvoiceDetails{" + "InvNo=" + invoice.getInvoiceNo() + ", itemName=" + itemName + ", itemPrice=" + itemPrice + ", quantity=" + count + '}';
    }
 
    public double getItemsTotalPrice()
    {
        return itemPrice*count;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public String getFileAsCSV() {
       
        return invoice.getInvoiceNo() + "," + itemName + "," + itemPrice + "," + count;
    
    }
    
}
