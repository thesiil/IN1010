public class Lege implements Comparable<Lege>{
    protected String navn;
    private IndeksertListe<Resept> utskrevneResepter;

    public Lege(String navn){
        this.navn = navn;
        utskrevneResepter = new IndeksertListe<>();
    }

    public String hentNavn(){
        return navn;
    }

    @Override
    public String toString(){
        return navn + ",0";
    }

    @Override
    //sammenligner navnene på Lege-objektene
    public int compareTo(Lege annen){
        return navn.compareTo(annen.hentNavn());
    }

    public int tellNarkotiskeResepter(){ //hjelpemetode for Legesystem, for å hente ut antall narkotiske
        int teller = 0;
        for (Resept resept : utskrevneResepter){
            if (resept.hentLegemiddel() instanceof Narkotisk){
                teller++;
            }
        }
        return teller;
    }
    
    public void skrivUtResept(Resept resept){// hadde gjerne kalt denne for leggtilresept, men da har pasient og lege metoder med like navn som jeg i etterkant ser ikke er så farlig, men jaja
        utskrevneResepter.leggTil(resept);
    }

    public IndeksertListe<Resept> hentResepter(){
        return utskrevneResepter;
    }

    public HvitResept skrivHvitResept (Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk){
            throw new UlovligUtskrift(this, legemiddel);
        }
        HvitResept nyresept = new HvitResept(legemiddel, this, pasient, reit);
        this.skrivUtResept(nyresept);
        pasient.leggTilResept(nyresept); //kun om man vil slippe å kalle på en metode til senere
        return nyresept;
    }

    public MilitærResept skrivMilResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk){throw new UlovligUtskrift(this, legemiddel);
        }
        MilitærResept nyresept = new MilitærResept(legemiddel, this, pasient);
        this.skrivUtResept(nyresept);
        pasient.leggTilResept(nyresept);
        return nyresept;
    }

    public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk){throw new UlovligUtskrift(this, legemiddel);}
        PResept nyresept = new PResept(legemiddel, this, pasient, reit);
        this.skrivUtResept(nyresept);
        pasient.leggTilResept(nyresept);
        return nyresept;
    }

    public BlåResept skrivBlåResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk && !(this instanceof Spesialist)){throw new UlovligUtskrift(this, legemiddel);}
        BlåResept nyresept = new BlåResept(legemiddel, this, pasient, reit);
        this.skrivUtResept(nyresept);
        pasient.leggTilResept(nyresept);
        return nyresept;
    }
}

class Spesialist extends Lege implements Godkjenningsfritak{
    final String kontrollkode;
    
    public Spesialist(String navn, String kontrollkode){
        super(navn);
        this.kontrollkode = kontrollkode;
    }

    @Override
    public String hentKontrollkode(){
        return kontrollkode;
    }

    @Override
    public String toString(){
        return navn + ", " + kontrollkode;
    }
}