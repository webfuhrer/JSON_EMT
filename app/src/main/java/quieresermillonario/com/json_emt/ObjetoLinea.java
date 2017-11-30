package quieresermillonario.com.json_emt;

/**
 * Created by luis on 30/11/2017.
 */

public class ObjetoLinea {
    private String id_linea, destino;
    private int tiempo;

    public ObjetoLinea(String id_linea, String destino, int tiempo) {
        this.id_linea = id_linea;
        this.destino = destino;
        this.tiempo = tiempo;
    }

    public String getId_linea() {
        return id_linea;
    }

    public String getDestino() {
        return destino;
    }

    public int getTiempo() {
        return tiempo;
    }
}
