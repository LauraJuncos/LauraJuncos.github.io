/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import DAO.CochesDAO;
import DAO.HistorialPropietariosDAO;
import DAO.PersonasDAO;
import DTO.CochesDTO;
import DTO.HistorialPropietariosDTO;
import DTO.PersonasDTO;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import servicio.CochesServicio;
import servicio.HistorialPropietariosServicio;
import servicio.PersonasServicio;

/**
 *
 * @author Laura
 */
public class Asociar extends javax.swing.JFrame {

    private CochesServicio cochesServicio;
    private PersonasServicio personasServicio;
    private HistorialPropietariosServicio historialPropietariosServicio;
    private RegistroVehiculos registroVehiculos;

    public Asociar(Connection conn, RegistroVehiculos registroVehiculos) {
        this.registroVehiculos = registroVehiculos;

        // Inicializar los servicios
        CochesDAO cochesDAO = new CochesDAO(conn);
        PersonasDAO personasDAO = new PersonasDAO(conn);
        cochesServicio = new CochesServicio(cochesDAO);
        personasServicio = new PersonasServicio(personasDAO);
        historialPropietariosServicio = new HistorialPropietariosServicio(new HistorialPropietariosDAO(conn));

        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar selección de filas (solo una fila a la vez)
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Cargar datos en las tablas
        cargarPersonasEnTabla();
        cargarCochesEnTabla();

        // Colores
        configurarEncabezadoTabla();
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

    private void asociarVehiculoAPersona() {
        int filaPersona = jTable1.getSelectedRow();
        int filaCoche = jTable2.getSelectedRow();

        if (filaPersona == -1 || filaCoche == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una persona y un vehículo", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Obtener los IDs ocultos de las tablas
            int idPersona = (int) jTable1.getModel().getValueAt(filaPersona, 0);
            int idCoche = (int) jTable2.getModel().getValueAt(filaCoche, 0);

            // Verificar si el vehículo ya está asociado a una persona
            HistorialPropietariosDTO historial = historialPropietariosServicio.obtenerHistorialPorCoche(idCoche);
            if (historial != null && historial.getFechaFin() == null) {
                JOptionPane.showMessageDialog(this, "El vehículo ya está asociado a otra persona", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Llamar al servicio para asociar el vehículo a la persona
            historialPropietariosServicio.asociarVehiculoAPersona(idPersona, idCoche);
            JOptionPane.showMessageDialog(this, "Vehículo asociado exitosamente");

            // Actualizar la ventana de RegistroVehiculos
            if (registroVehiculos != null) {
                registroVehiculos.cargarDatosPaginados(registroVehiculos.getPaginaActual());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al asociar vehículo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Asociar Vehículo");

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

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Asociar Vehículo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        asociarVehiculoAPersona();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
