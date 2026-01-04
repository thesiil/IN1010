class Vanedannende extends Legemiddel{
    public final int styrke;

    public Vanedannende(String navn, int pris, double mengdeVirkestoff, int styrke){
        super(navn, pris, mengdeVirkestoff);
        this.styrke = styrke;
    }

    public int hentStyrke(){
        return styrke;
    }
    
    public String hentType(){
        return "vanedannende";
    }

    @Override
    public String toString(){
        return super.toString() + "," + hentStyrke();
    }
}