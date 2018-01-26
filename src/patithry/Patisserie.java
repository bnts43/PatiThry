/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patithry;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bneut
 */
public class Patisserie {
    
    private static List<Gateau> listeGateaux = new ArrayList<>();
    private static Patisserie auBonPatissier = new Patisserie();
    private static List<Patissier> lequipePatisserie = new ArrayList<>();
    private static List<Thread> lequipeAuTravail = new ArrayList<>();
    private static List<Client> chersClients = new ArrayList<>();
    private static List<Thread> clientsEnAttente = new ArrayList<>();
    private static int nombreGateauxProduits = 0;
    private static int nombreClients = 0;
            

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long starting = System.currentTimeMillis();
        for (int i = 1; i<4 ; i++) {
            lequipePatisserie.add(new Patissier(auBonPatissier));
        }
        
        for (int i = 1; i <30 ; i++) {
            nombreClients++;
            chersClients.add(new Client(auBonPatissier));
        }
        
        lequipePatisserie.forEach(p -> lequipeAuTravail.add(new Thread(p)));
        chersClients.forEach(c -> clientsEnAttente.add(new Thread(c)));

        clientsEnAttente.forEach(c -> {
            c.start();
            System.out.println("Un client est entré dans la pâtisserie...");
        });
        
        mettreLEquipeAuTravail();
        long chronoFin = System.currentTimeMillis();
        System.out.println("L'équipe a produit "+nombreGateauxProduits+" gâteaux aujourd'hui.");
        System.out.println(nombreClients + " clients sont passés aujourd'hui.");
        System.out.println("Il reste "+listeGateaux.size()+" gâteaux en stock.");
        System.out.println("Il s'est passé "+ (chronoFin - starting) +" milli-secondes pour cela.");
    }

    
    public synchronized void depose(Gateau gateau) {
        nombreGateauxProduits++;
        listeGateaux.add(gateau);
        System.out.println("Le gateau a été déposé au stock");
        if (getStock() >= 1) {
            auBonPatissier.notify();
        }
        
    }
    
    public synchronized Gateau achete(Client client) {
        while (getStock() == 0) {
            try {wait();}
            catch (InterruptedException e) {System.out.println("Un problème");}
        }
        int hasard = (int) (Math.random() * (getStock()-1));
        Gateau gateauVendu = listeGateaux.get(hasard);
        listeGateaux.remove(hasard);
        System.out.println("Un client a acheté le gateau "+hasard);
        quitterLaPatisserie(client);
        return gateauVendu;
    }
    
    public int getStock() {
        return listeGateaux.size();
    }
    
    public void quitterLaPatisserie(Client client) {
        chersClients.remove(client);
        System.out.println("Un client a quitté la pâtisserie...");
    }
    
    public static void mettreLEquipeAuTravail() {
        lequipeAuTravail.forEach(t -> {
            t.start();
            System.out.println("Un pâtissier se met au boulot...");
        });       
        lequipeAuTravail.forEach(t -> {
        try {
                t.join();
            } catch (InterruptedException ex) {
                System.out.println("Un problème");
            }
        });
    }
    
    public static int getClientsEnMagasin() {
        return chersClients.size();
    }
}
