//package com.example.ash;
//
//import android.content.Context;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class getRequest {
//    private RequestQueue queue;
//    private String APIkey = "[API Key]";
//    private String brainID = "[Brain id]";
//    private String reply;
//    private char[] illegalChars = {'#', '<', '>', '$', '+', '%', '!', '`', '&',
//            '*', '\'', '\"', '|', '{', '}', '/', '\\', ':', '@'};
//
//    public getRequest(Context context){
//        queue = Volley.newRequestQueue(context);
//    }
//
//    public interface VolleyResponseListener {
//        void onError(String message);
//
//        void onResponse(String reply);
//    }
//
//    private String formatMessage(String message){
//
//        message = message.replace(' ', '-');
//        for(char illegalChar : illegalChars){
//            message = message.replace(illegalChar, '-');
//        }
//        return message;
//    }
//
//    public void getResponse(String message, final VolleyResponseListener volleyResponseListener){
//        message = formatMessage(message);
//        String url = "http://api.brainshop.ai/get?bid=" + brainID+ "&key=" + APIkey + "&uid=1&msg=" + message;
//        Log.d("URL", url);
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>(){
//                    @Override
//                    public void onResponse(JSONObject response){
//                        try {
//                            reply = response.getString("cnt");
//                            Log.d("RESPONSE", reply);
//                            volleyResponseListener.onResponse(reply);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            volleyResponseListener.onError("JSON Exception");
//                        }
//
//                    }
//                },
//                new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error){
//                        error.printStackTrace();
//                        volleyResponseListener.onError("Volley Error");
//                    }
//                });
//        queue.add(request);
//    }
//}

//
//package com.example.ash;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class OpenAIChat {
//    private RequestQueue queue;
//    private String apiKey = "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";  // Replace with your OpenAI API key
//    private String model = "gpt-3.5-turbo";  // Choose the appropriate model for your use case
//    private String baseUrl = "https://api.openai.com/v1/engines/" + model + "/completions";
//
//    public OpenAIChat(Context context) {
//        queue = Volley.newRequestQueue(context);
//    }
//
//    public interface VolleyResponseListener {
//        void onError(String message);
//
//        void onResponse(String reply);
//    }
//
//    public void getResponse(String message, final VolleyResponseListener volleyResponseListener) {
//        String prompt = "Answer to following query as a doctor: " + message ;  // Set up the prompt for the chat model
//        Log.d("Prompt" , prompt);
//        JSONObject jsonRequest = new JSONObject();
//        try {
//            jsonRequest.put("prompt", prompt);
//            jsonRequest.put("max_tokens", 50);  // Adjust the token limit as needed
//        } catch (JSONException e) {
//            e.printStackTrace();
//            volleyResponseListener.onError("JSON Exception");
//            return;
//        }
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, baseUrl, jsonRequest,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String choices = response.getJSONArray("choices").getJSONObject(0).getString("text");
//                            Log.d("RESPONSE", choices);
//                            volleyResponseListener.onResponse(choices);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            volleyResponseListener.onError("JSON Exception");
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        volleyResponseListener.onError("Volley Error");
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer " + apiKey);
//                return headers;
//            }
//        };
//        queue.add(request);
//    }
//}

//


package com.example.binarycoders;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OpenAIChat {
    private RequestQueue queue;
    private String apiKey = "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";  // Replace with your OpenAI API key
    private String baseUrl = "https://api.openai.com/v1/completions";

    public OpenAIChat(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String reply);
    }

    public  String extractContent(String input) {
        int contentIndex = input.indexOf("\"text\":\"");
        if (contentIndex != -1) {
            int startIndex = contentIndex + "\"text\":\"".length();
            int endIndex = input.indexOf("\"", startIndex);
            if (endIndex != -1) {
                return input.substring(startIndex, endIndex);
            }
        }
        return null;
    }

    public void getResponse(String userMessage, final VolleyResponseListener volleyResponseListener) {
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("model", "text-davinci-003");
            jsonRequest.put("prompt", userMessage);
            jsonRequest.put("max_tokens", 20);
            jsonRequest.put("temperature", 0);
        } catch (JSONException e) {
            e.printStackTrace();
            volleyResponseListener.onError("JSON Exception");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, baseUrl, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String reply = extractContent(response.getString("choices"));
                            reply = reply.replace(reply.substring(0,2) , "  ");
                            Log.d("RESPONSE", reply.replace('\n' , ' '));
                            volleyResponseListener.onResponse(reply);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            volleyResponseListener.onError("JSON Exception");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        volleyResponseListener.onError("Volley Error");
                        volleyResponseListener.onResponse("Please Try Again");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + apiKey);
                return headers;
            }
        };
        queue.add(request);
    }
}
