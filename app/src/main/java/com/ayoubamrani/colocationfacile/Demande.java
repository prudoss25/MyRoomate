package com.ayoubamrani.colocationfacile;

public class Demande
{
    private String EmailDemandeur;
    private String EmailOffreur;
    private String MessageDemandeur;
    private String NomDemandeur;
    private String IdDemande;
    private String IdOffre;
    private String DateDemande;
    private Boolean EstAcceptee;
    private String PhotoProfilDemandeur;



    public Boolean getEstAcceptee() {
        return EstAcceptee;
    }

    public void setEstAcceptee(Boolean estAcceptee) {
        EstAcceptee = estAcceptee;
    }



    public String getIdOffre() {
        return IdOffre;
    }

    public void setIdOffre(String idOffre) {
        IdOffre = idOffre;
    }

    public String getDateDemande() {
        return DateDemande;
    }

    public void setDateDemande(String dateDemande) {
        DateDemande = dateDemande;
    }


    public String getIdDemande() {
        return IdDemande;
    }

    public void setIdDemande(String idDemande) {
        IdDemande = idDemande;
    }


    public String getNomDemandeur() {
        return NomDemandeur;
    }

    public void setNomDemandeur(String nomDemandeur) {
        NomDemandeur = nomDemandeur;
    }

    public String getPhotoProfilDemandeur() {
        return PhotoProfilDemandeur;
    }

    public void setPhotoProfilDemandeur(String photoProfilDemandeur) {
        PhotoProfilDemandeur = photoProfilDemandeur;
    }


    public String getEmailDemandeur() {
        return EmailDemandeur;
    }

    public void setEmailDemandeur(String emailDemandeur) {
        EmailDemandeur = emailDemandeur;
    }

    public String getEmailOffreur() {
        return EmailOffreur;
    }

    public void setEmailOffreur(String emailOffreur) {
        EmailOffreur = emailOffreur;
    }

    public String getMessageDemandeur() {
        return MessageDemandeur;
    }

    public void setMessageDemandeur(String messageDemandeur) {
        MessageDemandeur = messageDemandeur;
    }

    public Demande(String emailDemandeur, String emailOffreur, String messageDemandeur, String nomDemandeur, String idDemande, String idOffre, String dateDemande, String photoProfilDemandeur,Boolean estAcceptee) {
        EmailDemandeur = emailDemandeur;
        EmailOffreur = emailOffreur;
        MessageDemandeur = messageDemandeur;
        NomDemandeur = nomDemandeur;
        IdDemande = idDemande;
        IdOffre = idOffre;
        DateDemande = dateDemande;
        PhotoProfilDemandeur = photoProfilDemandeur;
        EstAcceptee=estAcceptee;
    }

    public Demande() {

    }
}
