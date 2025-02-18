package hibernate.Proyecto_dgc;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.text.ParseException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.Calendar;


public class HibernateEnterprise {

	public static boolean registrarUsuario(Usuario usuario) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Establece valores adicionales
            usuario.setFechaAlta(new java.sql.Date(new Date().getTime())); // Fecha actual
            usuario.setValidado(false); // Por defecto no validado

            session.save(usuario); // Guardar en la base de datos
            transaction.commit();

            System.out.println("Usuario registrado exitosamente.");
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("Error al hacer rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
	
	public static Usuario iniciarSesion(String email, String contrasena) {
        Transaction transaction = null;
        Usuario usuario = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Busca el usuario por email y contraseña
            Query<Usuario> query = session.createQuery(
                "FROM Usuario u WHERE u.email = :email AND u.contrasena = :contrasena",
                Usuario.class
            );
            query.setParameter("email", email);
            query.setParameter("contrasena", contrasena);
            usuario = query.uniqueResult();

            if (usuario != null) {
                // Actualiza la fecha de último acceso
                usuario.setFechaUltimoAcceso(new Timestamp(System.currentTimeMillis()));
                session.update(usuario); // Actualiza el usuario en la base de datos
                transaction.commit();

                System.out.println("Inicio de sesión exitoso. Bienvenido, " + usuario.getNombre());
            } else {
                System.out.println("Credenciales inválidas. Intente nuevamente.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error durante el inicio de sesión: " + e.getMessage());
        }

        return usuario;
    }
	
	public static void actualizarEstadoProyectos(Usuario usuario, Scanner scanner) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        System.out.print("Introduce el ID del proyecto a actualizar: ");
	        Integer idProyecto = scanner.nextInt();
	        scanner.nextLine(); // Limpiar buffer

	        Proyecto proyecto = session.get(Proyecto.class, idProyecto);
	        if (proyecto == null) {
	            System.out.println("El proyecto no existe.");
	            return;
	        }

	        System.out.print("Introduce el nuevo estado (En tramite/Finalizado): ");
	        String nuevoEstado = scanner.nextLine();

	        Transaction transaction = session.beginTransaction();
	        proyecto.setEstado(nuevoEstado);

	        if ("Finalizado".equalsIgnoreCase(nuevoEstado)) {
	            // Agregar notificación al finalizar el proyecto
	            Notificaciones notificacion = new Notificaciones();
	            notificacion.setUsuario(proyecto.getUsuarioByIdUsuario());
	            notificacion.setFecha(new java.sql.Date(System.currentTimeMillis()));
	            notificacion.setContenido("Su proyecto \"" + proyecto.getNombre() + "\" ha sido finalizado.");
	            session.persist(notificacion);
	        }

	        session.update(proyecto);
	        transaction.commit();
	        System.out.println("El estado del proyecto se ha actualizado correctamente.");
	    } catch (Exception e) {
	        System.err.println("Error al actualizar el estado del proyecto: " + e.getMessage());
	    }
	}

	
	public static List<Alegacion> obtenerAlegacionesPorGestor(Integer idGestor) {
	    List<Alegacion> alegaciones = Collections.emptyList();

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        // Consulta HQL en una única línea compatible con versiones anteriores de Java
	        String hql = "SELECT a FROM Alegacion a JOIN a.proyecto p WHERE p.idGestor = :idGestor";

	        alegaciones = session.createQuery(hql, Alegacion.class)
	                             .setParameter("idGestor", idGestor)
	                             .getResultList();
	    } catch (Exception e) {
	        System.err.println("Error al obtener las alegaciones: " + e.getMessage());
	    }

	    return alegaciones;
	}
	
	public static void revisarAlegaciones(Integer idUsuario) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        // Consulta HQL para obtener alegaciones asociadas al usuario
	        String hql = "SELECT a FROM Alegacion a WHERE a.usuario.idUsuario = :idUsuario";
	        
	        // Ejecutar la consulta
	        List<Alegacion> alegaciones = session.createQuery(hql, Alegacion.class)
	                                             .setParameter("idUsuario", idUsuario)
	                                             .getResultList();

	        // Verificar si no hay alegaciones asociadas
	        if (alegaciones.isEmpty()) {
	            System.out.println("\nNo tienes alegaciones asociadas.\n");
	            return;
	        }

	        // Mostrar las alegaciones
	        System.out.println("\n=== Lista de Alegaciones Asociadas ===");
	        for (Alegacion alegacion : alegaciones) {
	            System.out.printf(
	                "ID: %d\nFecha: %s\nContenido: %s\nResolución: %s\nEstado: %s\n\n",
	                alegacion.getIdAlegacion(),
	                alegacion.getFechaPresentacion(),
	                alegacion.getContenido(),
	                alegacion.getResolucion(),
	                alegacion.getEstado()
	            );
	        }

	        // Espacio para implementar lógica futura
	        System.out.println("¿Deseas realizar alguna acción sobre estas alegaciones? (Funcionalidad futura)\n");

	    } catch (org.hibernate.QueryException qe) {
	        System.err.println("Error en la consulta HQL: " + qe.getMessage());
	    } catch (Exception e) {
	        System.err.println("Error inesperado al obtener las alegaciones: " + e.getMessage());
	    }
	}
	
	public static void mostrarIndicadoresGenerales() {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        System.out.println("\n=== Indicadores Generales ===");

	        // 1. Número total de proyectos
	        String hqlTotalProyectos = "SELECT COUNT(p) FROM Proyecto p";
	        Long totalProyectos = session.createQuery(hqlTotalProyectos, Long.class).getSingleResult();
	        System.out.println("Número total de proyectos: " + totalProyectos);

	        // 2. Número de proyectos por estado
	        String hqlProyectosPorEstado = "SELECT p.estado, COUNT(p) FROM Proyecto p GROUP BY p.estado";
	        List<Object[]> proyectosPorEstado = session.createQuery(hqlProyectosPorEstado).getResultList();
	        System.out.println("\nProyectos por estado:");
	        for (Object[] resultado : proyectosPorEstado) {
	            System.out.printf("Estado: %s - Total: %d%n", resultado[0], resultado[1]);
	        }

	        // 3. Número total de alegaciones
	        String hqlTotalAlegaciones = "SELECT COUNT(a) FROM Alegacion a";
	        Long totalAlegaciones = session.createQuery(hqlTotalAlegaciones, Long.class).getSingleResult();
	        System.out.println("\nNúmero total de alegaciones: " + totalAlegaciones);

	        // 4. Alegaciones por resolución
	        String hqlAlegacionesPorResolucion = "SELECT a.resolucion, COUNT(a) FROM Alegacion a GROUP BY a.resolucion";
	        List<Object[]> alegacionesPorResolucion = session.createQuery(hqlAlegacionesPorResolucion).getResultList();
	        System.out.println("\nAlegaciones por resolución:");
	        for (Object[] resultado : alegacionesPorResolucion) {
	            System.out.printf("Resolución: %s - Total: %d%n", resultado[0], resultado[1]);
	        }

	        // 5. Presupuesto total de proyectos
	        String hqlPresupuestoTotal = "SELECT SUM(p.presupuesto) FROM Proyecto p";
	        BigDecimal presupuestoTotal = session.createQuery(hqlPresupuestoTotal, BigDecimal.class).getSingleResult();
	        System.out.printf("\nPresupuesto total de proyectos: %.2f%n", presupuestoTotal);

	        System.out.println("\n=== Fin de Indicadores ===");

	    } catch (Exception e) {
	        System.err.println("Error al generar los indicadores: " + e.getMessage());
	    }
	}
	
	public static void validarUsuariosPendientes() {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        // Consulta HQL para obtener usuarios con validación pendiente
	        String hql = "FROM Usuario u WHERE u.validado = false";
	        List<Usuario> usuariosPendientes = session.createQuery(hql, Usuario.class).getResultList();

	        if (usuariosPendientes.isEmpty()) {
	            System.out.println("\nNo hay usuarios pendientes de validación.\n");
	            return;
	        }

	        // Mostrar los usuarios pendientes
	        System.out.println("\n=== Usuarios Pendientes de Validación ===");
	        for (Usuario usuario : usuariosPendientes) {
	            System.out.printf("ID: %d - Nombre: %s %s - Email: %s%n",
	                    usuario.getIdUsuario(),
	                    usuario.getNombre(),
	                    usuario.getApellidos(),
	                    usuario.getEmail());
	        }

	        // Solicitar el ID del usuario a validar
	        System.out.print("\nIngrese el ID del usuario que desea validar: ");
	        Scanner scanner = new Scanner(System.in);
	        int idUsuario = scanner.nextInt();

	        // Buscar el usuario seleccionado
	        Usuario usuarioSeleccionado = usuariosPendientes.stream()
	                .filter(u -> u.getIdUsuario() == idUsuario)
	                .findFirst()
	                .orElse(null);

	        if (usuarioSeleccionado == null) {
	            System.out.println("\nEl ID ingresado no corresponde a ningún usuario pendiente.\n");
	            return;
	        }

	        // Validar el usuario
	        Transaction transaction = session.beginTransaction();
	        usuarioSeleccionado.setValidado(true);
	        session.update(usuarioSeleccionado);
	        transaction.commit();

	        System.out.printf("\nEl usuario %s %s ha sido validado correctamente.\n",
	                usuarioSeleccionado.getNombre(),
	                usuarioSeleccionado.getApellidos());
	    } catch (Exception e) {
	        System.err.println("Error al validar usuarios: " + e.getMessage());
	    }
	}
	
	public static void gestionarFavoritos(Integer idUsuario) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Scanner scanner = new Scanner(System.in);

	        // Mostrar proyectos favoritos del usuario
	        String hqlFavoritos = "SELECT f.proyecto " +
	                              "FROM Favoritos f " +
	                              "WHERE f.usuario.idUsuario = :idUsuario";

	        List<Proyecto> favoritos = session.createQuery(hqlFavoritos, Proyecto.class)
	                                          .setParameter("idUsuario", idUsuario)
	                                          .getResultList();

	        System.out.println("\n=== Tus Proyectos Favoritos ===");
	        if (favoritos.isEmpty()) {
	            System.out.println("No tienes proyectos en tu lista de favoritos.");
	        } else {
	            for (Proyecto proyecto : favoritos) {
	                System.out.printf("ID: %d - Nombre: %s%n", proyecto.getIdProyecto(), proyecto.getNombre());
	            }
	        }

	        // Opción para agregar un proyecto a favoritos
	        System.out.print("\n¿Deseas agregar un proyecto a favoritos? (s/n): ");
	        String respuesta = scanner.nextLine();

	        if (respuesta.equalsIgnoreCase("s")) {
	            System.out.print("Introduce el ID del proyecto que deseas agregar: ");
	            int idProyecto = scanner.nextInt();

	            // Verificar si el proyecto ya está en favoritos
	            String hqlVerificar = "SELECT COUNT(f) " +
	                                  "FROM Favoritos f " +
	                                  "WHERE f.usuario.idUsuario = :idUsuario " +
	                                  "AND f.proyecto.idProyecto = :idProyecto";

	            Long count = session.createQuery(hqlVerificar, Long.class)
	                                .setParameter("idUsuario", idUsuario)
	                                .setParameter("idProyecto", idProyecto)
	                                .getSingleResult();

	            if (count > 0) {
	                System.out.println("Este proyecto ya está en tu lista de favoritos.");
	            } else {
	                // Agregar el proyecto a favoritos
	                Transaction transaction = session.beginTransaction();
	                Favoritos nuevoFavorito = new Favoritos();
	                Usuario usuario = session.get(Usuario.class, idUsuario);
	                Proyecto proyecto = session.get(Proyecto.class, idProyecto);

	                if (usuario == null || proyecto == null) {
	                    System.out.println("Usuario o proyecto no encontrado. Verifica los IDs.");
	                    return;
	                }

	                nuevoFavorito.setUsuario(usuario);
	                nuevoFavorito.setProyecto(proyecto);
	                session.persist(nuevoFavorito);
	                transaction.commit();
	                System.out.println("¡Proyecto agregado a tus favoritos!");
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("Error al gestionar favoritos: " + e.getMessage());
	    }
	}
	
	public static void buscarProyectos() {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Scanner scanner = new Scanner(System.in);

	        // Mostrar opciones de filtro
	        System.out.println("\n=== Búsqueda de Proyectos ===");
	        System.out.println("1. Buscar por nombre");
	        System.out.println("2. Buscar por estado");
	        System.out.println("3. Salir de la búsqueda");

	        System.out.print("\nElige una opción: ");
	        int opcion = scanner.nextInt();
	        scanner.nextLine(); // Limpiar buffer

	        // Variables para construir dinámicamente la consulta
	        StringBuilder hql = new StringBuilder("FROM Proyecto p WHERE 1=1");
	        Map<String, Object> parametros = new HashMap<>();

	        switch (opcion) {
	            case 1:
	                System.out.print("Introduce el nombre del proyecto: ");
	                String nombre = scanner.nextLine();
	                hql.append(" AND p.nombre LIKE :nombre");
	                parametros.put("nombre", "%" + nombre + "%");
	                break;

	            case 2:
	                System.out.print("Introduce el estado (En tramite/Finalizado): ");
	                String estado = scanner.nextLine();
	                hql.append(" AND p.estado = :estado");
	                parametros.put("estado", estado);
	                break;

	            case 3:
	                System.out.println("Saliendo de la búsqueda...");
	                return;

	            default:
	                System.out.println("Opción no válida.");
	                return;
	        }

	        // Crear la consulta HQL
	        Query<Proyecto> query = session.createQuery(hql.toString(), Proyecto.class);
	        for (Map.Entry<String, Object> entry : parametros.entrySet()) {
	            query.setParameter(entry.getKey(), entry.getValue());
	        }

	        // Ejecutar la consulta
	        List<Proyecto> proyectos = query.getResultList();

	        // Mostrar resultados
	        if (proyectos.isEmpty()) {
	            System.out.println("\nNo se encontraron proyectos con los criterios especificados.");
	        } else {
	            System.out.println("\n=== Resultados de la búsqueda ===");
	            for (Proyecto proyecto : proyectos) {
	                System.out.printf("ID: %d - Nombre: %s - Estado: %s - Presupuesto: %.2f - Fecha Inicio: %s - Fecha Fin: %s%n",
	                    proyecto.getIdProyecto(),
	                    proyecto.getNombre(),
	                    proyecto.getEstado(),
	                    proyecto.getPresupuesto(),
	                    proyecto.getFechaInicio(),
	                    proyecto.getFechaFin());
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("Error al buscar proyectos: " + e.getMessage());
	    }
	}
	
	public static void realizarAlegacion(Integer idUsuario) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Scanner scanner = new Scanner(System.in);

	        // Mostrar proyectos disponibles
	        String hqlProyectos = "FROM Proyecto p";
	        List<Proyecto> proyectos = session.createQuery(hqlProyectos, Proyecto.class).getResultList();

	        System.out.println("\n=== Proyectos Disponibles ===");
	        for (Proyecto proyecto : proyectos) {
	            System.out.printf("ID: %d - Nombre: %s - Estado: %s%n", 
	                proyecto.getIdProyecto(), 
	                proyecto.getNombre(), 
	                proyecto.getEstado());
	        }

	        // Seleccionar proyecto
	        System.out.print("\nIntroduce el ID del proyecto para realizar la alegación: ");
	        Integer idProyecto = scanner.nextInt();
	        scanner.nextLine(); // Limpiar buffer

	        Proyecto proyectoSeleccionado = session.get(Proyecto.class, idProyecto);
	        if (proyectoSeleccionado == null) {
	            System.out.println("El proyecto seleccionado no existe.");
	            return;
	        }

	        // Recoger contenido de la alegación
	        System.out.print("Introduce el contenido de la alegación: ");
	        String contenido = scanner.nextLine();

	        // Crear nueva alegación
	        Transaction transaction = session.beginTransaction();
	        Alegacion nuevaAlegacion = new Alegacion();

	        nuevaAlegacion.setUsuario(session.get(Usuario.class, idUsuario));
	        nuevaAlegacion.setProyecto(proyectoSeleccionado);
	        nuevaAlegacion.setFechaPresentacion(new java.sql.Date(System.currentTimeMillis()));
	        nuevaAlegacion.setContenido(contenido);
	        nuevaAlegacion.setResolucion("Pendiente");
	        nuevaAlegacion.setCodigoValidacion(UUID.randomUUID().toString()); // Generar código único
	        nuevaAlegacion.setEstado("Pendiente");

	        session.persist(nuevaAlegacion);
	        transaction.commit();

	        System.out.println("\n¡Alegación realizada correctamente!");
	    } catch (Exception e) {
	        System.err.println("Error al realizar la alegación: " + e.getMessage());
	    }
	}

	public static void subirInforme(Integer idUsuario, Integer idProyecto) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Scanner scanner = new Scanner(System.in);

	        // Obtener el nombre del autor basado en el idUsuario
	        Usuario usuario = session.get(Usuario.class, idUsuario);
	        if (usuario == null) {
	            System.out.println("Usuario no encontrado. No se puede subir el informe.");
	            return;
	        }

	        String nombreAutor = usuario.getNombre(); // Obtener el nombre del autor

	        // Crear un nuevo informe
	        Informe nuevoInforme = new Informe();
	        nuevoInforme.setAutor(nombreAutor); // Asignar el nombre del autor
	        nuevoInforme.setEstado("Pendiente"); // Estado inicial
	        nuevoInforme.setFechaCreacion(new java.sql.Date(System.currentTimeMillis())); // Fecha actual

	        System.out.print("Introduce el contenido del informe: ");
	        String contenido = scanner.nextLine();
	        nuevoInforme.setContenido(contenido); // Establecer el contenido del informe

	        System.out.print("Introduce el tipo de informe (Informe Técnico, Informe Final, etc.): ");
	        String tipoInforme = scanner.nextLine();
	        nuevoInforme.setTipoInforme(tipoInforme); // Establecer el tipo de informe

	        // Asociar el informe al proyecto
	        Proyecto proyecto = session.get(Proyecto.class, idProyecto);
	        if (proyecto == null) {
	            System.out.println("Proyecto no encontrado. No se puede subir el informe.");
	            return;
	        }
	        nuevoInforme.setProyecto(proyecto);

	        // Iniciar transacción y guardar el informe
	        Transaction transaction = session.beginTransaction();
	        session.persist(nuevoInforme);
	        transaction.commit();

	        System.out.println("¡Informe subido exitosamente!");
	    } catch (Exception e) {
	        System.err.println("Error al subir el informe: " + e.getMessage());
	    }
	}
	
	public static void verProyectosRelacionados() {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Scanner scanner = new Scanner(System.in);

	        // Menú de opciones
	        System.out.println("\n=== Ver Proyectos Relacionados ===");
	        System.out.println("1. Proyectos asignados a un gestor");
	        System.out.println("2. Proyectos con informes asociados");
	        System.out.println("3. Salir");

	        System.out.print("\nElige una opción: ");
	        int opcion = scanner.nextInt();
	        scanner.nextLine(); // Limpiar buffer

	        StringBuilder hql = new StringBuilder("FROM Proyecto p WHERE 1=1");
	        Map<String, Object> parametros = new HashMap<>();

	        switch (opcion) {
	            case 1:
	                System.out.print("Introduce el ID del gestor: ");
	                int idGestor = scanner.nextInt();
	                hql.append(" AND p.usuarioByIdGestor.idUsuario = :idGestor");
	                parametros.put("idGestor", idGestor);
	                break;

	            case 2:
	                hql.append(" AND EXISTS (SELECT i FROM Informe i WHERE i.proyecto.idProyecto = p.idProyecto)");
	                System.out.println("Mostrando proyectos con informes asociados.");
	                break;

	            case 3:
	                System.out.println("Saliendo...");
	                return;

	            default:
	                System.out.println("Opción no válida.");
	                return;
	        }

	        // Crear consulta HQL
	        Query<Proyecto> query = session.createQuery(hql.toString(), Proyecto.class);
	        for (Map.Entry<String, Object> entry : parametros.entrySet()) {
	            query.setParameter(entry.getKey(), entry.getValue());
	        }

	        // Ejecutar consulta y mostrar resultados
	        List<Proyecto> proyectos = query.getResultList();

	        if (proyectos.isEmpty()) {
	            System.out.println("\nNo se encontraron proyectos relacionados con los criterios especificados.");
	        } else {
	            System.out.println("\n=== Proyectos Relacionados ===");
	            for (Proyecto proyecto : proyectos) {
	                System.out.printf("ID: %d - Nombre: %s - Estado: %s - Gestor: %s - Presupuesto: %.2f%n",
	                    proyecto.getIdProyecto(),
	                    proyecto.getNombre(),
	                    proyecto.getEstado(),
	                    proyecto.getUsuarioByIdGestor().getNombre(),
	                    proyecto.getPresupuesto());
	            }
	        }

	    } catch (Exception e) {
	        System.err.println("Error al buscar proyectos relacionados: " + e.getMessage());
	    }
	}
	
	public static void verNotificaciones(Integer idUsuario) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        // Consulta HQL para obtener las notificaciones del usuario
	        String hql = "FROM Notificaciones n WHERE n.usuario.idUsuario = :idUsuario ORDER BY n.fecha DESC";
	        
	        List<Notificaciones> notificaciones = session.createQuery(hql, Notificaciones.class)
	                                                      .setParameter("idUsuario", idUsuario)
	                                                      .getResultList();

	        // Mostrar las notificaciones
	        System.out.println("\n=== Notificaciones Asociadas ===");
	        if (notificaciones.isEmpty()) {
	            System.out.println("No tienes notificaciones.");
	        } else {
	            for (Notificaciones notificacion : notificaciones) {
	                System.out.printf("ID: %d - Fecha: %s - Contenido: %s%n",
	                    notificacion.getIdNotificacion(),
	                    notificacion.getFecha(),
	                    notificacion.getContenido());
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("Error al obtener las notificaciones: " + e.getMessage());
	    }
	}
}