//import java.util.*;

public interface TosidigKø<T> extends Iterable<T>{
    
    public boolean erTom();

    public void settInnForan(T elem);

    public void settInnBak(T elem);

    public T taUtFørste();

    public T taUtSiste();

}