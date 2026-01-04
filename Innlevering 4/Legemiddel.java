public abstract class Legemiddel{
    public final String navn;
    public int pris;
    public final double mengdeVirkestoff;
    public final int ID;
    public static int idTeller = 0;

    public Legemiddel(String navn, int pris, double mengdeVirkestoff){
        this.navn = navn;
        this.pris = pris;
        this.mengdeVirkestoff = mengdeVirkestoff;
        ID = idTeller++;
    }

    public int hentPris(){
        return pris;
    }

    public void settNyPris(int pris){
        this.pris = pris;
    }

    public String hentNavn(){
        return navn;
    }

    public double hentVirkestoff(){
        return mengdeVirkestoff;
    }

    public String hentType(){
        return "vanlig";
    }

    public int hentID(){
        return ID;
    }

    @Override
    public String toString(){
        return hentNavn() + "," + hentType() + "," + hentPris() + "," + hentVirkestoff();
    }
}