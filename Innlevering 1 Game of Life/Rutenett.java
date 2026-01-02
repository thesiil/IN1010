class Rutenett{

    public int antRader;
    public int antKolonner;
    public Celle[][] rutene;

    public Rutenett(int rad, int kol){
        this.antRader = rad;
        this.antKolonner = kol;
        rutene = new Celle[rad][kol];
    }

    public void lagCelle(int rad, int kol){
        Celle nycelle = new Celle();
        
        if (Math.random() <= 0.3333){
            nycelle.settLevende();
        }
        rutene[rad][kol] = nycelle;
    }

    public void fyllMedTilfeldigeCeller(){
        for(int i = 0; i < rutene.length; i++){
            for(int j = 0; j < rutene[i].length; j++){
                lagCelle(i, j);
            }
        }
    }

    public Celle hentCelle(int rad, int kol){
        if (rad < 0 || rad >= rutene.length || kol < 0 || kol >= rutene[rad].length){
            return null;
        }
        return rutene[rad][kol];
    }

    public void tegnRutenett(){
        for (int tell = 2; tell >0; tell--){
            System.out.println();
        }
        for(int i = 0; i < rutene.length;i++){
            for(int j = 0; j < rutene[i].length; j++){
                System.out.print("+---");
            }
            System.out.println("+");
            for (int j = 0; j < rutene[i].length; j++){
                System.out.print("| " + hentCelle(i, j).hentStatusTegn() + " ");
            }
            System.out.println("|");
        }
        for (int j = 0; j < rutene[0].length; j++){
            System.out.print("+---");
        }
        
        System.out.println("+");
        for (int tell = 2; tell >0; tell--){
            System.out.println();
        }
    }

    public void settNaboer(int rad, int kol){
        
        Celle celle = hentCelle(rad, kol); //hentCelle på Celle-objectet vi legger til
        if (celle == null) return;

        for (int xrad = -1; xrad <= 1; xrad++){
            for (int ykol = -1; ykol <= 1; ykol++){
                if (xrad == 0 && ykol == 0) continue; //Cellen selv

                int naboRad = rad + xrad;
                int naboKol = kol + ykol;

                Celle nabo = hentCelle(naboRad, naboKol); //hentCelle på selve naboen
                
                if (nabo != null){
                    celle.leggTilNabo(nabo);
                }
            }
        }
    }

    public void kobleAlleCeller(){
        for(int i = 0; i < rutene.length; i++){
            for (int j = 0; j < rutene[i].length; j++){
                settNaboer(i, j);
            }
        }
    }

    public int antallLevende(){
        int teller = 0;
        for( int i = 0; i < rutene.length; i++){
            for (int j = 0; j < rutene[i].length; j++){
                if (rutene[i][j].erLevende()){
                    teller++;
                }
            }
        }
        return teller;
    }

    public int hentAntRader(){
        return antRader;
    }

    public int hentAntKolonner(){
        return antKolonner;
    }
}