package fr.jasonlagarde.e_football;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jasonhnovic on 10/05/2018.
 */

public class SessionManager {

    private SharedPreferences prefs;
    private  SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "app_prefs";
    private final static int PRIVATE_MODE = 0;
    private static String IS_LOGGED = "isLogged";
    private final static String ID = "id";
    private final static String NOM = "nom";
    private final static String PRENOM = "prenom";
    private final static String PSEUDO = "pseudo";
    private final static String EMAIL = "email";
    private Context context;

    public SessionManager(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public boolean isLogged(){
        return prefs.getBoolean(IS_LOGGED, false);
    }

    public String getId(){
        return prefs.getString(ID, null);
    }
    public String getNom(){
        return prefs.getString(NOM, null);
    }
    public String getPrenom(){
        return prefs.getString(PRENOM, null);
    }
    public String getPseudo(){
        return prefs.getString(PSEUDO, null);
    }
    public String getEmail(){
        return prefs.getString(EMAIL, null);
    }
    public void insertUer(User user){
        editor.putBoolean(IS_LOGGED, true);
        editor.putString(ID, user.getId());
        editor.putString(NOM, user.getNom());
        editor.putString(PRENOM, user.getPrenom());
        editor.putString(PSEUDO, user.getPseudo());
        editor.putString(EMAIL, user.getEmail());
        editor.commit();
    }
    public void logout(){
        editor.clear().commit();
    }

}
