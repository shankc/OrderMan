package com.kaidoh.mayuukhvarshney.orderman;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mayuukhvarshney on 02/06/16.
 */
public class HttpManager {

    public static String GetData(String URL){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }

    }
}
