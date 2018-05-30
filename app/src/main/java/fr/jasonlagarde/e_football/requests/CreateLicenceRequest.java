package fr.jasonlagarde.e_football.requests;

import android.content.Context;
import android.util.Log;

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
import java.util.Iterator;
import java.util.Map;
import java.util.SimpleTimeZone;

import fr.jasonlagarde.e_football.User;

/**
 * Created by jasonhnovic on 11/05/2018.
 */

public class CreateLicenceRequest {

    private Context context;
    private RequestQueue queue;

    public CreateLicenceRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void departements(final CreateLicenceRequest.CreateLicenceDepCallBack callback){

        String url = "http://jasonlagarde.fr/app_efootball/departements.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject json = new JSONObject(response);
                    callback.onSuccess(json);

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

                return map;
            }
        };

        queue.add(request);
    }

    public interface CreateLicenceDepCallBack{
        void onSuccess(JSONObject json);
        void onError(String message);
    }

    public void clubs(final String id_dep,final CreateLicenceRequest.CreateLicenceClubCallBack callback){

        String url = "http://jasonlagarde.fr/app_efootball/club.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject json = new JSONObject(response);
                    callback.onSuccess(json);

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
                map.put("dep", id_dep);

                return map;
            }
        };

        queue.add(request);
    }

    public interface CreateLicenceClubCallBack{
        void onSuccess(JSONObject json);
        void onError(String message);
    }

    public void createLicence(final String user_id, final String club_nom, final CreateLicenceRequest.CreateLicenceCallBack callback){

        String url = "http://jasonlagarde.fr/app_efootball/createLicence.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                callback.onSuccess(response);

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
                map.put("user_id", user_id);
                map.put("club_nom", club_nom);

                return map;
            }
        };

        queue.add(request);
    }

    public interface CreateLicenceCallBack{
        void onSuccess(String message);
        void onError(String message);
    }

}
