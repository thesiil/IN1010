class Lege{
    protected String navn;

    public Lege(String navn){
        this.navn = navn;
    }

    public String hentNavn(){
        return navn;
    }

    @Override
    public String toString(){
        return "Lege: " + navn;
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
        return "Spesialistlege: " + navn + " Kontrollkode: " + kontrollkode;
    }
}