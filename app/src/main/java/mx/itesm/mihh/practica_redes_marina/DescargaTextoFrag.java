package mx.itesm.mihh.practica_redes_marina;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescargaTextoFrag extends Fragment {

    private TextView tvContenido;


    public DescargaTextoFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_descarga_texto, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        tvContenido = getView().findViewById(R.id.tvContenido);
        AndroidNetworking.initialize(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        //Descarga

        String url = "https://www.googleapis.com/books/v1/volumes?q=title:android";

        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arrLibros = response.getJSONArray("items");
                            for (int i=0; i<arrLibros.length(); i++){
                                JSONObject dLibro = arrLibros.getJSONObject(i);
                                JSONObject info = dLibro.getJSONObject("volumeInfo");
                                String titulo = info.getString("title");
                                tvContenido.append(titulo + "\n\n");
                                String urlImg = info.getJSONObject("imageLinks").getString("thumbnail");
                                tvContenido.append(urlImg + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


        /*
        AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                tvContenido.setText(response);
            }

            @Override

            public void onError(ANError anError) {
                tvContenido.setText("Error en la descarga " + anError.getErrorCode());
            }
        });
        */
    }
}
