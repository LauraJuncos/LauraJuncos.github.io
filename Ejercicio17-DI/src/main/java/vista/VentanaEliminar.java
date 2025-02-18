/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import DAO.CochesDAO;
import DAO.PersonasDAO;
import DTO.CochesDTO;
import DTO.PersonasDTO;
import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import servicio.CochesServicio;
import servicio.PersonasServicio;

/**
 *
 * @author Laura
 */
public class VentanaEliminar extends javax.swing.JFrame {

    private PersonasServicio personasServicio;
    private CochesServicio cochesServicio;

    /**
     * Creates new form VentanaEliminar
     */
    public VentanaEliminar(PersonasServicio personasServicio, CochesServicio cochesServicio) {
        if (personasServicio == null || cochesServicio == null) {
            throw new IllegalArgumentException("Servicios no pueden ser null");
        }
        this.personasServicio = personasServicio;
        this.cochesServicio = cochesServicio;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Evitar que la ventana se pueda redimensionar

        cargarPersonasEnTabla();
        cargarCochesEnTabla();

        jButton1.addActionListener(e -> eliminarPersona());
        jButton2.addActionListener(e -> eliminarCoche());

        configurarEncabezadoTabla();
        configurarListenersDeSeleccion();
    }

    private void cargarPersonasEnTabla() {
        String[] columnasPersonas = {"ID", "Nombre", "DNI"};
        DefaultTableModel modeloPersonas = new DefaultTableModel(columnasPersonas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };

        try {
            List<PersonasDTO> personas = personasServicio.obtenerTodasLasPersonas();
            for (PersonasDTO persona : personas) {
                modeloPersonas.addRow(new Object[]{
                    persona.getId(),
                    persona.getNombre(),
                    persona.getDni()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar personas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        jTable1.setModel(modeloPersonas);
        jTable1.removeColumn(jTable1.getColumnModel().getColumn(0)); // Ocultar la columna ID
    }

    private void cargarCochesEnTabla() {
        String[] columnasCoches = {"ID", "Matrícula", "Año", "Marca", "Modelo"};
        DefaultTableModel modeloCoches = new DefaultTableModel(columnasCoches, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };

        try {
            List<CochesDTO> coches = cochesServicio.obtenerTodosLosCoches();
            for (CochesDTO coche : coches) {
                modeloCoches.addRow(new Object[]{
                    coche.getId(),
                    coche.getMatricula(),
                    coche.getAnoMatriculacion(),
                    coche.getMarca(),
                    coche.getModelo()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar coches: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        jTable2.setModel(modeloCoches);
        jTable2.removeColumn(jTable2.getColumnModel().getColumn(0)); // Ocultar la columna ID
    }

    private void eliminarPersona() {
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una persona", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPersona = (int) jTable1.getModel().getValueAt(filaSeleccionada, 0);
        personasServicio.eliminarPersona(idPersona);
        JOptionPane.showMessageDialog(this, "Persona eliminada exitosamente");
        cargarPersonasEnTabla();
    }

    private void eliminarCoche() {
        int filaSeleccionada = jTable2.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un coche", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCoche = (int) jTable2.getModel().getValueAt(filaSeleccionada, 0);
        cochesServicio.eliminarCoche(idCoche);
        JOptionPane.showMessageDialog(this, "Coche eliminado exitosamente");
        cargarCochesEnTabla();
    }

    private void configurarEncabezadoTabla() {
        // Crear el renderer para el encabezado de la tabla con un JLabel personalizado
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel headerLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                headerLabel.setFont(new Font("SansSerif", Font.BOLD, 12)); // Establecer la fuente en negrita
                headerLabel.setBackground(new java.awt.Color(100, 149, 237)); // Azul clarito pastel
                headerLabel.setForeground(java.awt.Color.BLACK); // Texto en negro
                headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT); // Alinear el texto a la izquierda
                return headerLabel;
            }
        };

        // Aplicar el renderer a todas las columnas del encabezado de la tabla
        JTableHeader header = jTable1.getTableHeader();
        JTableHeader header1 = jTable2.getTableHeader();
        header.setDefaultRenderer(headerRenderer);
        header1.setDefaultRenderer(headerRenderer);
        jButton1.setBackground(new java.awt.Color(255, 140, 0)); // Fondo naranja fuerte (Dark Orange)
        jButton2.setBackground(new java.awt.Color(255, 140, 0)); // Fondo naranja fuerte (Dark Orange)
    }

    private void configurarListenersDeSeleccion() {
        // Listener para la selección de la tabla de personas
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (jTable1.getSelectedRow() != -1) {
                        // Si hay una fila seleccionada en la tabla de personas, desactivar la tabla de coches
                        jTable2.clearSelection();
                        jTable2.setEnabled(false);
                    } else {
                        // Si no hay ninguna fila seleccionada en la tabla de personas, habilitar la tabla de coches
                        jTable2.setEnabled(true);
                    }
                }
            }
        });

        // Listener para la selección de la tabla de coches
        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (jTable2.getSelectedRow() != -1) {
                        // Si hay una fila seleccionada en la tabla de coches, desactivar la tabla de personas
                        jTable1.clearSelection();
                        jTable1.setEnabled(false);
                    } else {
                        // Si no hay ninguna fila seleccionada en la tabla de coches, habilitar la tabla de personas
                        jTable1.setEnabled(true);
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton1.setText("Eliminar Persona");

        jButton2.setText("Eliminar Coche");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
