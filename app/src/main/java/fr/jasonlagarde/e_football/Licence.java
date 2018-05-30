package fr.jasonlagarde.e_football;

/**
 * Created by jasonhnovic on 11/05/2018.
 */

public class Licence {

    private String id;
    private String id_user;
    private String id_club;
    private String date_create;

    public Licence(String id, String id_user, String id_club, String date_create) {
        this.id = id;
        this.id_user = id_user;
        this.id_club = id_club;
        this.date_create = date_create;
    }

    public String getId() {
        return id;
    }

    public String getId_user() {
        return id_user;
    }

    public String getId_club() {
        return id_club;
    }

    public String getDate_create() {
        return date_create;
    }
}
