
package Control;

import Model.Invoice;
import Model.InvoiceDetails;
import Model.InvoicesTableModel;
import Model.RowsTableModel;
import View.InvoiceDetailsDialog;
import View.InvoiceDialog;
import View.SalesInvoiceForm;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Eng.Alaa Hamed
 */
public class MainController implements ActionListener,ListSelectionListener{

    private SalesInvoiceForm _frm;
    private InvoiceDialog invoiceDialog;
    private InvoiceDetailsDialog invoiceDetailsDialog;
    public MainController(SalesInvoiceForm frm)
    {
        this._frm=frm;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       String actionCommand=e.getActionCommand();
       switch (actionCommand){
           case "Load File":
               loadFile();
                   break;
           case "Save File":
               saveFile();
                   break;
           case "Create New Invoice":
               createNewInvoice();
                   break;
           case "Delete Invoice":
               deleteInvoice();
                   break;
           case "Create New Item":
               createNewItem();
                   break;
           case "Delete Item":
               deleteItem();
                   break;
           case "createInvoiceCancel":
                InvoiceCancel();
                break;
            case "createInvoiceOK":
                NewInvoiceOK();
                break;
            case "createLineOK":
                InvoiceDetailsOK();
                break;
            case "createLineCancel":
                InvoiceDetailsCancel();
                break;
    }
    }

     @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex=_frm.getTableInvoices().getSelectedRow();
        if(selectedIndex !=-1){
        Invoice selectedInvoice=_frm.getInvoices().get(selectedIndex);
        _frm.getLblInvoiceNo().setText(" "+ selectedInvoice.getInvoiceNo());
        _frm.getLblDate().setText(" "+ selectedInvoice.getInvoiceDate());
        _frm.getLblCustomer().setText(" "+ selectedInvoice.getCustomerName());
        _frm.getLblTotal().setText(" "+ selectedInvoice.getInvoiceTotal());
        
        RowsTableModel rowsTableModel=new RowsTableModel(selectedInvoice.getInvoiceDetails());
        _frm.getTableItems().setModel(rowsTableModel);
        rowsTableModel.fireTableDataChanged();
    }
    }
    private void loadFile() {
      
    JFileChooser file=new JFileChooser(); 
   try{
       
    int result=file.showOpenDialog(_frm);
    if(result==JFileChooser.APPROVE_OPTION)
    {
        File invoiceFile=file.getSelectedFile();
        Path invoicePath=Paths.get(invoiceFile.getAbsolutePath());
      
       ArrayList<String> invoiceRows = (ArrayList<String>) Files.readAllLines(invoicePath);
     //   List <String> invoiceRows=Files.readAllLines(invoicePath);
        ArrayList<Invoice> invoicesArray=new ArrayList<>();
        System.out.println("Invoices have been read");
        
        for( String invoiceRow: invoiceRows)
        {
            try{
        
            String[] rowparts=invoiceRow.split(",");
            int invoiceNo=Integer.parseInt(rowparts[0]); 
            String invoiceDate=rowparts[1];
            String customerName=rowparts[2];
            Invoice invoice=new Invoice(invoiceNo, invoiceDate, customerName);
            invoicesArray.add(invoice);
            }
            catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(_frm, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
        }
        result=file.showOpenDialog(_frm);
        
        if(result==JFileChooser.APPROVE_OPTION)
        {
            File invoiceRow=file.getSelectedFile();
            Path rowPath=Paths.get(invoiceRow.getAbsolutePath());
            ArrayList<String> rows = (ArrayList<String>) Files.readAllLines(rowPath);
             
              for( String invoiceRowDetails: rows)
              {
            
              try{
            String[] invoicerowparts=invoiceRowDetails.split(",");
            int invoiceNo=Integer.parseInt(invoicerowparts[0]); 
            String itemName=invoicerowparts[1];
            double itemPrice=Double.parseDouble(invoicerowparts[2]);
            int quantity=Integer.parseInt(invoicerowparts[3]);
            Invoice _invoice=null;
            for(Invoice invoice:invoicesArray)
            {
                if(invoice.getInvoiceNo()==invoiceNo)
                {
                    _invoice=invoice;
                    break;
                }
            }
            InvoiceDetails invoiceDetails=new InvoiceDetails(itemName,itemPrice,quantity,_invoice);
            _invoice.getInvoiceDetails().add(invoiceDetails);
              } 
              catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(_frm, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);}
              }}
              
          _frm.setInvoices(invoicesArray);
          InvoicesTableModel invoicesTableModel=new InvoicesTableModel(invoicesArray);
          _frm.setInvoicesTableModel(invoicesTableModel);
          _frm.getTableInvoices().setModel(invoicesTableModel);
          _frm.getInvoicesTableModel().fireTableDataChanged();
      
     
    } } 
   catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(_frm, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE); }
   } //end of loadfile method
    
    
    private void saveFile() {
    ArrayList<Invoice> invoices = _frm.getInvoices();
        String headers = "";
        String lines = "";
        for (Invoice invoice : invoices) {
            String invFileCSV = invoice.getFileAsCSV();
            headers += invFileCSV;
            headers += "\n";

            for (InvoiceDetails invDetails : invoice.getInvoiceDetails()) {
                String lineFileCSV = invDetails.getFileAsCSV();
                lines += lineFileCSV;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(_frm);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(_frm);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }

    }

    private void createNewInvoice() {
        invoiceDialog = new InvoiceDialog(_frm);
        invoiceDialog.setVisible(true);
    }

    private void deleteInvoice() {
       int selectRow= _frm.getTableInvoices().getSelectedRow();
       
       if(selectRow !=-1)
       {
           _frm.getInvoices().remove(selectRow);
           _frm.getInvoicesTableModel().fireTableDataChanged();
       }
    }

    private void createNewItem() {
        invoiceDetailsDialog = new InvoiceDetailsDialog(_frm);
        invoiceDetailsDialog.setVisible(true);    }

    private void deleteItem() {
         int selectInvoice= _frm.getTableInvoices().getSelectedRow();
         int selectRow= _frm.getTableItems().getSelectedRow();
         if(selectInvoice !=-1 &&selectRow !=-1)
         {
            Invoice inv= _frm.getInvoices().get(selectInvoice);
            inv.getInvoiceDetails().remove(selectRow);
            RowsTableModel rowsTable= new RowsTableModel(inv.getInvoiceDetails());
            _frm.getTableItems().setModel(rowsTable);
            rowsTable.fireTableDataChanged();   
         }
         
    }

    private void InvoiceCancel() {
       invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void NewInvoiceOK() {
 String date = invoiceDialog.getInvDateField().getText();
        String customer = invoiceDialog.getCustNameField().getText();
        int num = _frm.getNextInvoiceNum();
        try {
            String[] dateParts = date.split("-");  
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(_frm, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(_frm, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Invoice invoice = new Invoice(num, date, customer);
                    _frm.getInvoices().add(invoice);
                    _frm.getInvoicesTableModel().fireTableDataChanged();
                    invoiceDialog.setVisible(false);
                    invoiceDialog.dispose();
                    invoiceDialog = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(_frm, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void InvoiceDetailsOK() {
String item = invoiceDetailsDialog.getItemNameField().getText();
        String countStr = invoiceDetailsDialog.getItemCountField().getText();
        String priceStr = invoiceDetailsDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = _frm.getTableInvoices().getSelectedRow();
        if (selectedInvoice != -1) {
            Invoice invoice = _frm.getInvoices().get(selectedInvoice);
            InvoiceDetails invoiceDetails = new InvoiceDetails(item, price, count, invoice);
            invoice.getInvoiceDetails().add(invoiceDetails);
            RowsTableModel rowsTableModel = (RowsTableModel) _frm.getTableItems().getModel();
            //linesTableModel.getLines().add(line);
            rowsTableModel.fireTableDataChanged();
            _frm.getInvoicesTableModel().fireTableDataChanged();
        }
        invoiceDetailsDialog.setVisible(false);
        invoiceDetailsDialog.dispose();
        invoiceDetailsDialog = null;
    }

    private void InvoiceDetailsCancel() {
        invoiceDetailsDialog.setVisible(false);
        invoiceDetailsDialog.dispose();
        invoiceDetailsDialog = null;
    }

   
}