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
            evento_controller e = new evento_controller(i+"", eventos.get(i), new File("27-10.txt"));
            controladorEventos.add(e);
        }

        //Hilos caja presencial
        caja caja1 = new caja(1, 0);
        caja1.setEvento(controladorEventos.get(0));
        caja1.start();

        


    }
}