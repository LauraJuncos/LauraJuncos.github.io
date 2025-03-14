/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package P5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Laura
 */
public class CreacióndeInformes extends javax.swing.JFrame {

    private ButtonGroup checkBoxGroup;
    private ButtonGroup checkBoxGroup1;

    /**
     * Creates new form CreacióndeInformes
     */
    public CreacióndeInformes() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Agrupar los checkboxes para que solo uno pueda estar seleccionado a la vez
        checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(jCheckBox1);
        checkBoxGroup.add(jCheckBox2);
        checkBoxGroup.add(jCheckBox3);

        // Agrupar los checkboxes para que solo uno pueda estar seleccionado a la vez
        checkBoxGroup1 = new ButtonGroup();
        checkBoxGroup1.add(jCheckBox4);
        checkBoxGroup1.add(jCheckBox5);
        checkBoxGroup1.add(jCheckBox6);
        checkBoxGroup1.add(jCheckBox7);
    }

    private void generarInforme() {
        String formato = "";
        String reporte = "";

        // Determinar el formato de exportación
        if (jCheckBox1.isSelected()) {
            formato = "pdf";
        } else if (jCheckBox2.isSelected()) {
            formato = "html";
        } else if (jCheckBox3.isSelected()) {
            formato = "xml";
        }

        // Determinar el informe a generar
        if (jCheckBox4.isSelected()) {
            reporte = "./src/informes/report1.jasper";
        } else if (jCheckBox5.isSelected()) {
            reporte = "./src/informes/report2.jasper";
        } else if (jCheckBox6.isSelected()) {
            reporte = "./src/informes/report3.jasper";
        } else if (jCheckBox7.isSelected()) {
            reporte = "./src/informes/report4.jasper";
        }

        if (formato.isEmpty() || reporte.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un informe y un formato.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fpdual",
                    "laura",
                    "1234")) {

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reporte);
                JasperPrint miInforme = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conexion);
                JasperViewer.viewReport(miInforme, false);
                switch (formato) {
                    case "pdf":
                        JasperExportManager.exportReportToPdfFile(miInforme, reporte.replace(".jasper", ".pdf"));
                        JOptionPane.showMessageDialog(this, "Informe PDF generado correctamente.");
                        break;
                    case "html":
                        JasperExportManager.exportReportToHtmlFile(miInforme, reporte.replace(".jasper", ".html"));
                        JOptionPane.showMessageDialog(this, "Informe HTML generado correctamente.");
                        break;
                    case "xml":
                        JasperExportManager.exportReportToXmlFile(miInforme, reporte.replace(".jasper", ".xml"), false);
                        JOptionPane.showMessageDialog(this, "Informe XML generado correctamente.");
                        break;
                }
                checkBoxGroup.clearSelection();
                checkBoxGroup1.clearSelection();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar el informe: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void verInforme() {
        String reporte = "";

        // Validar que se haya seleccionado un checkbox de la izquierda
        if (!jCheckBox4.isSelected() && !jCheckBox5.isSelected() && !jCheckBox6.isSelected() && !jCheckBox7.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un tipo de informe antes de verlo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Determinar el informe a mostrar según el checkbox seleccionado
        if (jCheckBox4.isSelected()) {
            reporte = "./src/informes/report1.jasper";
        } else if (jCheckBox5.isSelected()) {
            reporte = "./src/informes/report2.jasper";
        } else if (jCheckBox6.isSelected()) {
            reporte = "./src/informes/report3.jasper";
        } else if (jCheckBox7.isSelected()) {
            reporte = "./src/informes/report4.jasper";
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fpdual",
                    "laura",
                    "1234")) {

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reporte);
                JasperPrint miInforme = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conexion);

                // Mostrar el informe en el visor sin exportarlo
                JasperViewer.viewReport(miInforme, false);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar el informe: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void verGraficoParticipantes() {
        String reporte = "./src/informes/graficoParticipando.jasper"; // Ajusta la ruta si es necesario

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conexión a la base de datos
            try (Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fpdual", // Ajusta tu base de datos
                    "laura", // Usuario
                    "1234")) { // Contraseña

                // Cargar el informe compilado
                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reporte);

                // Llenar el informe con la conexión a la base de datos
                JasperPrint miInforme = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conexion);

                // Mostrar el informe en el visor JasperViewer
                JasperViewer.viewReport(miInforme, false);

            }

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró el driver JDBC.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar el gráfico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("GENERAR INFORME");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText(".pfp");

        jCheckBox2.setText(".html");

        jCheckBox3.setText(".xml");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("INFORMES");

        jCheckBox4.setText("Alumnos que participan");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        jCheckBox5.setText("Alumnos que NO participan");

        jCheckBox6.setText("Alumnos participando por ciclo");

        jCheckBox7.setText("Alumnos que NO participan por ciclo");

        jButton2.setText("VER INFORME");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("VER GRÁFICO DE PARTICIPANTES");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBox6, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                .addGap(72, 72, 72)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox7)
                            .addComponent(jCheckBox5)
                            .addComponent(jCheckBox4))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox4)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            generarInforme();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar el informe: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed

    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        verInforme();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        verGraficoParticipantes();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(CreacióndeInformes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreacióndeInformes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreacióndeInformes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreacióndeInformes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreacióndeInformes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
