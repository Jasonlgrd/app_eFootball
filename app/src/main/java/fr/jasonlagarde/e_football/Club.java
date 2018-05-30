package fr.jasonlagarde.e_football;

/**
 * Created by jasonhnovic on 11/05/2018.
 */

public class Club {

    private String id;
    private String nom;
    private String ville;
    private String departement;

    public Club(String id, String nom, String ville, String departement) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
        this.departement = departement;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getVille() {
        return ville;
    }

    public String getDepartement() {
        return departement;
    }
}
