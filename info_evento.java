public class info_evento {
    public String fecha;
    public String nombre;
    public int cantidadTickets;
    public int veintePorciento = 0; // Para web

    public info_evento(String fecha, String nombre, int cantidadTickets) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.cantidadTickets = cantidadTickets;

        double aux = (20 / 100) * cantidadTickets; // Calculando el 20%
        this.veintePorciento = (int) aux; //
    }

    public int getCantidadTickets() {
        return cantidadTickets;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean comprar(int tickets) {
        if (cantidadTickets - tickets < 0) { // No hay suficientes tickets disponibles para comprar
            return false;
        } else { // Compra exitosa
            this.cantidadTickets = cantidadTickets - tickets;
            return true;
        }

    }

    public boolean hayTickets() {
        if (this.cantidadTickets != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esComprableWeb(int ntickets) {
        if ((cantidadTickets - ntickets) >= veintePorciento) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Fecha: " + fecha + " Nombre: " + nombre + " Cantidad de tickets: " + cantidadTickets;
    }
}
