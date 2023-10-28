// caja puede ser tanto presencial como web
public class caja extends Thread{
    evento_controller evento;
    int idHilo;
    int tipo;
    String nombreHilo;

    public caja(int idHilo, int tipo){
        this.idHilo = idHilo;
        this.tipo = tipo;
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
        while (true) {
            evento.comprar(tipo,nombreHilo);
            System.out.println(nombreHilo+" "+evento.eventoControlado.nombre+": "+evento.eventoControlado.cantidadTickets);
            try {
                // Pausa el hilo durante 1 segundo (1000 milisegundos)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
}
