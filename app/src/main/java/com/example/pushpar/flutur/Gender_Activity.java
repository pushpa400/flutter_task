package com.example.pushpar.flutur;


import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Gender_Activity extends AppCompatActivity {
    ProgressDialog pd;
    MediaPlayer mplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gender_main);

        start_playing();
        final EditText name_field = findViewById(R.id.name_field);
        Button go_button = findViewById(R.id.go_button);
        final TextView result = findViewById(R.id.result);
        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                String name = name_field.getText().toString();

                if(name.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter a name",Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "https://api.genderize.io/?name="+name;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    String gender = response.getString("gender");
                                    int probability = response.getInt("probability");
                                    int count = response.getInt("count");
                                    result.setText(gender.toUpperCase()+"\n"+"PROBABILITY: "+probability+"\n"+"count:"+count);
                                }catch (JSONException e){
                                    Toast.makeText(getApplicationContext(),"Failed to find! Try another",Toast.LENGTH_SHORT).show();
                                }

                                if(pd.isShowing()){
                                    pd.dismiss();
                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                if(pd.isShowing()){
                                    pd.dismiss();
                                }
                                Toast.makeText(getApplicationContext(),"Internet not available!",Toast.LENGTH_SHORT).show();
                            }
                        });
                pd = new ProgressDialog(Gender_Activity.this);
                pd.setTitle("Loading");
                pd.setMessage("Please wait..");
                pd.setCancelable(false);
                pd.show();
                Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);

            }
        });
    }
    public void start_playing()
    {

        if(mplayer!=null && mplayer.isPlaying()){
            mplayer.stop();
            mplayer.reset();
            mplayer.release();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                mplayer=MediaPlayer.create(Gender_Activity.this,R.raw.music);
                mplayer.start();
            }
        }).start();
    }
    public void stop_playing(){
        if(mplayer==null)
        {
            return;
        }
        if(mplayer.isPlaying()){
            mplayer.stop();
            mplayer.reset();
            mplayer.release();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        stop_playing();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(mplayer==null)
        {
            return;
        }

        start_playing();
    }
    /*@Override
    protected void onDestroy(){
        super.onDestroy();
        stop_playing();
    }*/
}


