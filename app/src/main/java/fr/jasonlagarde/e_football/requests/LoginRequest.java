package fr.jasonlagarde.e_football.requests;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.jasonlagarde.e_football.User;

/**
 * Created by jasonhnovic on 10/05/2018.
 */

public class LoginRequest {

    private Context context;
    private RequestQueue queue;

    public LoginRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void connection(final String pseudo, final String password, final LoginRequest.LoginCallBack callback){

        String url = "http://jasonlagarde.fr/app_efootball/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        String id = json.getString("id");
                        String nom = json.getString("nom");
                        String prenom = json.getString("prenom");
                        String pseudo = json.getString("pseudo");
                        String email = json.getString("email");
                        User user = new User(id, nom, prenom, pseudo, email);
                        callback.onSuccess(user);
                    }else{
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    callback.onError("Une erreur s'est produite");
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){
                    callback.onError("Impossible de se conecter");
                }else if(error instanceof VolleyError){
                    callback.onError("Une erreur s'est produite");
                }

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("pseudo", pseudo);
                map.put("password", password);

                return map;
            }
        };

        queue.add(request);
    }

    public interface LoginCallBack{
        void onSuccess(User user);
        void onError(String message);
    }
}
