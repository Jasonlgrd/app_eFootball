package fr.jasonlagarde.e_football;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import fr.jasonlagarde.e_football.requests.LicenceRequest;
import fr.jasonlagarde.e_football.requests.ProfileRequest;

public class ProfileActivity extends AppCompatActivity {

    private TextView profile_nom, profile_prenom, profile_pseudo, profile_email, profile_identifiat;
    private Button btn_retour;
    private RequestQueue queue;
    private ProfileRequest request;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.setTitle("Profil");

        profile_nom = findViewById(R.id.profile_nom);
        profile_prenom = findViewById(R.id.profile_prenom);
        profile_pseudo = findViewById(R.id.profile_pseudo);
        profile_email = findViewById(R.id.profile_email);
        profile_identifiat = findViewById(R.id.profile_identifiant);
        btn_retour = findViewById(R.id.btn_retour);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new ProfileRequest(this, queue);
        sessionManager = new SessionManager(this);

        request.profile(sessionManager.getId(), new ProfileRequest.ProfileCallBack() {
            @Override
            public void onSuccess(User user) {
                profile_identifiat.setText("Identifiant : " + user.getId());
                profile_nom.setText("Nom : " + user.getNom());
                profile_prenom.setText("Prénom : " + user.getPrenom());
                profile_pseudo.setText("Pseudo : " + user.getPseudo());
                profile_email.setText("Email : " + user.getEmail());

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
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
