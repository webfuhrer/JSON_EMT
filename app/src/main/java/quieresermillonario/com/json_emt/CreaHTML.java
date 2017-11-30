package quieresermillonario.com.json_emt;

import java.util.List;

/**
 * Created by luis on 30/11/2017.
 */

class CreaHTML {

    public static String crearTabla(List<ObjetoLinea> lista_lineas) {
        String html_tabla="<table><tr><th>Línea</th><th>Destino</th><th>Tiempo de llegada</th></tr>";
        for (ObjetoLinea linea: lista_lineas)
        {
            html_tabla+="<tr><td>"+linea.getId_linea()+"</td><td>"+linea.getDestino()+"</td><td>"+linea.getTiempo()+"</td></tr>";
        }
        html_tabla+="</table>";
        return html_tabla;
    }
}
