class IndeksertListe <E> extends Lenkeliste<E> {
    
    public void leggTil (int pos, E x) {
        if (pos < 0 || pos > antall){
            throw new UgyldigListeindeks(pos);
        }
        Node nynode = new Node(x); //lager ny node-objekt
        if (pos == 0){ //om man legger til på første posisjon
            nynode.neste = første;
            første = nynode;
            if(antall == 0){
                siste = nynode;
            }
        }else{

        Node nåværende = første;

        for (int i = 0; i < pos -1; i++){ // skal finne posisjonen rett før den vi skal legge til
            nåværende = nåværende.neste;
            }
        nynode.neste = nåværende.neste; //nye nodens neste skal peke på det samme som nåværende neste, for å sette inn nytt element før denne
        nåværende.neste = nynode; //legger til nye noden
        if (pos == antall){ //om vi nå la til på siste posisjon, sett siste til å peke på nynode
            siste = nynode;
            }
        }
        antall++; //oppdaterer antallet til slutt
    }

    public void sett (int pos, E x) { //skal erstatte elementet i posisjon pos med x
    if (pos < 0 || pos >= antall){
        throw new UgyldigListeindeks(pos); // sjekker at posisjonen er gyldig 
    }
        Node nåværende = første;
        for (int i = 0; i < pos; i++){ // skal finne posisjonen vi skal erstatte på
            nåværende = nåværende.neste;
        }
        nåværende.data = x; //erstatter objektet på posisjonen
    }

    public E hent (int pos) {
        if (pos < 0 || pos >= antall){
            throw new UgyldigListeindeks(pos); // sjekker at posisjonen er gyldig 
        }
        Node nåværende = første;
        for (int i = 0; i < pos; i++){
            nåværende = nåværende.neste;
        }
        return nåværende.data;
    }

    public E fjern (int pos) {
        if (pos < 0 || pos >= antall){
            throw new UgyldigListeindeks(pos); // sjekker at posisjonen er gyldig 
        }
        if (pos == 0){
            Node fjernes = første; // om posisjonen man fjerner fra er 0, flytte første til å peke på første sin neste
            første = første.neste;
            
            if (første == null){ //om listen da er tom settes første+siste til null
                siste = null;
            }
            antall--;
            return fjernes.data;
        }
        Node nåværende = første; //starter iterering fra første for å komme frem til den rett før den som fjernes
        for (int i = 0; i < pos -1; i++){
            nåværende = nåværende.neste;
        }
        Node fjernes = nåværende.neste; //noden som fjernes
        nåværende.neste = fjernes.neste; // får nåværende.neste til å peke på en etter pos som fjernes
        
        if (fjernes == siste){
            siste = nåværende;
        }
        antall--;
        return fjernes.data;
    }
}
