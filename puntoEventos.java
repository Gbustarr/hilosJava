import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDate;

class puntoEventos {
    public static void main(String[] args) {

        ArrayList<info_evento> eventos = new ArrayList<info_evento>();
        
        reader r = new reader();
        // Lectura de archivo eventos.txt
        r.leer();
        // debug de eventos.txt
        //r.printEventos();

        //Guardando el arraylist generado por el reader
        eventos = r.getEventos();
        ArrayList<evento_controller> controladorEventos = new ArrayList<>();
        for(int i=0; i<eventos.size(); i++){
            System.out.println(eventos.get(i));
            evento_controller e = new evento_controller(i+"", eventos.get(i));
            controladorEventos.add(e);
        }
        LocalDate fechaActual[] = {LocalDate.now()};
        int aux2[]={0};
        //Hilos caja presencial
        caja caja1 = new caja(1, 0, controladorEventos, aux2, fechaActual);
        caja caja2 = new caja(2, 0,controladorEventos, aux2, fechaActual);

        //Hilos web
        caja web1 = new caja(1, 1, controladorEventos, aux2, fechaActual);
        caja web2 = new caja(2, 1,controladorEventos, aux2, fechaActual);
        caja web3 = new caja(3, 1,controladorEventos, aux2, fechaActual);
        caja web4 = new caja(4, 1,controladorEventos, aux2, fechaActual);

        caja1.start();
        caja2.start();
        web1.start();
        web2.start();
        web3.start();
        web4.start();
    }
}