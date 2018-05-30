package fr.jasonlagarde.e_football;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuLogActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView ML_title;
    private Button btn_profile, btn_licence, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_log);
        this.setTitle("Menu");

        ML_title = (TextView) findViewById(R.id.ML_title);
        btn_profile = (Button) findViewById(R.id.btn_profile);
        btn_licence = (Button) findViewById(R.id.btn_licence);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        sessionManager = new SessionManager(this);
        if (sessionManager.isLogged()){
            String id = sessionManager.getId();
            String nom = sessionManager.getNom();
            String prenom = sessionManager.getPrenom();
            String pseudo = sessionManager.getPseudo();
            String email = sessionManager.getEmail();
            ML_title.setText("Bonjour " + nom + " " + prenom);
        }


        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LicenceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
