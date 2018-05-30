package fr.jasonlagarde.e_football;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.jasonlagarde.e_football.requests.CreateLicenceRequest;
import fr.jasonlagarde.e_football.requests.LicenceRequest;

public class CreateLicenceActivity extends AppCompatActivity {

    private Button btn_retour, btn_send;
    private Spinner select_dep, select_club;
    private RequestQueue queue;
    private CreateLicenceRequest request;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_licence);
        this.setTitle("Création d'une licence");

        btn_retour = (Button) findViewById(R.id.btn_retour);
        btn_send = (Button) findViewById(R.id.btn_send);
        select_dep = (Spinner) findViewById(R.id.select_dep);
        select_club = (Spinner) findViewById(R.id.select_club);

        sessionManager = new SessionManager(this);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new CreateLicenceRequest(this, queue);

        final List departements = new ArrayList();
        final List clubs = new ArrayList();

        final ArrayAdapter adapterDepartements = new ArrayAdapter(this, android.R.layout.simple_spinner_item, departements);
        final ArrayAdapter adapterClubs = new ArrayAdapter(this, android.R.layout.simple_spinner_item, clubs);

        adapterDepartements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterClubs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        request.departements(new CreateLicenceRequest.CreateLicenceDepCallBack() {
            @Override
            public void onSuccess(JSONObject json) {
                try{
                    for (Iterator iterator = json.keys(); iterator.hasNext();) {
                        Object cle = iterator.next();
                        Object val = json.get(String.valueOf(cle));
                        departements.add(val);
                        select_dep.setAdapter(adapterDepartements);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        select_dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String dep = select_dep.getSelectedItem().toString();
                request.clubs(dep, new CreateLicenceRequest.CreateLicenceClubCallBack() {
                    @Override
                    public void onSuccess(JSONObject json) {
                        try{
                            clubs.clear();
                            for (Iterator iterator = json.keys(); iterator.hasNext();) {
                                Object cle = iterator.next();
                                Object val = json.get(String.valueOf(cle));
                                clubs.add(val);
                                select_club.setAdapter(adapterClubs);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.createLicence(sessionManager.getId(), select_club.getSelectedItem().toString(), new CreateLicenceRequest.CreateLicenceCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LicenceActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuLogActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
