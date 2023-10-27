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

        evento.comprar(tipo,nombreHilo);
    }
}
