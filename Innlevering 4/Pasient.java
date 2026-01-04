//import java.util.*;

public class Pasient {
    protected final int PASIENTID; //final siden den ikke skal kunne endres
    private static int teller = 0;

    private String navn, fødselsnummer;
    private IndeksertListe<Resept> resepter;

    public Pasient(String n, String fnummer){
        this.navn = n;
        this.fødselsnummer = fnummer;
        this.PASIENTID = teller++;
        this.resepter = new IndeksertListe<>();
    }

    public int hentPasientId(){
        return PASIENTID;
    }

    public String hentNavn(){
        return navn;
    }

    public String hentFødselsnummer(){
        return fødselsnummer;
    }

    public void leggTilResept(Resept resept){
        resepter.leggTil(resept);
    }

    public IndeksertListe<Resept> hentResepter(){
        return resepter;
    }

    public int tellNarkotiskeResepter(){ //hjelpemetode for Legesystem, for å hente ut antall narkotiske
        int teller = 0;
        for (Resept resept : resepter){
            if (resept.hentLegemiddel() instanceof Narkotisk){
                teller++;
            }
        }
        return teller;
    } 

    @Override
    public String toString(){
        return navn + ", " + fødselsnummer;
    }
}
