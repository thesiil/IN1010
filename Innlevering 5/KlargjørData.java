import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KlargjørData {

    private static final int ANTALL_TRÅDER = 8;

    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Bruk java KlargjørData <filsti>");
            return;
        }
        //en monitor per smittet/ikke smittet
        Monitor smittet = new Monitor();
        Monitor ikkesmittet = new Monitor();

        String metadata = args[0]; //henter navnet til metadatafilen
        File fil = new File(metadata);
        String mappe = fil.getParent() + "/"; //for å få mappen som metadata ligger i og for å senere finne datafilene der

        List<Thread> lesetråder = new ArrayList<>();
        // leser inn fra fil, og starter tråd basert på innhold i fillinjen, legger til tråden i trådlisten
        try(Scanner filleser = new Scanner(new File(metadata))){
            while(filleser.hasNextLine()){
                String linje = filleser.nextLine().strip();
                
                if(linje.isEmpty()) continue; //hopper over eventuelle tomme linjer

                String[] deler = linje.split(",");
                if (deler.length != 2) continue; //sikre riktig format per linje

                // henter ut filnavn med filsti, og "status" på filen(true eller false), derfra også riktig monitor
                String filnavn = mappe + deler[0];
                String erSmittet = deler[1]; //variabel for å lagre om det er true eller false, altså smittet eller ikke
                Monitor valgtMonitor = erSmittet.equalsIgnoreCase("true") ? smittet : ikkesmittet;
                //lager lesetråder basert på riktig monitor og filnavnet
                Lesetråd lesetråd = new Lesetråd(valgtMonitor, filnavn);
                Thread tråd = new Thread(lesetråd);

                //legger til i trådlisten og starter tråden, så den begynner å lese mens resten av filene blir lest.
                lesetråder.add(tråd);
                tråd.start();
                
            }
        } catch (FileNotFoundException e){
            System.out.println("Fant ikke filen" + e.getMessage());
        }
        
        // går igjennom trådene og avslutter
        for(Thread tråd : lesetråder){
            try{
                tråd.join();
                //alle lesetrådene ferdige og frekvenstabellene lagt til i monitorene
            } catch(InterruptedException e){
                System.out.println("En tråd ble avbrutt " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        //flettingen herfra
        List<Thread> flettetråder = new ArrayList<>();
        for (int i = 0; i < ANTALL_TRÅDER; i++){
            Thread smittetråd = new Thread(new Flettetråd(smittet));
            Thread ikkeSmittetråd = new Thread(new Flettetråd(ikkesmittet));
            flettetråder.add(smittetråd);
            flettetråder.add(ikkeSmittetråd);
            smittetråd.start();
            ikkeSmittetråd.start();
        }
        
        for(Thread tråd : flettetråder){
            try{
                tråd.join();
            } catch (InterruptedException e){
                System.out.println("En tråd ble avbrutt :(" + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        
        Frekvenstabell smittetTabell = smittet.taUt();
        Frekvenstabell ikkeSmittetTabell = ikkesmittet.taUt();

        smittetTabell.skrivTilFil("smittet");
        ikkeSmittetTabell.skrivTilFil("ikke_smittet");
        System.out.println("Filene skrevet ut.");
    }
}