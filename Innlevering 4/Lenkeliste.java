import java.util.Iterator;
//import java.util.NoSuchElementException;

abstract class Lenkeliste <E> implements Liste<E> {
    protected class Node {
        E data;
        Node neste;

        Node(E data){
            this.data = data;
            this.neste = null; //starter med ingen neste element i listen
        }
    }

    public Iterator<E> iterator(){
        return new LenkelisteIterator();
    }

    protected class LenkelisteIterator implements Iterator<E>{
        private Node denne = første;
        
        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public E next(){
            E data = denne.data;
            denne = denne.neste;
            return data;
        }
    }

    protected Node første;
    protected Node siste;
    protected int antall = 0;

    @Override
    public String toString () {
        String str = "[";
        Node denne = første;
        while (denne != null){
            str += denne.data;
            if (denne.neste != null){
                str += ", ";
            }
            denne = denne.neste;
        }
        str += "]";
        return str;
    }

    @Override
    public int størrelse () {
	return antall;
    }

    @Override
    public void leggTil (E x) {
        Node nynode = new Node(x); //lager ny Node-objekt
        if (første == null){ // om det ikke finnes elementer enda, blir nye noden både første og siste
            første = siste = nynode;
        } else{
            siste.neste = nynode; // legger til nynode som siste sin neste, og siste til nynode
            siste = nynode;
        }
        antall++; //øke antallet i lenkede listen
    }
    
    @Override
    public E hent() { //hent første i listen
        return (første != null) ? første.data : null; // returnerer data på første om den ikke er null, ellers null
    }

    @Override
    public E fjern () { //fjerner første
        if (første == null){
            throw new UgyldigListeindeks(-1);
        }
        E elem = første.data;
        første = første.neste; // peker på neste element i listen
        if (første == null){ //om listen nå er tom skal siste også peke på null
            siste = null;
        }
        antall--;
        return elem;
    }
}