package hibernate.Proyecto_dgc;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("=== Menú Principal ===");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea después del número

            switch (opcion) {
                case 1:
                    registrarUsuario(scanner);
                    break;
                case 2:
                    Usuario usuario = iniciarSesion(scanner);
                    if (usuario != null) {
                        mostrarMenuPorTipoUsuario(usuario, scanner);
                        return; // Salir del ciclo principal al mostrar el menú del usuario
                    }
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        scanner.close();
        HibernateUtil.shutdown(); // Cerrar la fábrica de sesiones al finalizar
    }

    public static void registrarUsuario(Scanner scanner) {
        // Este método no se modifica
        // ...
    }

    public static Usuario iniciarSesion(Scanner scanner) {
        System.out.println("=== Inicio de Sesión ===");
        System.out.print("Ingrese su Email: ");
        String email = scanner.next();

        System.out.print("Ingrese su Contraseña: ");
        String contrasena = scanner.next();

        Usuario usuario = HibernateEnterprise.iniciarSesion(email, contrasena);

        if (usuario != null) {
            System.out.println("¡Bienvenido, " + usuario.getNombre() + "!");
        } else {
            System.out.println("Error: Inicio de sesión fallido. Credenciales inválidas.");
        }

        return usuario;
    }

    public static void mostrarMenuPorTipoUsuario(Usuario usuario, Scanner scanner) {
        switch (usuario.getTipoUsuario()) {
            case GESTOR:
                mostrarMenuGestor(usuario, scanner);
                break;
            case USUARIO:
                mostrarMenuUsuario(scanner);
                break;
            case ADMINISTRACIONPUBLICA:
                mostrarMenuAdministracionPublica(scanner);
                break;
            default:
                System.out.println("Tipo de usuario no reconocido.");
        }
    }

    public static void mostrarMenuGestor(Usuario usuario, Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("=== Menú Gestor ===");
            System.out.println("1. Actualizar Estados");
            System.out.println("2. Revisar Alegaciones");
            System.out.println("3. Configurar Indicadores");
            System.out.println("4. Validación de Usuarios");
            System.out.println("5. Agregar a Favoritos");
            System.out.println("6. Buscar Proyectos");
            System.out.println("7. Notificaciones");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    HibernateEnterprise.actualizarEstadoProyectos(usuario, scanner);
                    break;
                case 2:
                	HibernateEnterprise.revisarAlegaciones(1);
                    break;
                case 3:
                    HibernateEnterprise.mostrarIndicadoresGenerales();
                    break;
                case 4:
                    HibernateEnterprise.validarUsuariosPendientes();
                    break;
                case 5:
                    System.out.print("Introduce tu ID de usuario: ");
                    int idUsuario = scanner.nextInt();
                    HibernateEnterprise.gestionarFavoritos(idUsuario);
                    break;
                case 6:
                    HibernateEnterprise.buscarProyectos();
                    break;
                case 7:
                    HibernateEnterprise.verNotificaciones(1);
                    break;
                case 8:
                    System.out.println("Saliendo del menú gestor...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    public static void mostrarMenuUsuario(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("=== Menú Usuario ===");
            System.out.println("1. Agregar a Favoritos");
            System.out.println("2. Realizar Alegaciones");
            System.out.println("3. Buscar Proyectos");
            System.out.println("4. Notificaciones");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                	System.out.print("Introduce tu ID de usuario: ");
                    int idUsuario = scanner.nextInt();
                    HibernateEnterprise.gestionarFavoritos(idUsuario);
                    break;
                case 2:
                	System.out.print("Introduce tu ID de usuario: ");
                    int idUsuario1 = scanner.nextInt();
                    HibernateEnterprise.realizarAlegacion(idUsuario1);
                    break;
                case 3:
                	HibernateEnterprise.buscarProyectos();
                    break;
                case 4:
                	HibernateEnterprise.verNotificaciones(1);
                    break;
                case 5:
                    System.out.println("Saliendo del menú usuario...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    public static void mostrarMenuAdministracionPublica(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("=== Menú Administración Pública ===");
            System.out.println("1. Subir Informes");
            System.out.println("2. Buscar Proyectos");
            System.out.println("3. Ver Proyectos Relacionados");
            System.out.println("4. Notificaciones");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                	System.out.print("Introduce el ID del usuario que sube el informe: ");
                    Integer idUsuario = scanner.nextInt();
                    System.out.print("Introduce el ID del proyecto asociado al informe: ");
                    Integer idProyecto = scanner.nextInt();
                    HibernateEnterprise.subirInforme(idUsuario, idProyecto);
                    break;
                case 2:
                	HibernateEnterprise.buscarProyectos();
                    break;
                case 3:
                    HibernateEnterprise.verProyectosRelacionados();
                    break;
                case 4:
                	HibernateEnterprise.verNotificaciones(1);
                    break;
                case 5:
                    System.out.println("Saliendo del menú administración pública...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}