/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entities.Visiteur;

/**
 *
 * @author developpeur
 */
public class Session {
    private static Session session = null;
    private  static Visiteur leVisiteur;
    
    private Session(Visiteur visiteur){
        Session.leVisiteur = visiteur;
    }
    
    public static void ouvrir(Visiteur visiteur){
        Session.session = new Session(visiteur);
    }
    
    public static void fermer(){
        Session.session = null;
    }
    
    public static Session getSession() {
        return session;
    }
    
    public static Visiteur getVisiteur(){
        return leVisiteur ;
    }
    
    public static boolean estOuverte(){
        return session != null ;
    }
    
}
