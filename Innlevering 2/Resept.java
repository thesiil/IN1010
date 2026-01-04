abstract class Resept{
    protected Legemiddel legemiddel;
    protected Lege utskrivendeLege;
    protected int pasientId;
    protected int reit;
    private static int teller = 0;
    protected final int reseptId;

    public Resept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendeLege;
        this.pasientId = pasientId;
        this.reit = reit;
        reseptId = teller++;
    }

    public int hentId(){
        return reseptId;
    }

    public Legemiddel hentLegemiddel(){
        return legemiddel;
    }

    public Lege hentLege(){
        return utskrivendeLege;
    }

    public int hentPasientId(){
        return pasientId;
    }

    public int hentReit(){
        return reit;
    }

    public boolean bruk(){
        if (reit == 0){
            return false;
        }    
        reit --;
        return true;
    }

    @Override
    public String toString(){
        return "Legemiddel: " + legemiddel.navn + " Lege: " + hentLege() +
        " PasientID: " + hentPasientId() + " Antall reit: " + hentReit() + 
        " Farge på resept: " + farge();
    }
    
    abstract public String farge();
    
    abstract public int prisÅBetale();
    
}


class HvitResept extends Resept{
    public HvitResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){
        super(legemiddel, utskrivendeLege, pasientId, reit);
    }

    @Override
    public String farge(){
        return "hvit";
    }

    @Override
    public int prisÅBetale(){
        return legemiddel.hentPris();
    }
}

class MilitærResept extends HvitResept{
    public MilitærResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId){
        super(legemiddel, utskrivendeLege, pasientId, 3); //skrives alltid med 3 reit
    }

    @Override
    public int prisÅBetale(){
        return 0;
    }
}

class PResept extends HvitResept{
    public PResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){
        super(legemiddel, utskrivendeLege, pasientId, reit);
    }

    @Override
    public int prisÅBetale(){
        int nypris = legemiddel.hentPris() - 108;
        if (nypris < 0){
            return 0;
        }
        return nypris;
        
    }
}

class BlåResept extends Resept{
    public BlåResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){
        super(legemiddel, utskrivendeLege, pasientId, reit);
    }

    @Override
    public String farge(){
        return "blå";
    }

    @Override
    public int prisÅBetale(){
        return (int) Math.round((legemiddel.hentPris())*0.25); //eksplisitt casting til Int, siden math.round blir en long
    }
   
}

