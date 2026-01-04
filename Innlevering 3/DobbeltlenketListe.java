import java.util.NoSuchElementException;
import java.util.*;

@SuppressWarnings("unused")
public class DobbeltlenketListe<T> implements TosidigKø<T> {
    private class Node{
        T elem;
        Node forrige, neste;

        Node(T elem){
            this.elem = elem;
        }
    }

    private final Node HODE;

    public DobbeltlenketListe(){
        HODE = new Node(null);
        HODE.forrige = HODE.neste = HODE;
    }

    public boolean erTom(){
        return HODE.neste == HODE;
    }

    private void settInnEtter(Node node, T elem){ //hjelpemetode siden vi gjør det samme på forskjellige måter i følgende metoder
        Node ny = new Node(elem);
        ny.forrige = node;
        ny.neste = node.neste;
        node.neste.forrige = ny; // 
        node.neste = ny;
    }

    public void settInnForan(T elem){
        settInnEtter(HODE, elem);
        /*Node ny = new Node(elem);
        ny.forrige = HODE;
        ny.neste = HODE.neste;
        HODE.neste.forrige = ny; // 
        HODE.neste = ny;*/
    }

    public void settInnBak(T elem){
        settInnEtter(HODE.forrige, elem); //setter inn på slutten av rekka
        /*Node ny = new Node(elem);
        ny.neste = HODE;
        ny.forrige = HODE.forrige; //hode sin forrige peker på siste
        HODE.forrige.neste = ny;
        HODE.forrige = ny;*/
    }

    //hjelpemetode for fjerning
    private void taUtDenne(Node denne){
        denne.forrige.neste = denne.neste; //oppdaterer denne sin neste til å hoppe over seg selv
        denne.neste.forrige = denne.forrige; //samme med forrige
    }

    public T taUtFørste(){
        if(erTom()){
            throw new NoSuchElementException(); //om vi prøver å få tilgang til noe som ikke finnes
        }
        Node første = HODE.neste;
        taUtDenne(første);
        return første.elem;
    }

    public T taUtSiste(){
        if (erTom()){
            throw new NoSuchElementException();
        }
        Node siste = HODE.forrige;
        taUtDenne(siste);
        return siste.elem;
    }
    public Iterator<T> iterator(){
        return new ListeIterator();
    }
    private class ListeIterator implements Iterator<T>{
        Node ståsted = HODE;

        public boolean hasNext(){
            return ståsted.neste != HODE;
        }

        public T next(){
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            ståsted = ståsted.neste;
            return ståsted.elem;
        }
    }
}
