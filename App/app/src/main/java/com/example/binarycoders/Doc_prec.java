package com.example.binarycoders;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Doc_prec extends AppCompatActivity {

    private final String TAG = "Doc_prec"; // Tag for logging

    private EditText userInputEditText;
    private Button consultButton;

    private TextView assistantReplyTextView;

    private final String OPENAI_API_KEY = "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";
    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_prec);

        userInputEditText = findViewById(R.id.userInputEditText);
        consultButton = findViewById(R.id.consultButton);
        assistantReplyTextView = findViewById(R.id.assistantReplyTextView);

        consultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = userInputEditText.getText().toString();
                Log.d(TAG, "User Input: " + userInput); // Logging user input
                long startTime = System.currentTimeMillis(); // Start time of the request
                consultWithOpenAI(userInput);
                long endTime = System.currentTimeMillis(); // End time of the request
                long duration = endTime - startTime; // Duration of the request in milliseconds
                Log.d(TAG, "Request duration: " + duration + "ms");
            }
        });
    }

    private void consultWithOpenAI(String userInput) {
        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("model", "gpt-3.5-turbo");

            JSONArray messages = new JSONArray();
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a Doctor.");
            messages.put(systemMessage);

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", userInput);
            messages.put(userMessage);

            jsonRequest.put("messages", messages);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, OPENAI_API_URL, jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray choices = response.getJSONArray("choices");
                                JSONObject completion = choices.getJSONObject(0);
                                String assistantReply = completion.getJSONObject("message").getString("content");

                                // Handle the assistant's reply (e.g., display in a TextView)
                                // For simplicity, let's assume you have a TextView named assistantReplyTextView
                                 assistantReplyTextView.setText(assistantReply);
                                Log.d(TAG, "Assistant Reply: " + assistantReply);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Doc_prec.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d( "Error" , error.toString());
                            error.printStackTrace();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + OPENAI_API_KEY);
                    return headers;
                }
            };

            int MY_TIMEOUT_MS = 100000; // 10 seconds
            request.setRetryPolicy(new DefaultRetryPolicy(MY_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            Volley.newRequestQueue(this).add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
