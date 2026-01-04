import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Subsekvensregister {
    
    private ArrayList<Frekvenstabell> register;
    private final static int SUBSEKVENSLENGDE = 3;

    public Subsekvensregister(){
        this.register = new ArrayList<>();
    }

    public void settInn(Frekvenstabell f){
        register.add(f);
    }

    public Frekvenstabell taUT(){
        if (register.isEmpty()){
            throw new IllegalArgumentException("Registeret er tomt");
        }
        return register.removeFirst(); //Lagde først med random indeksgenerering for å ta ut tilfeldig tabell fra registeret, men endret til å ta ut første.
    }

    public int antall(){
        return register.size();
    }

    public static Frekvenstabell les(String filnavn){
        Frekvenstabell frekvenstabell = new Frekvenstabell(); //lager ny frekvenstabell
        
        try(Scanner fil = new Scanner(new File(filnavn))){ //lager ny scanner på filen
            while (fil.hasNextLine()){ //iterere gjennom frem til alle linjene er gått igjennom
                String linje = fil.nextLine().strip(); //strip for å fjerne whitespace i starten og slutten

                if(linje.isEmpty()){ //hoppe over tomme linjer
                    continue;
                }
                //iterere gjennom linjen 3 bokstaver om gangen
                for (int i = 0; i <= linje.length() - SUBSEKVENSLENGDE; i++){
                    String substring = linje.substring(i, i + SUBSEKVENSLENGDE); //fra og med til men ikke med indeks for substring av hele linjen
                    if (!frekvenstabell.containsKey(substring)){ //ignorerer duplikater
                        frekvenstabell.put(substring, 1);
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Filen ikke funnet " + e.getMessage());
        }
        return frekvenstabell;
    }
}
