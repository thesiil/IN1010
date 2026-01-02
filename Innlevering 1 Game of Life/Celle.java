class Celle{

    public boolean levende;
    public int antNaboer;
    public int antLevendeNaboer;
    private Celle[] naboer; 

    public Celle(){
        levende = false;
        naboer = new Celle[8];
        antNaboer = 0;
        antLevendeNaboer = 0;
    }

    public void settDoed(){
        levende = false;
    }

    public void settLevende(){
        levende = true;
    }

    public boolean erLevende(){
        return levende;
    }

    public char hentStatusTegn(){
        return (levende) ? 'O' : '.';
    }

    public void leggTilNabo(Celle nabo){
        
        for (int i = 0; i < naboer.length; i++){
            if (naboer[i] == null){
                naboer[i] = nabo;
                antNaboer ++;
                break;
            }
        }
    }

    public void tellLevendeNaboer(){
      int i = 0;
      for (Celle c : naboer){ // for-each
        if (c == null){
            continue; //ungår error med null-verdier i arrayen
        }
        if (c.erLevende()){
            i++;
        }
      }
      antLevendeNaboer = i;
    }

    public void oppdaterStatus(){
        if (levende){
            if (antLevendeNaboer < 2){
                levende = false;
            } else if (antLevendeNaboer > 3){
                levende = false;
            }
        }
        else if (!levende){
            if (this.antLevendeNaboer == 3){
                levende = true;
            }
        }
    } 
}    
