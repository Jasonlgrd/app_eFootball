package fr.jasonlagarde.e_football;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import fr.jasonlagarde.e_football.requests.LicenceRequest;

public class LicenceActivity extends AppCompatActivity {

    private TextView licence_id, licence_date, club_id, club_nom, club_ville, dep_id, dep_nom;
    private Button btn_retour;
    private RequestQueue queue;
    private LicenceRequest request;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);
        this.setTitle("Licence");

        licence_id = (TextView) findViewById(R.id.licence_id);
        licence_date = (TextView) findViewById(R.id.licence_date);
        club_id = (TextView) findViewById(R.id.club_id);
        club_nom = (TextView) findViewById(R.id.club_nom);
        club_ville = (TextView) findViewById(R.id.club_ville);
        dep_id = (TextView) findViewById(R.id.dep_id);
        dep_nom = (TextView) findViewById(R.id.dep_nom);
        btn_retour = (Button) findViewById(R.id.btn_retour);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new LicenceRequest(this, queue);
        sessionManager = new SessionManager(this);

        request.Licence(sessionManager.getId(), new LicenceRequest.LicenceCallBack() {
            @Override
            public void onSuccess(Licence licence, Club club, Departement departement) {
                licence_id.setText("Numéro : " + licence.getId());
                licence_date.setText("Date de création : " + licence.getDate_create());
                club_id.setText("Numéro : " + club.getId());
                club_nom.setText("Nom : " + club.getNom());
                club_ville.setText("Ville : " + club.getVille());
                dep_id.setText("Numéro : " + departement.getId());
                dep_nom.setText("Département : " + departement.getNom());

                btn_retour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MenuLogActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onLicence(){
                Intent intent = new Intent(getApplicationContext(), CreateLicenceActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
