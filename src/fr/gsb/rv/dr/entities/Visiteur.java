/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.entities;

/**
 *
 * @author developpeur
 */
public class Visiteur {
    private String matricule;
    private String nom;
    private String prenom;

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
    
        @Override
    public String toString() {
        return "Visiteur{" + "matricule=" + matricule + ", nom=" + nom + ", prenom=" + prenom + '}';
    }
    
}
