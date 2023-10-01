/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


/**
 *
 * @author Firas
 */
public class SessionManager {
    private static SessionManager instance;
    private Utilisateur currentUser;
    // other instance variables
    
    private SessionManager() {
        // constructor
    }
    
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void setCurrentUser(Utilisateur user) {
        this.currentUser = user;
    }
    
    public Utilisateur getCurrentUser() {
        return this.currentUser;
    }
    
    // other methods
}
