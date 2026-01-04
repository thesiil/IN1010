class Prioritetskø <E extends Comparable<E>> extends Lenkeliste<E> {
    // ...
    public void leggTil(E x){
        Node nynode = new Node(x);
        if (første == null || x.compareTo(første.data) <= 0){
            nynode.neste = første;
            første = nynode;
            if (antall == 0){
                siste = nynode;
            }
            antall++;
            return; //for å hoppe ut og ikke fortsette i while løkka selv om første if-sjekk gjelder i dette tilfellet.
        }
        Node nåværende = første; //begynner å gå gjennom listen fra første
        while (nåværende.neste != null && x.compareTo(nåværende.neste.data) > 0){
            nåværende = nåværende.neste;
        }
        nynode.neste = nåværende.neste;
        nåværende.neste = nynode;
        if(nynode.neste == null){
            siste = nynode;
        }
        antall++;
    }

    
}
