class PResept extends HvitResept{
    public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit){
        super(legemiddel, utskrivendeLege, pasient, reit);
    }

    @Override
    public String type(){
        return "p";
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