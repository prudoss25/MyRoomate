package com.ayoubamrani.colocationfacile;

public class Notification
{
    private String EmailEmetteur;
    private String EmailDestinataire;
    private String MessageNotification;
    private String NomEmetteur;
    private String IdNotification;
    private String IdOffre;
    private String DateNotification;
    private String PhotoProfilEmetteur;
    private String IdDemande;

    public Notification(String emailEmetteur, String emailDestinataire, String messageNotification, String nomEmetteur, String idNotification, String idOffre, String dateNotification, String photoProfilEmetteur, String idDemande) {
        EmailEmetteur = emailEmetteur;
        EmailDestinataire = emailDestinataire;
        MessageNotification = messageNotification;
        NomEmetteur = nomEmetteur;
        IdNotification = idNotification;
        IdOffre = idOffre;
        DateNotification = dateNotification;
        PhotoProfilEmetteur = photoProfilEmetteur;
        IdDemande = idDemande;
    }

    public Notification(){}

    public String getEmailEmetteur() {
        return EmailEmetteur;
    }

    public void setIdDemande(String idDemande) {
        IdDemande = idDemande;
    }

    public String getIdDemande() {
        return IdDemande;
    }

    public void setEmailEmetteur(String emailEmetteur) {
        EmailEmetteur = emailEmetteur;
    }

    public String getPhotoProfilEmetteur() {
        return PhotoProfilEmetteur;
    }

    public void setPhotoProfilEmetteur(String photoProfilEmetteur) {
        PhotoProfilEmetteur = photoProfilEmetteur;
    }

    public String getEmailDestinataire() {
        return EmailDestinataire;
    }

    public void setEmailDestinataire(String emailDestinataire) {
        EmailDestinataire = emailDestinataire;
    }

    public String getMessageNotification() {
        return MessageNotification;
    }

    public void setMessageNotification(String messageNotification) {
        MessageNotification = messageNotification;
    }

    public String getNomEmetteur() {
        return NomEmetteur;
    }

    public void setNomEmetteur(String nomEmetteur) {
        NomEmetteur = nomEmetteur;
    }

    public String getIdNotification() {
        return IdNotification;
    }

    public void setIdNotification(String idNotification) {
        IdNotification = idNotification;
    }

    public String getIdOffre() {
        return IdOffre;
    }

    public void setIdOffre(String idOffre) {
        IdOffre = idOffre;
    }

    public String getDateNotification() {
        return DateNotification;
    }

    public void setDateNotification(String dateNotification) {
        DateNotification = dateNotification;
    }
}
