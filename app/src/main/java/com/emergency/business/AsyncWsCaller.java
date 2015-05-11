package com.emergency.business;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.widget.DatePicker;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by elmehdiharabida on 21/04/2015.
 */
public class AsyncWsCaller<Parameter,Return> extends AsyncTask<Void, Void, Return> {

    private OnTaskCompleted<Return> listener;
    Parameter parameterIn;
    Class returnClassType;
    String url;




        public AsyncWsCaller(OnTaskCompleted listener, Parameter parameterIn, Class returnClassType, String url){
        this.listener=listener;
        this.parameterIn = parameterIn;
        this.returnClassType = returnClassType;
        this.url = url;
    }

    @Override
    protected Return doInBackground(Void... params) {
        RestTemplate restTemplate = new RestTemplate();
        Return manageUserOut = null;
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        try{
        manageUserOut = (Return) restTemplate.postForObject(url, parameterIn,returnClassType);
        }catch(Exception e){
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
        return manageUserOut;
    }



    @Override
    protected void onPostExecute(Return parameterOut) {
        listener.onTaskCompleted(parameterOut);
    }
}
