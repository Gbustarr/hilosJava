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
    int aux2[];
    LocalDate fechaActual[];

    public caja(int idHilo, int tipo, ArrayList<evento_controller>controladorEventos, int aux2[], LocalDate fechaActual[]){
        this.idHilo = idHilo;
        this.tipo = tipo;
        this.controladorEventos=controladorEventos;
        this.aux2 = aux2;
        this.fechaActual = fechaActual;
        if(tipo == 0){
            nombreHilo = "Caja " + idHilo;
        }
        else{
            nombreHilo = "Hilo Web " + idHilo;
        }

    }

    public boolean comprobarFechaAnterior(evento_controller evento, LocalDate fechaActual) {
        LocalDate fechaEvento = LocalDate.of(evento.eventoControlado.anho, evento.eventoControlado.mes, evento.eventoControlado.dia);
        return fechaActual.isBefore(fechaEvento);
    }
    public boolean comprobarDiaEvento(evento_controller evento, LocalDate fechaActual) {
        LocalDate fechaEvento = LocalDate.of(evento.eventoControlado.anho, evento.eventoControlado.mes, evento.eventoControlado.dia);
        return fechaActual.isEqual(fechaEvento);
    }

    public void setEvento(evento_controller evento){
        this.evento = evento;
    }
    @Override
    public void run() {
        int aux=0;
        
        while (true) {
            if(aux2[0]<5) {//30 interacciones diarias
                int sum=0;
                for(int i =0;i<controladorEventos.size();i++){
                    sum=sum+controladorEventos.get(i).eventoControlado.cantidadTickets;
                    if(sum!=0){
                        //break;
                    }
                }
                if(sum>0){
                    idEvento = (int) (Math.random() * (controladorEventos.size()));
                    if(comprobarFechaAnterior(controladorEventos.get(idEvento), fechaActual[0])){
                        if( (tipo == 0 && idHilo==1) || tipo==1){
                            setEvento(controladorEventos.get(idEvento));
                            evento.comprar(tipo, nombreHilo, new File(fechaActual[0].toString() + ".txt"));
                            System.out.println(nombreHilo + " " + evento.eventoControlado.nombre + ": " + evento.eventoControlado.cantidadTickets);
                            //aux++;
                        }
                    }
                    else if (comprobarDiaEvento(controladorEventos.get(idEvento), fechaActual[0])) {
                        setEvento(controladorEventos.get(idEvento));
                        evento.comprar(tipo, nombreHilo, new File(fechaActual[0].toString() + ".txt"));
                        System.out.println(nombreHilo + " " + evento.eventoControlado.nombre + ": " + evento.eventoControlado.cantidadTickets);
                        //aux++;
                    }
                    else{
                        controladorEventos.remove(idEvento);
                    }
                    aux2[0]++;
                }else{
                    break;
                }
            }else{
                aux2[0]=0;
                fechaActual[0] = fechaActual[0].plusDays(1);
                System.out.println(fechaActual[0]);
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
