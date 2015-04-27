/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derbytrial;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.Connection;



public class PurchaseOrderEntry extends javax.swing.JFrame {

private Connection conn;

    public PurchaseOrderEntry() {
        conn = MusicStoreLauncher.conn;
        
        initComponents();
    }

    // The code below sets up a check to make sure duplicate windows don't open.
    private static boolean isOpen = false;

    public static boolean getIsOpen() {
        return isOpen;
    }

    public static void setIsOpen(boolean set) {
        isOpen = set;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        POLabel = new javax.swing.JLabel();
        VendorLabel = new javax.swing.JLabel();
        VendorIDField = new javax.swing.JTextField();
        DateOrderedLabel = new javax.swing.JLabel();
        DateOrderedEntry = new javax.swing.JTextField();
        DateReceiveLabel = new javax.swing.JLabel();
        DateReceiveEntry = new javax.swing.JTextField();
        SubmitInfoButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        PurchaseTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        AddRowButton = new javax.swing.JButton();
        ClearRowsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        POLabel.setText("Purchase Order Entry");

        VendorLabel.setText("Vendor ID:");

        DateOrderedLabel.setText("Date Ordered:");

        DateReceiveLabel.setText("Date To Receive: ");

        SubmitInfoButton.setText("Submit");
        SubmitInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitInfoButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        PurchaseTable.setModel(new LineTableModel("Stock ID", "Amount"));
        jScrollPane1.setViewportView(PurchaseTable);

        jLabel1.setText("Stock Items (Stock ID, Amount)");

        AddRowButton.setText("Add Row");
        AddRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRowButtonActionPerformed(evt);
            }
        });

        ClearRowsButton.setText("Clear Rows");
        ClearRowsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearRowsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(POLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CancelButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SubmitInfoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ClearRowsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddRowButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(DateReceiveLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(DateReceiveEntry))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(DateOrderedLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(DateOrderedEntry))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(VendorLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(VendorIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(POLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VendorLabel)
                    .addComponent(VendorIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DateOrderedLabel)
                    .addComponent(DateOrderedEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DateReceiveEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DateReceiveLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SubmitInfoButton)
                            .addComponent(CancelButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AddRowButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ClearRowsButton)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SubmitInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitInfoButtonActionPerformed

        
        if (PurchaseTable.isEditing()){
            PurchaseTable.getCellEditor().stopCellEditing();
        }

        String addOrderQuery = "insert into PURCHASE_ORDER(VENDOR_ID, ORDER_DATE, DATE_TO_RECEIVE) values(?,?,?)";
        String addLineQuery = "insert into PURCHASE_ORDER_LINE values(?,?,?,?, FALSE)";

        try (PreparedStatement salePrep = conn.prepareStatement(addOrderQuery, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement solPrep = conn.prepareStatement(addLineQuery)) {
            int vendorID = Integer.parseInt(VendorIDField.getText());
            salePrep.setInt(1, vendorID);
            salePrep.setDate(2, Date.valueOf(DateOrderedEntry.getText()));
            salePrep.setDate(3, Date.valueOf(DateReceiveEntry.getText()));
            salePrep.execute();

            ResultSet rs = salePrep.getGeneratedKeys();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Unexpected error: unable to retreive purchase key.");
                try {
                    conn.rollback();
                } catch (SQLException s) {
                }

                return;
            }

            int purchaseID = rs.getInt(1);
            BigDecimal total = new BigDecimal(0);
            for (int i = 0; i < PurchaseTable.getRowCount(); i++) {
                
                int stockID = Integer.parseInt(PurchaseTable.getValueAt(i, 0).toString());
                int amount = Integer.parseInt(PurchaseTable.getValueAt(i, 1).toString());

                Statement tempStmt = conn.createStatement();
                Statement addStatement = conn.createStatement();
                ResultSet tempRs = tempStmt.executeQuery("select VENDOR_COST from STOCK where STOCK_ID = " + stockID);

                if (!tempRs.next()) {
                    JOptionPane.showMessageDialog(this, "Stock ID not found!");
                    try {
                        conn.rollback();
                    } catch (SQLException s) {
                    }
                    return;
                }
                BigDecimal price = tempRs.getBigDecimal(1);
                BigDecimal subTotal = price.multiply(new BigDecimal(amount));
                total = total.add(subTotal);

                solPrep.setInt(1, purchaseID);
                solPrep.setInt(2, stockID);
                solPrep.setInt(3, amount);
                solPrep.setBigDecimal(4, price);

                addStatement.execute("UPDATE STOCK SET ON_ORDER = (ON_ORDER + " + amount + ") WHERE STOCK_ID = " + stockID);
                
                solPrep.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Purchase #" + purchaseID + " created for vendor #" + vendorID + "\nTotal price: " + total);
            conn.commit();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Invalid insert operation: \nPlease check that your ID values are correct.");
            try {
                conn.rollback();
            } catch (SQLException s) {
            }
            System.err.println(e);
        } catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(this, "Illegal values entered: \nPlease make sure only numbers are used for \nnumerical entries.");
            try {
                conn.rollback();
            } catch (SQLException s) {
            }
        } catch (IllegalArgumentException i) {
            //illegal date format
            JOptionPane.showMessageDialog(this, "Illegal Date: \nPlease enter date in yyyy-mm-dd format.");
            try {
                conn.rollback();
            } catch (SQLException s) {
            }
        }
    }//GEN-LAST:event_SubmitInfoButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        isOpen = false;
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        isOpen = false;
    }//GEN-LAST:event_formWindowClosing

    private void AddRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRowButtonActionPerformed
        // TODO add your handling code here:
        LineTableModel model = (LineTableModel) PurchaseTable.getModel();
        model.addRow();
    }//GEN-LAST:event_AddRowButtonActionPerformed

    private void ClearRowsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearRowsButtonActionPerformed
        // TODO add your handling code here:
        LineTableModel model = (LineTableModel) PurchaseTable.getModel();
        model.clearRows();
    }//GEN-LAST:event_ClearRowsButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrderEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrderEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrderEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrderEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PurchaseOrderEntry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddRowButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton ClearRowsButton;
    private javax.swing.JTextField DateOrderedEntry;
    private javax.swing.JLabel DateOrderedLabel;
    private javax.swing.JTextField DateReceiveEntry;
    private javax.swing.JLabel DateReceiveLabel;
    private javax.swing.JLabel POLabel;
    private javax.swing.JTable PurchaseTable;
    private javax.swing.JButton SubmitInfoButton;
    private javax.swing.JTextField VendorIDField;
    private javax.swing.JLabel VendorLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
