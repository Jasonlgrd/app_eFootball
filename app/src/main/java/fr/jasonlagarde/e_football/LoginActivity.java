package fr.jasonlagarde.e_football;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import fr.jasonlagarde.e_football.requests.LoginRequest;

public class LoginActivity extends AppCompatActivity {

    private EditText et_pseudo_log, et_password_log;
    private Button btn_send;
    private RequestQueue queue;
    private LoginRequest request;
    private ProgressBar pb_loader;
    private Handler handler;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Connexion");

        //Récupération du message de l'inscription
        Intent intent = getIntent();
        if (intent.hasExtra("REGISTER")){
            Toast.makeText(this, intent.getStringExtra("REGISTER"), Toast.LENGTH_SHORT).show();
        }

        et_pseudo_log = (EditText) findViewById(R.id.et_pseudo_log);
        et_password_log = (EditText) findViewById(R.id.et_password_log);
        btn_send = (Button) findViewById(R.id.btn_send_log);
        pb_loader = (ProgressBar) findViewById(R.id.pb_loader);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new LoginRequest(this, queue);
        handler = new Handler();
        sessionManager = new SessionManager(this);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pseudo = et_pseudo_log.getText().toString().trim();
                final String password = et_password_log.getText().toString().trim();
                pb_loader.setVisibility(View.VISIBLE);
                if (pseudo.length() > 0 && password.length() > 0){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            request.connection(pseudo, password, new LoginRequest.LoginCallBack() {
                                @Override
                                public void onSuccess(User user) {
                                    pb_loader.setVisibility(View.GONE);
                                    sessionManager.insertUer(user);
                                    Intent intent = new Intent(getApplicationContext(), MenuLogActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onError(String message) {
                                    pb_loader.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    },1000);

                }else{
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
