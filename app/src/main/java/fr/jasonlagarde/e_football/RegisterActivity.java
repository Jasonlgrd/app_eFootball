package fr.jasonlagarde.e_football;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.Map;

import fr.jasonlagarde.e_football.requests.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_send;
    private EditText et_nom, et_prenom, et_pseudo, et_email, et_password, et_password2;
    private ProgressBar pb_loader;
    private RequestQueue queue;
    private RegisterRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("Inscription");

        btn_send = (Button) findViewById(R.id.btn_send);
        pb_loader = (ProgressBar) findViewById(R.id.pb_loader);
        et_nom = (EditText) findViewById(R.id.et_nom);
        et_prenom = (EditText) findViewById(R.id.et_prenom);
        et_pseudo = (EditText) findViewById(R.id.et_pseudo);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password2 = (EditText) findViewById(R.id.et_password2);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new RegisterRequest(this, queue);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb_loader.setVisibility(View.VISIBLE);
                String nom = et_nom.getText().toString().trim();
                String prenom = et_prenom.getText().toString().trim();
                String pseudo = et_pseudo.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String password2 = et_password2.getText().toString().trim();
                if (nom.length() > 0 && prenom.length() > 0 && pseudo.length() > 0 && email.length() > 0 && password.length() > 0 && password2.length() > 0){

                    request.register(nom, prenom, pseudo, email, password, password2, new RegisterRequest.RegisterCallBack() {
                        @Override
                        public void onSuccess(String message) {
                            pb_loader.setVisibility(View.GONE);
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("REGISTER", message);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void inputErrors(Map<String, String> errors) {
                            pb_loader.setVisibility(View.GONE);
                            if (errors.get("nom") != null){
                                Toast.makeText(getApplicationContext(), errors.get("nom"), Toast.LENGTH_LONG).show();
                            }
                            if (errors.get("prenom") != null){
                                Toast.makeText(getApplicationContext(), errors.get("prenom"), Toast.LENGTH_LONG).show();
                            }
                            if (errors.get("pseudo") != null){
                                Toast.makeText(getApplicationContext(), errors.get("pseudo"), Toast.LENGTH_LONG).show();
                            }
                            if (errors.get("email") != null){
                                Toast.makeText(getApplicationContext(), errors.get("email"), Toast.LENGTH_LONG).show();
                            }
                            if (errors.get("password") != null){
                                Toast.makeText(getApplicationContext(), errors.get("password"), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError(String message) {
                            pb_loader.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    pb_loader.setVisibility(View.GONE);
                }
            }
        });
    }
}
