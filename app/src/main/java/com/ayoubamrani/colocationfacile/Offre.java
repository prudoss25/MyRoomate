package com.ayoubamrani.colocationfacile;

public class Offre {
    private String IdOffre;
    private String Localisation;
    private String Type;
    private int Prix;
    private int Duree;
    private int NbrDeColocs;
    private int AgeMin;
    private int AgeMax;
    private String Description;
    private boolean EtudiantSeulement;
    private boolean FumeurAccepte;
    private boolean AnimauxAcceptes;
    private boolean OffreurAPaye;
    private boolean DemandeurAPaye;
    private String IdOffreur;
    private String PhotoOffreUrl;
    private String NomOffreur;
    private String PhotoOffreur;

    public Offre(){}

    public Offre(String IdOffre, String IdOffreur, String Localisation, String Type, int Prix, int Duree , int NbrDeColocs, String Description, int AgeMin, int AgeMax, boolean FumeurAccepte, boolean AnimauxAcceptes, boolean EtudiantSeulement, String PhotoOffreUrl, String NomOffreur, String PhotoOffreur, boolean OffreurAPaye, boolean DemandeurAPaye)
    {
        this.IdOffre=IdOffre;
        this.IdOffreur=IdOffreur;
        this.Localisation=Localisation;
        this.Type=Type;
        this.Prix=Prix;
        this.Duree=Duree;
        this.NbrDeColocs=NbrDeColocs;
        this.Description=Description;
        this.AgeMin=AgeMin;
        this.AgeMax=AgeMax;
        this.FumeurAccepte=FumeurAccepte;
        this.AnimauxAcceptes=AnimauxAcceptes;
        this.EtudiantSeulement=EtudiantSeulement;
        this.PhotoOffreUrl=PhotoOffreUrl;
        this.NomOffreur=NomOffreur;
        this.PhotoOffreur=PhotoOffreur;
        this.OffreurAPaye=OffreurAPaye;
        this.DemandeurAPaye=DemandeurAPaye;
    }

    public String getIdOffre() {
        return IdOffre;
    }

    public void setIdOffre(String idOffre) {
        IdOffre = idOffre;
    }

    public String getNomOffreur() {
        return NomOffreur;
    }

    public void setNomOffreur(String nomOffreur) {
        NomOffreur = nomOffreur;
    }

    public String getPhotoOffreur() {
        return PhotoOffreur;
    }

    public void setPhotoOffreur(String photoOffreur) {
        PhotoOffreur = photoOffreur;
    }

    public String getLocalisation() {
        return Localisation;
    }

    public void setLocalisation(String localisation) {
        Localisation = localisation;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getPrix() {
        return Prix;
    }

    public void setPrix(int prix) {
        Prix = prix;
    }

    public int getDuree() {
        return Duree;
    }

    public void setDuree(int duree) {
        Duree = duree;
    }

    public int getNbrDeColocs() {
        return NbrDeColocs;
    }

    public void setNbrDeColocs(int nbrDeColocs) {
        NbrDeColocs = nbrDeColocs;
    }

    public int getAgeMin() {
        return AgeMin;
    }

    public void setAgeMin(int ageMin) {
        AgeMin = ageMin;
    }

    public int getAgeMax() {
        return AgeMax;
    }

    public void setAgeMax(int ageMax) {
        AgeMax = ageMax;
    }

    public boolean isOffreurAPaye() {
        return OffreurAPaye;
    }

    public void setOffreurAPaye(boolean offreurAPaye) {
        OffreurAPaye = offreurAPaye;
    }

    public boolean isDemandeurAPaye() {
        return DemandeurAPaye;
    }

    public void setDemandeurAPaye(boolean demandeurAPaye) {
        DemandeurAPaye = demandeurAPaye;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isEtudiantSeulement() {
        return EtudiantSeulement;
    }

    public void setEtudiantSeulement(boolean etudiantSeulement) {
        EtudiantSeulement = etudiantSeulement;
    }

    public boolean isFumeurAccepte() {
        return FumeurAccepte;
    }

    public void setFumeurAccepte(boolean fumeurAccepte) {
        FumeurAccepte = fumeurAccepte;
    }

    public boolean isAnimauxAcceptes() {
        return AnimauxAcceptes;
    }

    public void setAnimauxAcceptes(boolean animauxAcceptes) {
        AnimauxAcceptes = animauxAcceptes;
    }

    public String getIdOffreur() {
        return IdOffreur;
    }

    public void setIdOffreur(String idOffreur) {
        IdOffreur = idOffreur;
    }

    public String getPhotoOffreUrl() {
        return PhotoOffreUrl;
    }

    public void setPhotoOffreUrl(String photoOffreUrl) {
        PhotoOffreUrl = photoOffreUrl;
    }

}
