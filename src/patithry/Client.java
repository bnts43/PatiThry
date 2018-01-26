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
public class Client implements Runnable {

    Patisserie auBonPatissier;
    Gateau monGateau;
    
    Client(Patisserie auBonPatissier) {
        this.auBonPatissier = auBonPatissier;
        System.out.println("Un client arrive...");
    }
    
    @Override
    public void run() {
        monGateau = auBonPatissier.achete(this);
    }
    
}
