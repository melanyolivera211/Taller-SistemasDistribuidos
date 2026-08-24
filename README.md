# Sistema de Cálculo de IMC con Sockets TCP en Java

Este proyecto es una aplicación cliente-servidor desarrollada en **Java** utilizando **Sockets TCP** e **Interfaz Gráfica de Usuario (GUI) con Java Swing**. Permite calcular el Índice de Masa Corporal (IMC) a partir del peso y la altura proporcionados por el cliente, procesando la solicitud de manera concurrente en el servidor mediante multihilo (*multithreading*).

---

## 📹 Video de Demostración (YouTube)

Puedes ver la demostración del funcionamiento del proyecto en el siguiente enlace:

> 🔗 **[Sistemas Distribuidos - Taller 1: Cálculo de IMC con Sockets TCP en Java](https://youtu.be/1CqIF6QqpCE)**

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java (JDK 8 o superior)
- **Comunicación en red:** Sockets TCP (`ServerSocket`, `Socket`)
- **Interfaz Gráfica:** Java Swing / AWT
- **Concurrencia:** Hilos (`Thread`, `SubProcesoCliente`)
- **Control de Versiones:** Git & GitHub

---

## 🏗️ Arquitectura del Proyecto

El proyecto está dividido en dos partes independientes:

```text
SocketIMC/
├── cliente/                # Proyecto Cliente Java
│   └── src/
│       └── imc/
│           └── cliente/
│               ├── Principal.java
│               └── vistas/VentanaPrincipal.java
└── servidor/               # Proyecto Servidor Java
    └── src/
        └── imc/
            ├── Principal.java
            ├── modelo/CalculoImc.java
            ├── servidor/ServidorTcp.java y SubProcesoCliente.java
            └── vistas/VentanaPrincipal.java
```

1. **Servidor (`/servidor`)**: 
   - Escucha conexiones en el puerto de red `9007` (configurable).
   - Administra conexiones concurrentes asignando a cada cliente un hilo (`SubProcesoCliente`).
   - Realiza el cálculo del IMC y categoriza el estado de salud (Bajo peso, Peso normal, Sobrepeso, Obesidad).
   - Muestra un registro de actividades (*logs*) en tiempo real.

2. **Cliente (`/cliente`)**:
   - Interfaz gráfica para conectar/desconectar del servidor especificando Dirección IP y Puerto.
   - Formulario para enviar los datos de **Peso (kg)** y **Altura (m)**.
   - Recibe y muestra la respuesta del servidor con el resultado numérico y la interpretación médica.

---

## 🚀 Cómo Iniciar el Proyecto (Uso de 2 Terminales)

Para probar la comunicación Cliente-Servidor, abre **dos terminales** independientes en la carpeta raíz del proyecto.

### 📌 Terminal 1: Servidor

1. Navega a la carpeta `src` del servidor:
   ```bash
   cd servidor/src
   ```
2. Ejecuta la clase principal del servidor:
   ```bash
   java imc.Principal
   ```
   *(Si requieres compilar previamente los archivos Java: `javac imc/Principal.java`)*

3. En la interfaz gráfica que se abre, haz clic en el botón **"INICIAR SERVICIO"** (escuchará por defecto en el puerto `9007`).

---

### 📌 Terminal 2: Cliente

1. Navega a la carpeta `src` del cliente:
   ```bash
   cd cliente/src
   ```
2. Ejecuta la clase principal del cliente:
   ```bash
   java imc.cliente.Principal
   ```
   *(Si requieres compilar previamente los archivos Java: `javac imc/cliente/Principal.java`)*

3. En la interfaz gráfica del cliente:
   - Ingresa la dirección IP (`localhost` para pruebas locales) y el puerto (`9007`).
   - Haz clic en **"Conectar"**.
   - Pásate a la pestaña de cálculo, ingresa el **Peso (kg)** y la **Altura (m)**, y presiona el botón para realizar el cálculo.

---

## ⚙️ Ejecución desde IDE (NetBeans / Eclipse / VS Code / IntelliJ)

Si prefieres usar un IDE:
1. Abre los proyectos `servidor` y `cliente` por separado.
2. Ejecuta la clase principal del Servidor (`imc.Principal.java`).
3. Presiona **Iniciar** en la ventana del servidor.
4. Ejecuta la clase principal del Cliente (`imc.cliente.Principal.java`).
5. Conéctate y prueba el sistema.

---

## 📝 Autor / Estudiante

- **Materia:** Sistemas Distribuidos
- **Taller:** Taller 1 - Sockets IMC
