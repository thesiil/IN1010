class Verden{

    private Rutenett rutenett;
    public int genNr;

    public Verden(int rad, int kol){
        rutenett = new Rutenett(rad, kol);
        genNr = 0;

        rutenett.fyllMedTilfeldigeCeller();
        rutenett.kobleAlleCeller();
    }

    public void tegn(){
        System.out.println("_______________________________");
        System.out.println("\nGenerasjon nr: " + genNr);
        rutenett.tegnRutenett();
        System.out.println("Det er " + rutenett.antallLevende() + " levende celler.\n");
        System.out.println("_______________________________");
    }

    public void oppdatering(){
        int antRader = rutenett.hentAntRader();
        int antKolonner = rutenett.hentAntKolonner();

        for(int i = 0; i < antRader; i++){
            for (int j = 0; j < antKolonner; j++){
                rutenett.hentCelle(i, j).tellLevendeNaboer();
            }
        }
        for( int i = 0; i < rutenett.antRader; i++){
            for (int j = 0; j < antKolonner; j++){
                rutenett.hentCelle(i, j).oppdaterStatus();
            }
        }
        genNr++;
    }
}