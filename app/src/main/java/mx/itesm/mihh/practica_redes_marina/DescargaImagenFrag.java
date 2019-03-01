package mx.itesm.mihh.practica_redes_marina;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescargaImagenFrag extends Fragment {

    private ImageView imgFoto;
    private ProgressDialog esperando;

    public DescargaImagenFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_descarga_imagen, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        imgFoto = getView().findViewById(R.id.imgFoto);
        AndroidNetworking.initialize(getContext());

    }

    @Override
    public void onResume() {
        super.onResume();

        mostrarCuadroEsperando();
        String url = "https://upload.wikimedia.org/wikipedia/commons/d/dd/Big_%26_Small_Pumkins.JPG";
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imgFoto.setImageBitmap(response);
                        esperando.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "Error: " +  anError.getErrorCode(), Toast.LENGTH_SHORT).show();
                        esperando.dismiss();
                    }
                });
    }

    private void mostrarCuadroEsperando() {
        this.esperando = new ProgressDialog(getContext());
        esperando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        esperando.setIndeterminate(true);
        esperando.setMessage("Descargando...");
        esperando.setCanceledOnTouchOutside(false);
        esperando.show();
    }
}
