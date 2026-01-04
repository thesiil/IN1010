public class BlåResept extends Resept{
    public BlåResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit){
        super(legemiddel, utskrivendeLege, pasient, reit);
    }

    @Override
    public String type(){
        return "blaa";
    }

    @Override
    public int prisÅBetale(){
        return (int) Math.round((legemiddel.hentPris())*0.25); //eksplisitt casting til Int, siden math.round blir en long
    }
   
}