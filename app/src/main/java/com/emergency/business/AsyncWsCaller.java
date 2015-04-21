package com.emergency.business;

import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

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
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Return manageUserOut = (Return) restTemplate.postForObject(url, parameterIn,returnClassType);
        return manageUserOut;
    }



    @Override
    protected void onPostExecute(Return parameterOut) {
        listener.onTaskCompleted(parameterOut);
    }
}
