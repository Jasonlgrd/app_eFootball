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

/**
 * Created by jasonhnovic on 10/05/2018.
 */

public class RegisterRequest {

    private Context context;
    private RequestQueue queue;

    public RegisterRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void register(final String nom, final String prenom, final String pseudo, final String email, final String password, final String password2, final RegisterRequest.RegisterCallBack callback) {

        String url = "http://jasonlagarde.fr/app_efootball/register.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Map<String, String> errors = new HashMap<>();

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error) {
                        callback.onSuccess("Vous êtes bien inscrit");
                    }else{
                        JSONObject messages = json.getJSONObject("message");
                        if (messages.has("nom")){
                            errors.put("nom", messages.getString("nom"));
                        }
                        if (messages.has("prenom")){
                            errors.put("prenom", messages.getString("prenom"));
                        }
                        if (messages.has("pseudo")){
                            errors.put("pseudo", messages.getString("pseudo"));
                        }
                        if (messages.has("email")){
                            errors.put("email", messages.getString("email"));
                        }
                        if (messages.has("password")){
                            errors.put("password", messages.getString("password"));
                        }
                        callback.inputErrors(errors);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }else if(error instanceof VolleyError){
                    callback.onError("Une erreur s'est produite");
                }

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("nom", nom);
                map.put("prenom", prenom);
                map.put("pseudo", pseudo);
                map.put("email", email);
                map.put("password", password);
                map.put("password2", password2);

                return map;
            }
        };

        queue.add(request);

    }

    public interface RegisterCallBack{
        void onSuccess(String message);
        void inputErrors(Map<String, String> errors);
        void onError(String message);
    }

}
