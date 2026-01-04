class Narkotisk extends Legemiddel{
    public final int styrke;

    public Narkotisk(String navn, int pris, double mengdeVirkestoff, int styrke){
        super(navn, pris, mengdeVirkestoff);
        this.styrke = styrke;
    }

    public int hentStyrke(){
        return styrke;
    }

    public String hentType(){
        return "narkotisk";
    }
    
    @Override
    public String toString(){
        return super.toString() + "," + hentStyrke();
    }    
}