package quieresermillonario.com.json_emt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    WebView web_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         web_view=(WebView) findViewById(R.id.web_view);


        String url = "https://openbus.emtmadrid.es:9443/emt-proxy-server/last/geo/GetArriveStop.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        //Creo el oyente para esperar la respuesta de la petición hecha.
        Response.Listener oyente = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List lista_lineas=tratarJSON(response);
                String html_tabla=CreaHTML.crearTabla(lista_lineas);
                web_view.loadData(html_tabla, "text/html", "UTF-8");
            }
        };
        //Creo el oyente para esperar la respuesta de la petición hecha (en caso de error)
        Response.ErrorListener oyente_fallo = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Error", error.getMessage());
            }
        };


        //Fabrico una petición que será por POST, a url y cuya respuesta se recibirá en oyente o en oyente_fallo en caso de error
        StringRequest sr = new StringRequest(Request.Method.POST, url, oyente, oyente_fallo) {
            //Sobreescribo getParams() rellenando un Map con los datos que necesita el método GetArriveStop.php
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map mapa = new HashMap();
                //Las variables de la clase Claves pueden obtenerse en Opendata EMT
                mapa.put("idClient", Claves.usr);
                mapa.put("passKey", Claves.pass);
                mapa.put("idStop", "3811");
                return mapa;
            }
        };


        queue.add(sr);
    }
    /*****************************************************************************/

    protected List tratarJSON(String json)
    {
        List<ObjetoLinea> lista_lineas=new ArrayList();
        try {
            JSONObject json_objeto=new JSONObject(json);

            JSONArray lista_arrives=json_objeto.getJSONArray("arrives");
            for (int i=0; i<lista_arrives.length(); i++) {
                JSONObject objeto_parada = lista_arrives.getJSONObject(i);
                //JSONObject objeto_linea=objeto_parada.getJSONObject("lineId");
                String id=objeto_parada.getString("lineId");
                int tiempo_llegada= objeto_parada.getInt("busTimeLeft");
                String destino= objeto_parada.getString("destination");
                ObjetoLinea linea=new ObjetoLinea(id, destino, tiempo_llegada);
                lista_lineas.add(linea);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista_lineas;
    }
}