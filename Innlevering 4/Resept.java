abstract class Resept{
    protected Legemiddel legemiddel;
    protected Lege utskrivendeLege;
    protected Pasient pasient;
    protected int reit;
    private static int teller = 0;
    protected final int reseptId;

    public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit){
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendeLege;
        this.pasient = pasient;
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
        return pasient.hentPasientId();
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
        return legemiddel.hentID() + "," + hentLege().hentNavn() +
        "," + hentPasientId()  + 
        "," + type() + "," + hentReit();
    }
    
    abstract public String type();
    
    abstract public int prisÅBetale();
    
}