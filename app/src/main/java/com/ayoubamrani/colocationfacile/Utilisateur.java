package com.ayoubamrani.colocationfacile;

public class Utilisateur {
    private String Nom;
    private String Prenom;
    private String Sexe;
    private int Age;
    private String Email;
    private String MotDePasse;
    private boolean EstEtudiant;
    private boolean EstFumeur;
    private boolean PossedAnimaux;
    private String PhotoProfilUrl;

    public Utilisateur(){}

    public Utilisateur(String Nom, String Prenom, String Sexe, int Age, String Email, String MotDePasse, boolean EstEtudiant, boolean EstFumeur, boolean PossedeAnimaux, String PhotoProfilUrl)
    {
        this.Nom=Nom;
        this.Prenom=Prenom;
        this.Sexe=Sexe;
        this.Age=Age;
        this.Email=Email;
        this.MotDePasse=MotDePasse;
        this.EstEtudiant=EstEtudiant;
        this.EstFumeur=EstFumeur;
        this.PossedAnimaux=PossedeAnimaux;
        this.PhotoProfilUrl=PhotoProfilUrl;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public void setEstEtudiant(boolean estEtudiant) {
        EstEtudiant = estEtudiant;
    }

    public void setEstFumeur(boolean estFumeur) {
        EstFumeur = estFumeur;
    }

    public void setPossedAnimaux(boolean possedAnimaux) {
        PossedAnimaux = possedAnimaux;
    }

    public void setPhotoProfilUrl(String photoProfilUrl) {
        PhotoProfilUrl = photoProfilUrl;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getSexe() {
        return Sexe;
    }

    public int getAge() {
        return Age;
    }

    public String getEmail() {
        return Email;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public boolean isEstEtudiant() {
        return EstEtudiant;
    }

    public boolean isEstFumeur() {
        return EstFumeur;
    }

    public boolean isPossedAnimaux() {
        return PossedAnimaux;
    }

    public String getPhotoProfilUrl() {
        return PhotoProfilUrl;
    }
}
