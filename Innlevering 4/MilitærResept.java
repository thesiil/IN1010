public class MilitærResept extends HvitResept{
    public MilitærResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient){
        super(legemiddel, utskrivendeLege, pasient, 3); //skrives alltid med 3 reit
    }

    @Override
    public String type(){
        return "militaer";
    }

    @Override
    public int prisÅBetale(){
        return 0;
    }
}