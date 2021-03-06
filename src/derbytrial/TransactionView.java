/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derbytrial;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Steven
 */
public class TransactionView extends javax.swing.JFrame {

    Connection conn;
    String currentTable = null;
    Integer currentID = null;
    String nullQuery = "select * from #table where 1 = 2";

    public TransactionView() {
        conn = MusicStoreLauncher.conn;
        initComponents();
    }

    // The code below sets up a check to make sure duplicate windows don't open.
    private static boolean isOpen = false;
    // This makes the Purchase Order fields invisible

    public static boolean getIsOpen() {
        return isOpen;
    }

    public static void setIsOpen(boolean set) {
        isOpen = set;
    }

    // This class turns on/off the labels for Sales info
    private void setSalesVisible(boolean set) {
        PaymentTypeDisplayLabel.setVisible(set);
        PaymentTypeLabel.setVisible(set);
    }

    //This class turns on/off the labels for Purchasing info
    private void setPurchaseVisible(boolean set) {
        ReceiveDateDisplayLabel.setVisible(set);
        ReceiveDateLabel.setVisible(set);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TransactionViewLabel = new javax.swing.JLabel();
        OrderTypeLabel = new javax.swing.JLabel();
        OrderTypeList = new javax.swing.JComboBox();
        OrderIDLabel = new javax.swing.JLabel();
        OrderIDField = new javax.swing.JTextField();
        IDSubmitButton = new javax.swing.JButton();
        NameDisplayLabel = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        OrderDateDisplayLabel = new javax.swing.JLabel();
        OrderDateLabel = new javax.swing.JLabel();
        ReceiveDateDisplayLabel = new javax.swing.JLabel();
        ReceiveDateLabel = new javax.swing.JLabel();
        PaymentTypeDisplayLabel = new javax.swing.JLabel();
        PaymentTypeLabel = new javax.swing.JLabel();
        LineItemScrollPane = new javax.swing.JScrollPane();
        LineItems = new javax.swing.JTable();
        DeleteButton = new javax.swing.JButton();
        ExitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        TransactionViewLabel.setText("Transaction View");

        OrderTypeLabel.setText("Type of Order:");

        OrderTypeList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sales", "Purchase" }));
        OrderTypeList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                OrderTypeListItemStateChanged(evt);
            }
        });

        OrderIDLabel.setText("Order ID:");

        IDSubmitButton.setText("Submit");
        IDSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDSubmitButtonActionPerformed(evt);
            }
        });

        NameDisplayLabel.setText("Customer/Vendor:");

        NameLabel.setText("-");

        OrderDateDisplayLabel.setText("Date of Order:");

        OrderDateLabel.setText("-");

        ReceiveDateDisplayLabel.setText("Date to Receive: ");

        ReceiveDateLabel.setText("-");

        PaymentTypeDisplayLabel.setText("Payment Method:");

        PaymentTypeLabel.setText("-");

        LineItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        LineItemScrollPane.setViewportView(LineItems);

        DeleteButton.setText("Delete Order Record");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        ExitButton.setText("Exit");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Total Price:");

        priceLabel.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LineItemScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(TransactionViewLabel)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(OrderTypeLabel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(OrderTypeList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(OrderIDLabel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(OrderIDField)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(NameDisplayLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(NameLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(OrderDateDisplayLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(OrderDateLabel)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IDSubmitButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ReceiveDateDisplayLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ReceiveDateLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(PaymentTypeDisplayLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PaymentTypeLabel)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DeleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ExitButton)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(priceLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TransactionViewLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OrderTypeLabel)
                    .addComponent(OrderTypeList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OrderIDLabel)
                    .addComponent(OrderIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDSubmitButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NameDisplayLabel)
                    .addComponent(NameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OrderDateDisplayLabel)
                    .addComponent(OrderDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReceiveDateDisplayLabel)
                    .addComponent(ReceiveDateLabel))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PaymentTypeDisplayLabel)
                    .addComponent(PaymentTypeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LineItemScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DeleteButton)
                    .addComponent(ExitButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_ExitButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        isOpen = false;
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        isOpen = false;
    }//GEN-LAST:event_formWindowClosing

    private void clearForm() {
        //resets the form

        if (OrderTypeList.getSelectedIndex() == 0) { //Sales selected

            //clear JTable and set column names to sale_order_line
            try (Statement stmt = conn.createStatement()) {
                PreparedStatement temp = conn.prepareStatement("select * from PURCHASE_ORDER_LINE where 1 = 2");
                LineItems.setModel(new JDBCTableModel(currentTable, temp));

            } catch (SQLException s) {
                System.err.println(s);
            }

        } else { //purchases selected
            //clear JTable and set column names to purchase_order_line
            try (Statement stmt = conn.createStatement()) {
                PreparedStatement temp = conn.prepareStatement("select * from SALE_ORDER_LINE where 1 = 2");
                LineItems.setModel(new JDBCTableModel(currentTable, temp));

            } catch (SQLException s) {
                System.err.println(s);
            }
        }

        //clear labels
        NameLabel.setText("-");
        OrderDateLabel.setText("-");
        PaymentTypeLabel.setText("-");
        priceLabel.setText("-");
        ReceiveDateLabel.setText("-");

        OrderIDField.setText("");

    }
    private void IDSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDSubmitButtonActionPerformed
        currentID = null;
        currentTable = null;

        String solQuery = "select * from SALE_ORDER_LINE where SALE_ID = ?";
        String polQuery = "select * from PURCHASE_ORDER_LINE where PO_ID = ?";
        try (Statement stmt = conn.createStatement();
                PreparedStatement solPrep = conn.prepareStatement(solQuery);
                PreparedStatement polPrep = conn.prepareStatement(polQuery)) {
            if (OrderTypeList.getSelectedIndex() == 0) { //Sales selected
                int saleID = Integer.parseInt(OrderIDField.getText());
                ResultSet rs = stmt.executeQuery("select * from SALE where SALE_ID = " + saleID);
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Sale #" + saleID + " could not be found.");
                    return;
                }
                NameLabel.setText(rs.getString(2));
                OrderDateLabel.setText(rs.getDate(3).toString());
                PaymentTypeLabel.setText(rs.getString(4));

                ResultSet rs2 = stmt.executeQuery("select sum(COST) COST_SUM from SALE_ORDER_LINE where SALE_ID = " + saleID);
                BigDecimal total = new BigDecimal(0);
                total.setScale(2);

                if (rs2.next()) {
                    total = rs2.getBigDecimal("COST_SUM");
                }

                priceLabel.setText("$" + total.toString());

                solPrep.setInt(1, saleID);

                LineItems.setModel(new JDBCTableModel("SALE_ORDER_LINE", solPrep));
                currentID = saleID;
                currentTable = "SALE";

            } else { //Purchases selected
                int purchaseID = Integer.parseInt(OrderIDField.getText());
                ResultSet rs = stmt.executeQuery("select * from PURCHASE_ORDER where PO_ID = " + purchaseID);
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Purchase Order #" + purchaseID + " could not be found.");
                    return;
                }
                NameLabel.setText(rs.getString(2));
                OrderDateLabel.setText(rs.getDate(3).toString());
                ReceiveDateLabel.setText(rs.getDate(4).toString());

                ResultSet rs2 = stmt.executeQuery("select sum(COST) COST_SUM from PURCHASE_ORDER_LINE where PO_ID = " + purchaseID);
                BigDecimal total = new BigDecimal(0);
                total.setScale(2);

                if (rs2.next()) {
                    total = rs2.getBigDecimal("COST_SUM");
                }

                priceLabel.setText("$" + total.toString());

                polPrep.setInt(1, purchaseID);

                LineItems.setModel(new JDBCTableModel("PURCHASE_ORDER_LINE", polPrep));

                currentID = purchaseID;
                currentTable = "PURCHASE_ORDER";
            }
        } catch (SQLException s) {
            System.err.println(s);
        } catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(this, "Error: ID can only be an integer.");
        }
    }//GEN-LAST:event_IDSubmitButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        if (currentTable != null && currentID != null) {
            try (Statement stmt = conn.createStatement()) {
                if (currentTable.equals("SALE")) {
                    stmt.execute("UPDATE STOCK S SET S.AMOUNT = (S.AMOUNT + "
                            + "(SELECT O.AMOUNT "
                            + "FROM SALE_ORDER_LINE O "
                            + "WHERE S.STOCK_ID = O.STOCK_ID AND O.SALE_ID = " + currentID + "))"
                            + " WHERE S.STOCK_ID IN ("
                            + "SELECT O.STOCK_ID "
                            + "FROM SALE_ORDER_LINE O "
                            + "WHERE SALE_ID = " + currentID + ")");
                    stmt.executeUpdate("delete from SALE_ORDER_LINE where SALE_ID = " + currentID);
                    stmt.executeUpdate("delete from SALE where SALE_ID = " + currentID);

                    PreparedStatement temp = conn.prepareStatement("select * from SALE_ORDER_LINE where 1 = 2");

                    LineItems.setModel(new JDBCTableModel(currentTable, temp));

                    NameLabel.setText("-");
                    OrderDateLabel.setText("-");
                    PaymentTypeLabel.setText("-");
                    priceLabel.setText("-");

                    JOptionPane.showMessageDialog(this, "Sale #" + currentID + " deleted.");
                    currentTable = null;
                    currentID = null;
                    conn.commit();
                } else {
                    stmt.execute("UPDATE STOCK S SET S.AMOUNT = (S.AMOUNT - "
                            + "(SELECT P.AMOUNT "
                            + "FROM PURCHASE_ORDER_LINE P "
                            + "WHERE S.STOCK_ID = P.STOCK_ID AND P.PO_ID = " + currentID + "))"
                            + " WHERE S.STOCK_ID IN ("
                            + "SELECT P.STOCK_ID "
                            + "FROM PURCHASE_ORDER_LINE P "
                            + "WHERE P.PO_ID = " + currentID + " AND P.RECEIVED = TRUE)");
                    stmt.execute("UPDATE STOCK S SET S.ON_ORDER = (S.ON_ORDER - "
                            + "(SELECT P.AMOUNT "
                            + "FROM PURCHASE_ORDER_LINE P "
                            + "WHERE S.STOCK_ID = P.STOCK_ID AND P.PO_ID = " + currentID + "))"
                            + " WHERE S.STOCK_ID IN ("
                            + "SELECT P.STOCK_ID "
                            + "FROM PURCHASE_ORDER_LINE P "
                            + "WHERE P.PO_ID = " + currentID + " AND P.RECEIVED = FALSE)");
                    stmt.executeUpdate("delete from PURCHASE_ORDER_LINE where PO_ID = " + currentID);
                    stmt.executeUpdate("delete from PURCHASE_ORDER where PO_ID = " + currentID);

                    PreparedStatement temp = conn.prepareStatement("select * from PURCHASE_ORDER_LINE where 1 = 2");
                    LineItems.setModel(new JDBCTableModel(currentTable, temp));

                    NameLabel.setText("-");
                    OrderDateLabel.setText("-");
                    ReceiveDateLabel.setText("-");
                    priceLabel.setText("-");

                    JOptionPane.showMessageDialog(this, "Purchase Order #" + currentID + " deleted.");
                    currentTable = null;
                    currentID = null;
                    conn.commit();
                }
            } catch (SQLException s) {
                System.err.println(s);
                try {
                    conn.rollback();
                } catch (SQLException q) {
                }
            }
        }
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        setPurchaseVisible(false);
    }//GEN-LAST:event_formWindowActivated

    private void OrderTypeListItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_OrderTypeListItemStateChanged
        clearForm();
        switch (evt.getItem().toString()) {
            case "Sales":
                setSalesVisible(true);
                setPurchaseVisible(false);
                break;
            case "Purchase":
                setPurchaseVisible(true);
                setSalesVisible(false);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_OrderTypeListItemStateChanged

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
            java.util.logging.Logger.getLogger(TransactionView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransactionView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransactionView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransactionView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransactionView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteButton;
    private javax.swing.JButton ExitButton;
    private javax.swing.JButton IDSubmitButton;
    private javax.swing.JScrollPane LineItemScrollPane;
    private javax.swing.JTable LineItems;
    private javax.swing.JLabel NameDisplayLabel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel OrderDateDisplayLabel;
    private javax.swing.JLabel OrderDateLabel;
    private javax.swing.JTextField OrderIDField;
    private javax.swing.JLabel OrderIDLabel;
    private javax.swing.JLabel OrderTypeLabel;
    private javax.swing.JComboBox OrderTypeList;
    private javax.swing.JLabel PaymentTypeDisplayLabel;
    private javax.swing.JLabel PaymentTypeLabel;
    private javax.swing.JLabel ReceiveDateDisplayLabel;
    private javax.swing.JLabel ReceiveDateLabel;
    private javax.swing.JLabel TransactionViewLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel priceLabel;
    // End of variables declaration//GEN-END:variables
}
