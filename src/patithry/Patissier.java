/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patithry;

/**
 *
 * @author bneut
 */
public class Patissier implements Runnable {
    
    Patisserie auBonPatissier;

    public Patissier(Patisserie auBonPatissier) {
        this.auBonPatissier = auBonPatissier;
        System.out.println("Un pâtissier embauché...");
    }

    @Override
    public void run() {
        while (auBonPatissier.getClientsEnMagasin() > 0) {
            Gateau gateau = new Gateau();
            auBonPatissier.depose(gateau);
        }
    }

    
    
    
}
