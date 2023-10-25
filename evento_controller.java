// evento_controller gestiona el acceso hacia el info_evento designado
// esto garantiza que los hilos al ejecutar esta clase, se ejecuten en orden de llegada.

import java.util.concurrent.Semaphore;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class evento_controller {
    String nombre_evento = "";
    Semaphore semaforo = new Semaphore(1);
    info_evento eventoControlado = null;
    Random random = new Random();
    File logDia = null; // Referencia al archivo de log del dia

    // Constructor
    public evento_controller(String n, info_evento evento, File f) {
        this.nombre_evento = n;
        this.eventoControlado = evento;
        this.logDia = f;
    }

    // Comprar tickets
    public int comprar(int tipo, String infoHilo) { // 0 : Presencial, 1 : Web

        try {
            semaforo.acquire();
            if (eventoControlado.hayTickets()) { // Verificando si el evento tiene tickets disponibles
                if (tipo == 1) { // Web
                    int asientos = getRandomNumberSeats();

                    if (eventoControlado.esComprableWeb(asientos)) { // Check para poder comprar
                        eventoControlado.comprar(asientos);
                        log(infoHilo + " compra " + asientos + " ticket de " + eventoControlado.getNombre());
                        semaforo.release();
                        return 1; // Venta online satisfactoria
                    } else {
                        log(infoHilo + " intenta comprar " + asientos + " ticket de " + eventoControlado.getNombre()
                                + " - No quedan disponibles (>80%)");
                        semaforo.release();
                        return 2; // Venta online fallida, tickets a comprar sobrepasa al 80% vendido
                    }

                } else { // Presencial
                    int asientos = getRandomNumberSeats();

                    if (eventoControlado.comprar(asientos)) {
                        log(infoHilo + " compra " + asientos + " ticket de " + eventoControlado.getNombre());
                        semaforo.release();
                        return 3; // Venta Presencial exitosa
                    } else {
                        log(infoHilo + " intenta comprar " + asientos + " ticket de " + eventoControlado.getNombre()
                                + " - Compra mayor al disponible ");
                        semaforo.release();
                        return 4; // Venta Presencial fallida
                    }

                }

            } else {
                log(infoHilo + " intenta comprar tickets de " + eventoControlado.getNombre()
                        + " - No hay tickets disponibles ");
                semaforo.release();
                return 5; // No hay tickets disponibles para vender
            }
        } catch (Exception e) {
            // Error mi king
            e.printStackTrace();
            return -1; // Error
        }

    }

    public int getRandomNumberSeats() {
        int numeroAleatorio = random.nextInt(3);
        numeroAleatorio = +1; // Para evitar que genere 0
        return numeroAleatorio;
    }

    public void log(String INFO) throws IOException {
        FileWriter fileWriter = new FileWriter(this.logDia, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // Línea a agregar al archivo
        String linea = INFO;

        // Agrega la línea al archivo
        bufferedWriter.write(linea);
        bufferedWriter.newLine(); // Agrega un salto de línea

        // Cierra el BufferedWriter
        bufferedWriter.close();
    }

}