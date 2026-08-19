package imc.servidor;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import imc.vistas.VentanaPrincipal;

public class ServidorTcp extends Thread {
    private Boolean estado;
    public static Map<String, SubProcesoCliente> listaDeClientes;
    private Integer puerto = 9007;
    private ServerSocket servicio;
    private VentanaPrincipal ventana;

    public ServidorTcp(Integer puerto, VentanaPrincipal v) {
        if (puerto != null && puerto != 0) {
            this.puerto = puerto;
        }
        this.ventana = v;
        listaDeClientes = new HashMap<>();
    }

    @Override
    public void run() {
        iniciarServicio();
    }

    public void iniciarServicio() {
        try {
            servicio = new ServerSocket(puerto);
            estado = true;
            ventana.getBtnIniciar().setText("DETENER");
            ventana.getTxtEstado().setText("ONLINE");
            ventana.getTxtEstado().setForeground(Color.GREEN);
            ventana.getBtnIniciar().setForeground(Color.RED);
            String msg = log() + "Servidor disponible en el Puerto " + puerto;
            System.out.println(msg);
            ventana.getCajaLog().append(msg + "\n");
            while (estado) {
                Socket cliente = servicio.accept();
                String ip = cliente.getInetAddress().getHostAddress();
                msg = log() + "Cliente " + ip + " conectado";
                System.out.println(msg);
                ventana.getCajaLog().append(msg + "\n");
                SubProcesoCliente atencion = new SubProcesoCliente(cliente, ventana);
                listaDeClientes.put(ip, atencion);
                atencion.start();
            }
        } catch (IOException ex) {
            String msg = log() + "ERROR al abrir el puerto " + puerto;
            System.out.println(msg);
            ventana.getCajaLog().append(msg + "\n");
            ventana.getBtnIniciar().setText("INICIAR");
            ventana.getTxtEstado().setText("OFF LINE");
        }
    }

    public void detenerServicio() {
        if (estado) {
            estado = false;
            ventana.getBtnIniciar().setText("INICIAR");
            ventana.getBtnIniciar().setForeground(Color.GREEN);
            ventana.getTxtEstado().setText("OFF LINE");
            ventana.getTxtEstado().setForeground(Color.RED);
            // Desconectar todos los clientes
            for (Map.Entry<String, SubProcesoCliente> entry : listaDeClientes.entrySet()) {
                String ip = entry.getKey();
                SubProcesoCliente cliente = entry.getValue();
                try {
                    cliente.getCliente().close();
                } catch (IOException ex) {
                    // ignore
                }
            }
            listaDeClientes.clear();
            try {
                servicio.close();
            } catch (IOException ex) {
                String msg = log() + "ERROR no se puede cerrar el Puerto " + puerto;
                System.out.println(msg);
                ventana.getCajaLog().append(msg + "\n");
            }
        }
    }

    public String log() {
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        return "SERVIDOR -> " + f.format(new Date()) + " - ";
    }
}
