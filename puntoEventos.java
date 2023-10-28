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
        caja caja2 = new caja(2, 0);

        //Hilos web
        caja web1 = new caja(1, 1);
        caja web2 = new caja(2, 1);
        caja web3 = new caja(3, 1);
        caja web4 = new caja(4, 1);

        int idEvento;
        
        caja1.start();
        idEvento = (int)(Math.random()*(controladorEventos.size()-1));
        caja1.setEvento(controladorEventos.get(idEvento));
        //idEvento = (int)(Math.random()*(controladorEventos.size()-1));
        caja2.setEvento(controladorEventos.get(idEvento));
        caja2.start();

        
        

        


    }
}