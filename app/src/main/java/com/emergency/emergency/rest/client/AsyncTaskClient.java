package com.emergency.emergency.rest.client;

import android.os.AsyncTask;

import com.emergency.emergency.dto.EmergencyDTO;
import com.emergency.emergency.util.EmergencyConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by elmehdiharabida on 19/04/2015.
 */
public class AsyncTaskClient<PARAMS extends EmergencyDTO> extends AsyncTask<PARAMS, Void, EmergencyDTO> {

    private URL urlToRequest = null;
    

    AsyncTaskClient(String path){
        try {
            urlToRequest = new URL(EmergencyConstants.EMERGENCY_WS_PROTOCOL,
                    EmergencyConstants.EMERGENCY_WS_HOST ,
                    EmergencyConstants.EMERGENCY_WS_PORT ,
                    path);
        } catch (MalformedURLException e) {
            /**
             * Ne doit jamais arriver
             */
            e.printStackTrace();
        }
    }


    @Override
    protected EmergencyDTO doInBackground(PARAMS... paramses) {
        HttpURLConnection urlConnection2 = null;
        EmergencyDTO returnedObject = null;

        try {

        /*
        Creer la connexion
         */
        urlConnection2 = (HttpURLConnection) urlToRequest.openConnection();
        urlConnection2.setDoOutput(true);
        urlConnection2.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        urlConnection2.setChunkedStreamingMode(0);
       // Gson gsonObject = new Gson();
            /*
            Creation du stream et ecriture de la requete sous format JSON
             */
            OutputStream out = new BufferedOutputStream(urlConnection2.getOutputStream());
           // out.write(gsonObject.toJson(paramses[0]).getBytes());
            out.flush();
            out.close();

            /*
            Lecture de la reponse du web service
             */
            InputStream in = new BufferedInputStream(urlConnection2.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            /*
            parsing du resultat en sortie du web service
             */
         //   returnedObject = gsonObject.fromJson(line, EmergencyDTO.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnedObject;
    }

    @Override
    protected void onPostExecute(EmergencyDTO returnedValue) {

        /*
        Recuperer l'objet en sortie pour reutilisation
         */

        super.onPostExecute(returnedValue);
    }
}
