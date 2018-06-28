package org.vortex.vortexchat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.vortex.vortexchat.R;
import org.vortex.vortexchat.util.VolleySingleton;

public class SuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Bundle bundle = getIntent().getExtras();

        String auth = bundle.getString("my_token", "");
        String number = bundle.getString("number", "");

        TextView authTextView = findViewById(R.id.authTextView);
        TextView numberTextView = findViewById(R.id.numberTextView);
        authTextView.setText(auth);
        numberTextView.setText(number);

        JSONObject addUserJSONObject = new JSONObject();
        try {
            addUserJSONObject.put("auth_id", auth);
            addUserJSONObject.put("phone_number",number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Connecting to my home machine IP
        String url = "http://192.168.1.4:8082/api/register";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, addUserJSONObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("API", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


}
