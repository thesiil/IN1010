import java.io.*;
import java.util.*;

class Frekvenstabell extends TreeMap<String, Integer> {
    
    @Override
    public String toString(){
        StringBuilder linje = new StringBuilder();
        for (Map.Entry<String, Integer> frekv : this.entrySet()){
            linje.append(frekv.getKey()).append(" ").append(frekv.getValue()).append("\n");
        }
        return linje.toString();
    }

    public static Frekvenstabell flett(Frekvenstabell f1, Frekvenstabell f2){
        Frekvenstabell flettet = new Frekvenstabell();
        
        f1.forEach(flettet::put); //legger inn alle nøkler og verdien fra f1 til flettet
        f2.forEach((n,v) -> flettet.put(n, v + flettet.getOrDefault(n, 0))); //legge inn alle par fra f2 i flettet

        return flettet;
    }

    public void skrivTilFil(String filnavn){
        try(PrintWriter tilFil = new PrintWriter(new File(filnavn))){
            tilFil.write(this.toString());
        } catch (IOException e){
            System.out.println("Feil ved oppretting av fil: " + e.getMessage());
            System.exit(1);
        }
    }
}