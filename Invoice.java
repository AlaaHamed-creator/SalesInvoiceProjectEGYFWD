/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Eng.Alaa Hamed
 */
public class Invoice {
    private int invoiceNo;
    private String invoiceDate;
    private String customerName;
    private ArrayList<InvoiceDetails> invoiceDetails;

    public Invoice() {}

    public Invoice(int invoiceNo, String invoiceDate, String customerName) {
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }

    public ArrayList<InvoiceDetails> getInvoiceDetails() {
        if(invoiceDetails==null)
        {
            invoiceDetails=new ArrayList<>();
        }
        return invoiceDetails;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int InvoiceNo) {
        this.invoiceNo = InvoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String InvoiceDate) {
        this.invoiceDate = InvoiceDate;
    }

    @Override
    public String toString() {
        return "Invoice{" + "InvoiceNo=" + invoiceNo + ", InvoiceDate=" + invoiceDate + ", customerName=" + customerName + '}';
    }
    public double getInvoiceTotal()
    {
        double total=0.0;
        for(InvoiceDetails line:getInvoiceDetails())
        {
            total+=line.getItemsTotalPrice();        
        }
        return total;
    }
    
     public String getFileAsCSV() {
        return invoiceNo + "," + invoiceDate + "," + customerName;
    }
}
