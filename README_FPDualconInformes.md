# FPDualconInformes

El proyecto **FPDualconInformes** tiene como objetivo gestionar usuarios e informes en un sistema de FPDual. En la primera ventana, se presentan dos botones: uno para la creación de usuarios y otro para la creación de informes.

## Funcionalidad

### 1. **Creación de Usuarios:**
Si clickeas en el botón de **USUARIOS**, serás redirigido a una ventana donde podrás crear un usuario para agregarlo a la base de datos. Solo tienes que rellenar los campos solicitados y presionar el botón de **Enviar** para almacenar el usuario.

### 2. **Generación de Informes:**
Si seleccionas el botón de **INFORMES**, se mostrarán dos grupos de checkboxes:
- El primer grupo es obligatorio y permite seleccionar el tipo de informe que deseas ver o descargar. Si no se selecciona ningún informe, el sistema mostrará un mensaje de error y no abrirá ningún informe.
- El segundo grupo de checkboxes permite elegir el formato en el que deseas descargar el informe.

Una vez seleccionado el informe y el formato, podrás ver el informe usando el botón **VER INFORME**, que abre el informe en un visor JasperViewer. Si prefieres descargar el informe, el sistema lo descargará en el formato seleccionado y abrirá un segundo visor JasperViewer para su visualización.

### 3. **Gráfico de Participantes:**
En la parte inferior de la ventana, hay un botón para visualizar un gráfico que muestra los participantes de FPDual.

## Características
- **Gestión de usuarios**: Crear, almacenar y gestionar usuarios en la base de datos.
- **Generación de informes**: Ver y descargar informes en distintos formatos.
- **Visualización de gráficos**: Mostrar un gráfico con los participantes de FPDual.

## Instalación
1. **Descargar el proyecto:**
   Haz clic en el siguiente enlace para descargar el archivo comprimido `.rar`:
   [Descargar FPDualconInformes.rar](https://github.com/LauraJuncos/LauraJuncos.github.io/blob/main/FPDualconInformes.rar)

2. **Descomprimir el archivo:**
   Una vez descargado, descomprime el archivo `.rar` en una carpeta de tu elección.

3. **Instalar dependencias:**
   Todas las dependencias están incluidas en el pom.
   La base de datos esta dentro de la carpeta que habéis descargado, importala en el xampp.
