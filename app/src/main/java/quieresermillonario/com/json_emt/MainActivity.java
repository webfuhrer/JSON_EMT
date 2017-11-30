package quieresermillonario.com.json_emt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*User: WEB.SERV.ataraxa@hotmail.com
Key: 83D88CD0-8A9B-4CE6-B976-B922B61FAE6D        */


        String url = "https://openbus.emtmadrid.es:9443/emt-proxy-server/last/geo/GetArriveStop.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        //Creo el oyente para esperar la respuesta de la petición hecha.
        Response.Listener oyente = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("Respuesta", response);
                tratarJSON(response);
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
                mapa.put("idClient", "WEB.SERV.ataraxa@hotmail.com");
                mapa.put("passKey", "83D88CD0-8A9B-4CE6-B976-B922B61FAE6D");
                mapa.put("idStop", "3811");
                return mapa;
            }
        };


        queue.add(sr);
    }
    protected void tratarJSON(String json)
    {
        try {
            JSONObject json_objeto=new JSONObject(json);

            JSONArray lista_arrives=json_objeto.getJSONArray("arrives");
            for (int i=0; i<lista_arrives.length(); i++) {
                JSONObject objeto_parada = lista_arrives.getJSONObject(i);
                //JSONObject objeto_linea=objeto_parada.getJSONObject("lineId");
                Log.v("Paradas: ", objeto_parada.getString("lineId"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}