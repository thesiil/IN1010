public class HvitResept extends Resept{
    public HvitResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit){
        super(legemiddel, utskrivendeLege, pasient, reit);
    }

    @Override
    public String type(){
        return "hvit";
    }

    @Override
    public int prisÅBetale(){
        return legemiddel.hentPris();
    }
}