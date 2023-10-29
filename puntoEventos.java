import java.io.File;
import java.util.ArrayList;

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
            evento_controller e = new evento_controller(i+"", eventos.get(i));
            controladorEventos.add(e);
        }

        //Hilos caja presencial
        caja caja1 = new caja(1, 0, controladorEventos);
        caja caja2 = new caja(2, 0,controladorEventos);

        //Hilos web
        caja web1 = new caja(1, 1, controladorEventos);
        caja web2 = new caja(2, 1,controladorEventos);
        caja web3 = new caja(3, 1,controladorEventos);
        caja web4 = new caja(4, 1,controladorEventos);

        caja1.start();
        caja2.start();
        web1.start();
        web2.start();
        web3.start();
        web4.start();
    }
}