import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// generadorDias es un iterador que mientras este en bucle genera clientes en un
// dia especifico. Tambien genera un archivo de entrada para que los hilos evento_controller
// los rellenen.
public class generadorDias {
    private int diaActual = 0;
    private int comprasRealizadas = 0;
    private ArrayList<String> transaccionesDelDia = new ArrayList<>();

    public void nuevoDia() {
        diaActual++;
        comprasRealizadas = 0;
        transaccionesDelDia.clear();
    }

    public int getDiaActual() {
        return diaActual;
    }

    public void registrarTransaccion(String transaccion) {
        transaccionesDelDia.add(transaccion);
        comprasRealizadas++;
    }

    public boolean diaFinalizado() {
        return comprasRealizadas >= 50;
    }

    public void crearArchivoDia() {
        String nombreArchivo = "Dia" + diaActual + ".txt";

        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String transaccion : transaccionesDelDia) {
                bufferedWriter.write(transaccion);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 }

