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
import java.util.Map;

import fr.jasonlagarde.e_football.Club;
import fr.jasonlagarde.e_football.Departement;
import fr.jasonlagarde.e_football.Licence;

/**
 * Created by jasonhnovic on 11/05/2018.
 */

public class LicenceRequest {

    private Context context;
    private RequestQueue queue;

    public LicenceRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void Licence(final String id, final LicenceRequest.LicenceCallBack callback){

        String url = "http://jasonlagarde.fr/app_efootball/licence.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    Boolean licence = json.getBoolean("licence");

                    if (!error){
                        if (licence){
                            String lice_id = json.getString("lice_id");
                            String lice_id_user = json.getString("lice_id_user");
                            String lice_id_club = json.getString("lice_id_club");
                            String lice_date_create = json.getString("lice_date_create");
                            String club_id = json.getString("club_id");
                            String club_nom = json.getString("club_nom");
                            String club_ville = json.getString("club_ville");
                            String club_departement = json.getString("club_departement");
                            String dep_id = json.getString("dep_id");
                            String dep_nom = json.getString("dep_nom");

                            Licence licence1 = new Licence(lice_id, lice_id_user, lice_id_club, lice_date_create);
                            Club club = new Club(club_id, club_nom, club_ville,club_departement);
                            Departement departement = new Departement(dep_id, dep_nom);
                            callback.onSuccess(licence1, club, departement);
                        }else{
                            callback.onLicence();
                        }
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
        }){@Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id", id);

                return map;
            }
        };

        queue.add(request);
    }

    public interface LicenceCallBack{
        void onSuccess(Licence licence, Club club, Departement departement);
        void onLicence();
        void onError(String message);
    }


}
