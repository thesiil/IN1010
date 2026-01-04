public abstract class Legemiddel{
    public final String navn;
    public int pris;
    public final double mengdeVirkestoff;
    public final int id;
    public static int idTeller = 0;

    public Legemiddel(String navn, int pris, double mengdeVirkestoff){
        this.navn = navn;
        this.pris = pris;
        this.mengdeVirkestoff = mengdeVirkestoff;
        this.id = idTeller++;
    }

    public int hentPris(){
        return pris;
    }

    public void settNyPris(int pris){
        this.pris = pris;
    }

    @Override
    public String toString(){
        return "Legemiddel-ID " + id + " Navn: " + navn + 
        " Pris: " + pris + " Mengde virkestoff: " + mengdeVirkestoff;
    }
}

// vet ikke om man bør ha subklassene i egne filer i sånne tilfeller? gir mest mening å ha i samme vil for å ha oversikt, men gi gjerne beskjed!
class Narkotisk extends Legemiddel{
    public final int styrke;

    public Narkotisk(String navn, int pris, double mengdeVirkestoff, int styrke){
        super(navn, pris, mengdeVirkestoff);
        this.styrke = styrke;
    }
    
    @Override
    public String toString(){
        return super.toString() + " Nivå på narkotisk styrke: " + styrke;
    }    
}

class Vanedannende extends Legemiddel{
    public final int styrke;

    public Vanedannende(String navn, int pris, double mengdeVirkestoff, int styrke){
        super(navn, pris, mengdeVirkestoff);
        this.styrke = styrke;
    }
    
    @Override
    public String toString(){
        return super.toString() + " Nivå på vanedannende styrke: " + styrke;
    }
}

class Vanlig extends Legemiddel{
    public Vanlig(String navn, int pris, double mengdeVirkestoff){
        super(navn, pris, mengdeVirkestoff);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}        