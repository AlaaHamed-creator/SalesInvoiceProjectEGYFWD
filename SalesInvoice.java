/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoice;

import View.SalesInvoiceForm;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Eng.Alaa Hamed
 */
public class SalesInvoice {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //SalesInvoiceForm.setDefaultLookAndFeelDecorated(true);
       // SalesInvoiceForm.show(true);
        SalesInvoiceForm frm=new SalesInvoiceForm();
        frm.setVisible(true);
         
    }
    
}
