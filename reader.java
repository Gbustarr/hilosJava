// Esta clase cumple la función de leer el archivo de eventos
// para crear un arraylist de eventos

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class reader {
    ArrayList<info_evento> eventos = new ArrayList<info_evento>();

    public void leer() {
        // Lectura de archivo

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("./eventos.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;

            Pattern patron = Pattern.compile("(\\d+)-(\\d+)-(\\d+) - ([^-]+) - (\\d+)");
            while ((linea = br.readLine()) != null) {
                Matcher matcher = patron.matcher(linea);
                if (matcher.find()) {
                    int dia = Integer.parseInt(matcher.group(1));
                    int mes = Integer.parseInt(matcher.group(2));
                    int anho = Integer.parseInt(matcher.group(3));
                    String nombre = matcher.group(4);
                    int cantidad = Integer.parseInt(matcher.group(5));
                    info_evento evento = new info_evento(anho,mes,dia, nombre, cantidad);
                    eventos.add(evento);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public ArrayList<info_evento> getEventos(){
        return this.eventos;
    }

    public void printEventos(){
        for (info_evento info_evento : eventos) {
            System.out.println(info_evento);
        }
    }

}
