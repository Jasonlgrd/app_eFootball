package fr.jasonlagarde.e_football;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jasonhnovic on 10/05/2018.
 */

public class User implements Parcelable {

    private String id;
    private String nom;
    private String prenom;
    private String pseudo;
    private String email;

    public User(String id, String nom, String prenom, String pseudo, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.email = email;
    }

    protected User(Parcel in) {
        id = in.readString();
        nom = in.readString();
        prenom = in.readString();
        pseudo = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nom);
        parcel.writeString(prenom);
        parcel.writeString(pseudo);
        parcel.writeString(email);
    }
}
