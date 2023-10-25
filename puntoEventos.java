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

        


    }
}