package com.kaidoh.mayuukhvarshney.orderman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mayuukhvarshney on 09/06/16.
 */


public class LoginActivity extends AppCompatActivity {
    EditText UserName,Password;
    Button LoginButton;
    String username_text,password_text;
    JSONObject LoginDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
LoginDetails = new JSONObject();
        UserName= (EditText) findViewById(R.id.input_username);
        Password = (EditText) findViewById(R.id.input_password);
        LoginButton= (Button) findViewById(R.id.btn_login);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_text=UserName.getText().toString();
                password_text=Password.getText().toString();
                try{
                    LoginDetails.put("username",username_text);
                    LoginDetails.put("password",password_text);
                    new Authorize().execute();
                }
                catch(JSONException e){
                    e.printStackTrace();
                }


            }
        });


    }
    class Authorize extends AsyncTask<Void,Void,JSONArray>{

        @Override
        protected JSONArray doInBackground(Void... params) {
            String URL = Config.URL+"login/postuser";
            String json = LoginDetails.toString();
            String content = HttpManager.PostObject(URL,json);
            SharedPreferences datastore= getSharedPreferences("DataStorage", 0);
            SharedPreferences.Editor editor=datastore.edit();
           try{
               JSONObject obj = new JSONObject(content);
               JSONArray array;
               array = obj.getJSONArray("users");


               return array;
           }
           catch (JSONException e){
               e.printStackTrace();
               return null;
           }

        }
        @Override
        protected void onPostExecute(JSONArray array)
        {

            SharedPreferences datastore= getSharedPreferences("DataStorage", 0);
            SharedPreferences.Editor editor=datastore.edit();
            if(array!=null)
            {
                try {
                    JSONObject object = null;
                    object = array.getJSONObject(0);
                    editor.putString("UserId", object.getString("userid"));
                    Intent intent = new Intent(LoginActivity.this,MainMenu.class);
                    startActivity(intent);
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(LoginActivity.this,"Unauthorized Login Attempt",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
