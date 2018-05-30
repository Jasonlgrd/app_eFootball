package fr.jasonlagarde.e_football;

/**
 * Created by jasonhnovic on 11/05/2018.
 */

public class Departement {

    private String id;
    private String nom;

    public Departement(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
}
