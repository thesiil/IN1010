class Stabel <E> extends Lenkeliste<E> {
    
    @Override
    public void leggTil (E x) {
        Node nynode = new Node(x); //lager ny Node-objekt av elementet
        nynode.neste = første; // flytter pekeren til sin neste og setter nye objektet på første.
        første = nynode;
        if (siste == null){
            siste = nynode;
        }
        antall++; //øke antallet i lenkede listen
    }
}    


