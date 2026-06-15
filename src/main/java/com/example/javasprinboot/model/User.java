package com.example.javasprinboot.model;

/**
 * Mod�le repr�sentant un utilisateur de l'application.
 * Correspond � la table "users" dans la base PostgreSQL.
 */
public class User {

    private Long id;
    private String login;
    private String password;
    private String name;

    public User() {}

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    // === Getters et Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
