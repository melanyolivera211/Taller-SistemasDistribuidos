package imc.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import imc.modelo.CalculoImc;
import imc.vistas.VentanaPrincipal;

public class SubProcesoCliente extends Thread {
    private Socket cliente;
    private String ip;
    private VentanaPrincipal ventana;

    public SubProcesoCliente(Socket cliente, VentanaPrincipal v) {
        this.cliente = cliente;
        this.ip = cliente.getInetAddress().getHostAddress();
        this.ventana = v;
    }

    @Override
    public void run() {
        try {
            CalculoImc.Imc imc = calcularImc();
            enviarRespuesta(imc);
        } catch (Exception ex) {
            String msg = log() + ex.getMessage();
            System.out.println(msg);
            ventana.getCajaLog().append(msg + "\n");
            try {
                cliente.close();
            } catch (IOException e) {
                ServidorTcp.listaDeClientes.remove(ip);
            } finally {
                ServidorTcp.listaDeClientes.remove(ip);
            }
        }
    }

    public CalculoImc.Imc calcularImc() throws Exception {
        DataInputStream input = null;
        try {
            input = new DataInputStream(cliente.getInputStream());
            String msg = "Esperando el PESO: ";
            System.out.println(log() + msg);
            ventana.getCajaLog().append(log() + msg + "\n\n");
            float peso = input.readFloat();
            msg = "PESO: " + peso;
            System.out.println(log() + msg);
            ventana.getCajaLog().append(log() + msg + "\n");
            msg = "Esperando La Altura: ";
            System.out.println(log() + msg);
            ventana.getCajaLog().append(log() + msg + "\n");
            float altura = input.readFloat();
            msg = "ALTURA: " + altura;
            System.out.println(log() + msg);
            ventana.getCajaLog().append(log() + msg + "\n");
            CalculoImc datosImc = new CalculoImc(peso, altura);
            msg = "IMC: " + datosImc.getImc().resultado;
            System.out.println(log() + msg);
            ventana.getCajaLog().append(log() + msg + "\n");
            msg = "MENSAJE: " + datosImc.getImc().mensaje;
            System.out.println(log() + msg);
            ventana.getCajaLog().append(log() + msg + "\n");
            return datosImc.getImc();
        } catch (IOException ex) {
            String msg = "Error al capturar datos del cliente " + ip;
            System.out.println(log() + msg);
            ventana.getCajaLog().append(log() + msg + "\n");
            throw new Exception("Error al capturar datos del cliente " + ip);
        }
    }

    public void enviarRespuesta(CalculoImc.Imc imc) {
        Thread hiloResponde = new Thread() {
            @Override
            public void run() {
                try (DataOutputStream output = new DataOutputStream(cliente.getOutputStream())) {
                    output.writeFloat(imc.resultado);
                    output.writeUTF(imc.mensaje);
                    output.flush();
                    String msg = "IMC: " + imc.resultado;
                    System.out.println(log() + msg);
                    ventana.getCajaLog().append(log() + msg + "\n");
                    msg = "MENSAJE: " + imc.mensaje;
                    System.out.println(log() + msg);
                    ventana.getCajaLog().append(log() + msg + "\n");
                } catch (IOException ex) {
                    String msg = "Error al enviar datos al cliente " + ip;
                    System.out.println(log() + msg);
                    ventana.getCajaLog().append(log() + msg + "\n");
                    ServidorTcp.listaDeClientes.remove(ip);
                }
            }
        };
        hiloResponde.start();
    }

    public String log() {
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        return ip + " -> " + f.format(new Date()) + " - ";
    }

    public Socket getCliente() {
        return cliente;
    }
}
