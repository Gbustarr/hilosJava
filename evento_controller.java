// evento_controller gestiona el acceso hacia el info_evento designado
// esto garantiza que los hilos al ejecutar esta clase, se ejecuten en orden de llegada.

import java.util.concurrent.Semaphore;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class evento_controller {
    String nombre_evento = "";
    Semaphore semaforo = new Semaphore(5);
    private static Lock lock = new ReentrantLock();
    info_evento eventoControlado = null;
    Random random = new Random();
    
    // Constructor
    public evento_controller(String n, info_evento evento) {
        this.nombre_evento = n;
        this.eventoControlado = evento;
    }

    // Comprar tickets
    public int comprar(int tipo, String infoHilo, File dia) { // 0 : Presencial, 1 : Web

        try {
            semaforo.acquire();
            lock.lock();
            if (eventoControlado.hayTickets()) { // Verificando si el evento tiene tickets disponibles
                if (tipo == 1) { // Web
                    int asientos = getRandomNumberSeats();

                    if (eventoControlado.esComprableWeb(asientos)) { // Check para poder comprar
                        eventoControlado.comprar(asientos);
                        log(infoHilo + " compra " + asientos + " ticket de " + eventoControlado.getNombre()+" tickets restantes:"+eventoControlado.cantidadTickets,dia  );
                        semaforo.release();
                        lock.unlock();
                        // resultado(infoHilo,asientos,eventoControlado,1); // Venta online satisfactoria
                        return eventoControlado.cantidadTickets;
                    } else {
                        log(infoHilo + " intenta comprar " + asientos + " ticket de " + eventoControlado.getNombre()
                                + " - No quedan disponibles (>80%)"+" tickets restantes:"+eventoControlado.cantidadTickets, dia);
                        semaforo.release();
                        lock.unlock();
                        
                        //resultado(infoHilo,asientos,eventoControlado,2);  // Venta online fallida, tickets a comprar sobrepasa al 80% vendido
                        return eventoControlado.cantidadTickets;
                    }

                } else { // Presencial
                    int asientos = getRandomNumberSeats();

                    if (eventoControlado.comprar(asientos)) {
                        log(infoHilo + " compra " + asientos + " ticket de " + eventoControlado.getNombre()+" tickets restantes:"+eventoControlado.cantidadTickets, dia);
                        semaforo.release();
                        lock.unlock();
                        //resultado(infoHilo,asientos,eventoControlado,3);  // Venta Presencial exitosa
                        return eventoControlado.cantidadTickets; 
                    } else {
                        log(infoHilo + " intenta comprar " + asientos + " ticket de " + eventoControlado.getNombre()
                                + " - Compra mayor al disponible "+" tickets restantes:"+eventoControlado.cantidadTickets, dia);
                        semaforo.release();
                        lock.unlock();
                        //resultado(infoHilo,asientos,eventoControlado,4);  // Venta Presencial fallida
                        return eventoControlado.cantidadTickets;
                    }

                }

            } else {
                log(infoHilo + " intenta comprar tickets de " + eventoControlado.getNombre()
                        + " - No hay tickets disponibles "+" tickets restantes:"+eventoControlado.cantidadTickets, dia);
                semaforo.release();
                lock.unlock();
                //resultado(infoHilo,0,eventoControlado,5);  // No hay tickets disponibles para vender
                return eventoControlado.cantidadTickets;
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

    public void log(String INFO, File f) throws IOException {
        FileWriter fileWriter = new FileWriter(f, true);
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