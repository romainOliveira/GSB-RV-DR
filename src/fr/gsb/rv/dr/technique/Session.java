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
    
    public Session getSession() {
        return session;
    }
    
    public Visiteur getLeVisiteur(){
        return leVisiteur ;
    }
    
    public boolean estOuverte(){
        return session != null ;
    }
    
}
