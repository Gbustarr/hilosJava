import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

// caja puede ser tanto presencial como web
public class caja extends Thread{
    evento_controller evento;
    int idHilo, idEvento;
    int tipo;
    ArrayList<evento_controller> controladorEventos = new ArrayList<>();
    String nombreHilo;
    File f=null;

    public caja(int idHilo, int tipo, ArrayList<evento_controller>controladorEventos){
        this.idHilo = idHilo;
        this.tipo = tipo;
        this.controladorEventos=controladorEventos;
        if(tipo == 0){
            nombreHilo = "Caja " + idHilo;
        }
        else{
            nombreHilo = "Hilo Web " + idHilo;
        }
    }

    public void setEvento(evento_controller evento){
        this.evento = evento;
    }
    @Override
    public void run() {
        LocalDate fechaActual = LocalDate.now();
        int aux=0;
        int aux2=0;
        while (true) {
            if(aux2<10) {
                idEvento = (int) (Math.random() * (controladorEventos.size() - 1));
                setEvento(controladorEventos.get(idEvento));
                evento.comprar(tipo, nombreHilo, new File(fechaActual.plusDays(aux).toString() + ".txt"));
                System.out.println(nombreHilo + " " + evento.eventoControlado.nombre + ": " + evento.eventoControlado.cantidadTickets);
                aux++;
                idEvento = (int) (Math.random() * (controladorEventos.size() - 1));

            }else{
                aux2=0;
                aux++;
            }

            try {
                // Pausa el hilo durante 1 segundo (1000 milisegundos)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
}
