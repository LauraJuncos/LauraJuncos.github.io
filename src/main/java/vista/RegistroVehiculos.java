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
import DTO.PaginacionDTO;
import DTO.PersonasDTO;
import Persistencia.Conectar;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servicio.CochesServicio;
import servicio.HistorialPropietariosServicio;
import servicio.PersonasServicio;

/**
 *
 * @author Laura
 */
public class RegistroVehiculos extends javax.swing.JFrame {

    private Conectar conexion;
    private Connection conn;
    private CochesServicio cochesServicio;
    private PersonasServicio personasServicio;
    private HistorialPropietariosServicio historialPropietariosServicio;
    private CochesDAO cochesDAO;
    private HistorialPropietariosDAO historialPropietariosDAO;

    private int paginaActual = 1;
    private boolean filtrosVisibles = false;

    public RegistroVehiculos() {
        initComponents();
        conexion = new Conectar();
        conn = conexion.getConnection();

        // Inicialización de DAOs y servicios
        cochesDAO = new CochesDAO(conn); // Usa la variable de instancia
        PersonasDAO personasDAO = new PersonasDAO(conn);
        historialPropietariosDAO = new HistorialPropietariosDAO(conn);

        cochesServicio = new CochesServicio(cochesDAO);
        personasServicio = new PersonasServicio(personasDAO);
        historialPropietariosServicio = new HistorialPropietariosServicio(historialPropietariosDAO);

        cargarDatosPaginados(paginaActual);
        actualizarLabelPagina();

        // Asignar acciones a botones
        bSiguiente.addActionListener(e -> siguientePagina());
        bAnterior.addActionListener(e -> paginaAnterior());

        // Cargar marcas al iniciar
        cargarMarcas();

        // Agrupar botones de sexo
        ButtonGroup grupoSexo = new ButtonGroup();
        grupoSexo.add(jRadioButton1); // Asumimos que jRadioButton1 es "Hombre"
        grupoSexo.add(jRadioButton2); // Asumimos que jRadioButton2 es "Mujer"

        // Configuración inicial de filtros
        PanelFiltros.setVisible(false);
        jLabel3.setText("\u25BC Filtros avanzados");
        setLocationRelativeTo(null); // Centrar la ventana
    }

    // Método para cargar datos paginados en la tabla
    private void cargarDatosPaginados(int pagina) {
        int registrosPorPagina = 10;
        try {
            // Obtener datos paginados
            PaginacionDTO<CochesDTO> cochesPaginados = cochesServicio.obtenerCochesPaginados(pagina, registrosPorPagina);
            List<PersonasDTO> personas = personasServicio.buscarPorNombre(""); // Filtrar o modificar según sea necesario

            String[] columnas = {"Nombre", "Marca", "Modelo", "Año de matriculación", "Nº de Vehículos"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            // Unir datos de coches y personas para llenar la tabla
            for (CochesDTO coche : cochesPaginados.getDatos()) {
                for (PersonasDTO persona : personas) {
                    if (coche.getId() == persona.getId()) { // Verifica si los IDs coinciden
                        int numVehiculos = 0;
                        try {
                            numVehiculos = historialPropietariosDAO.contarVehiculosPorPersona(persona.getId()); // Obtener el número de vehículos
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        modelo.addRow(new Object[]{
                            persona.getNombre(),
                            coche.getMarca(),
                            coche.getModelo(),
                            coche.getAnoMatriculacion(),
                            numVehiculos
                        });
                    }
                }
            }

            jTable1.setModel(modelo);
            actualizarLabelPagina();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos paginados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para cargar la siguiente página
    private void siguientePagina() {
        paginaActual++;
        cargarDatosPaginados(paginaActual);
    }

    // Método para cargar la página anterior
    private void paginaAnterior() {
        if (paginaActual > 1) {
            paginaActual--;
            cargarDatosPaginados(paginaActual);
        }
    }

    // Actualizar etiqueta de página actual
    private void actualizarLabelPagina() {
        jLabel7.setText("Página: " + paginaActual);
    }

    // Alternar visibilidad de filtros avanzados
    private void mostrarFiltros() {
        filtrosVisibles = !filtrosVisibles;
        PanelFiltros.setVisible(filtrosVisibles);
        jLabel3.setText(filtrosVisibles ? "\u25B2 Filtros avanzados" : "\u25BC Filtros avanzados");
    }

    // Cargar marcas en JComboBox
    public void cargarMarcas() {
        List<String> marcas = cochesServicio.obtenerMarcas();
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Todas"); // Añadir opción de "Todas"
        for (String marca : marcas) {
            jComboBox1.addItem(marca);
        }
    }

    // Cargar modelos en función de la marca seleccionada
    public void cargarModelosPorMarca() {
        String marcaSeleccionada = (String) jComboBox1.getSelectedItem();
        if (marcaSeleccionada != null && (marcaSeleccionada.equals("Todas") || marcaSeleccionada.isEmpty())) {
            jComboBox2.removeAllItems();
            jComboBox2.addItem("Todos"); // Mostrar "Todos" si no se ha seleccionado una marca específica
        } else {
            List<String> modelos = cochesServicio.obtenerModelosPorMarca(marcaSeleccionada);
            jComboBox2.removeAllItems();
            jComboBox2.addItem("Todos"); // Añadir opción de "Todos"
            for (String modelo : modelos) {
                jComboBox2.addItem(modelo);
            }
        }
    }

    private void aplicarFiltros() {
        String nombre = jTextField1.getText().trim();
        Integer sexo = null;
        if (jRadioButton1.isSelected()) {
            sexo = 1; // Hombre
        } else if (jRadioButton2.isSelected()) {
            sexo = 2; // Mujer
        }
        String marca = jComboBox1.getSelectedItem().equals("Todas") ? "" : (String) jComboBox1.getSelectedItem();
        String modelo = jComboBox2.getSelectedItem().equals("Todos") ? "" : (String) jComboBox2.getSelectedItem();
        String anoMatriculacion = jTextField2.getText().trim();
        String numVehiculosTexto = jTextField3.getText().trim();

        try {
            // Lógica de aplicación de filtros
            List<PersonasDTO> personasFiltradas = personasServicio.buscarPorNombre(nombre);
            if (sexo != null) {
                Integer finalSexo = sexo;
                personasFiltradas.removeIf(persona -> persona.getSexo().ordinal() != finalSexo);
            }

            List<CochesDTO> cochesFiltrados = cochesServicio.buscarCochesPorAtributos(marca, modelo, anoMatriculacion);
            List<HistorialPropietariosDTO> historial = historialPropietariosServicio.obtenerHistorialCompleto();

            // Filtrar personas por el número de vehículos asociados si se especifica
            if (!numVehiculosTexto.isEmpty()) {
                int numVehiculos = Integer.parseInt(numVehiculosTexto);
                personasFiltradas.removeIf(persona -> {
                    try {
                        return historialPropietariosDAO.contarVehiculosPorPersona(persona.getId()) != numVehiculos;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return true;
                    }
                });
            }

            List<Object[]> resultadosCombinados = combinarPersonasYVehiculos(personasFiltradas, cochesFiltrados, historial);

            actualizarTabla(resultadosCombinados);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al aplicar filtros: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El número de vehículos debe ser un valor numérico", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Object[]> combinarPersonasYVehiculos(List<PersonasDTO> personas, List<CochesDTO> coches, List<HistorialPropietariosDTO> historial) {
        List<Object[]> resultadosCombinados = new ArrayList<>();

        for (PersonasDTO persona : personas) {
            int numVehiculos = 0;
            try {
                numVehiculos = historialPropietariosDAO.contarVehiculosPorPersona(persona.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (CochesDTO coche : coches) {
                boolean encontrado = historial.stream()
                        .anyMatch(registro -> registro.getIdPersonas() == persona.getId() && registro.getIdCoches() == coche.getId());
                if (encontrado) {
                    resultadosCombinados.add(new Object[]{
                        persona.getNombre(),
                        coche.getMarca(),
                        coche.getModelo(),
                        coche.getAnoMatriculacion(),
                        numVehiculos
                    });
                }
            }
        }
        return resultadosCombinados;
    }

    private void actualizarTabla(List<Object[]> resultados) {
        String[] columnas = {"Nombre", "Marca", "Modelo", "Año de matriculación", "Nº de Vehículos"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Object[] fila : resultados) {
            modelo.addRow(fila);
        }

        jTable1.setModel(modelo);
    }

// Cerrar la conexión de la base de datos al cerrar la ventana
    @Override
    public void dispose() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        bFiltrosAvanzados = new javax.swing.JButton();
        PanelTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        PanelFiltros = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        bSiguiente = new javax.swing.JButton();
        bAnterior = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Crear = new javax.swing.JMenuItem();
        AsociarVehiculo = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Vehículos");

        jLabel1.setText("Nombre:");

        jRadioButton1.setText("Hombre");

        jRadioButton2.setText("Mujer");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Marca:");

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Filtros avanzados");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        bFiltrosAvanzados.setText("Aplicar Filtros");
        bFiltrosAvanzados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltrosAvanzadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelDatosLayout = new javax.swing.GroupLayout(PanelDatos);
        PanelDatos.setLayout(PanelDatosLayout);
        PanelDatosLayout.setHorizontalGroup(
            PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bFiltrosAvanzados)
                .addContainerGap())
        );
        PanelDatosLayout.setVerticalGroup(
            PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(bFiltrosAvanzados))
                .addContainerGap(15, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout PanelTablaLayout = new javax.swing.GroupLayout(PanelTabla);
        PanelTabla.setLayout(PanelTablaLayout);
        PanelTablaLayout.setHorizontalGroup(
            PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelTablaLayout.setVerticalGroup(
            PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTablaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel4.setText("Modelo:");

        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel5.setText("Año de Matriculación: ");

        jLabel6.setText("Nº de Vehículos: ");

        javax.swing.GroupLayout PanelFiltrosLayout = new javax.swing.GroupLayout(PanelFiltros);
        PanelFiltros.setLayout(PanelFiltrosLayout);
        PanelFiltrosLayout.setHorizontalGroup(
            PanelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFiltrosLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelFiltrosLayout.setVerticalGroup(
            PanelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFiltrosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PanelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        bSiguiente.setText("Siguiente");
        bSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSiguienteActionPerformed(evt);
            }
        });

        bAnterior.setText("Anterior");
        bAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAnteriorActionPerformed(evt);
            }
        });

        jLabel7.setText("pag: ");

        jMenu1.setText("Opciones");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        Crear.setText("Crear");
        Crear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CrearMouseClicked(evt);
            }
        });
        Crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearActionPerformed(evt);
            }
        });
        jMenu1.add(Crear);

        AsociarVehiculo.setText("AsociarVehiculo");
        AsociarVehiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AsociarVehiculoMouseClicked(evt);
            }
        });
        AsociarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsociarVehiculoActionPerformed(evt);
            }
        });
        jMenu1.add(AsociarVehiculo);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelTabla, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bAnterior)
                        .addGap(296, 296, 296)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSiguiente)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSiguiente)
                    .addComponent(bAnterior)
                    .addComponent(jLabel7)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void CrearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CrearMouseClicked

    }//GEN-LAST:event_CrearMouseClicked

    private void AsociarVehiculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AsociarVehiculoMouseClicked

    }//GEN-LAST:event_AsociarVehiculoMouseClicked

    private void CrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearActionPerformed
        Crear ventanaCrear = new Crear(cochesServicio);
        ventanaCrear.setVisible(true);
    }//GEN-LAST:event_CrearActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

    }//GEN-LAST:event_jMenu1ActionPerformed

    private void AsociarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsociarVehiculoActionPerformed
        Asociar ventanaAsociar = new Asociar(conn);
        ventanaAsociar.setVisible(true);
    }//GEN-LAST:event_AsociarVehiculoActionPerformed

    private void bAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAnteriorActionPerformed

    }//GEN-LAST:event_bAnteriorActionPerformed

    private void bSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSiguienteActionPerformed

    }//GEN-LAST:event_bSiguienteActionPerformed

    private void bFiltrosAvanzadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltrosAvanzadosActionPerformed
        aplicarFiltros();
    }//GEN-LAST:event_bFiltrosAvanzadosActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        cargarModelosPorMarca();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        mostrarFiltros();
    }//GEN-LAST:event_jLabel3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

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
            java.util.logging.Logger.getLogger(RegistroVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroVehiculos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AsociarVehiculo;
    private javax.swing.JMenuItem Crear;
    private javax.swing.JPanel PanelDatos;
    private javax.swing.JPanel PanelFiltros;
    private javax.swing.JPanel PanelTabla;
    private javax.swing.JButton bAnterior;
    private javax.swing.JButton bFiltrosAvanzados;
    private javax.swing.JButton bSiguiente;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
