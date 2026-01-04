import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;;


public class Monitor {
    private final Subsekvensregister register;
    private final Lock lås = new ReentrantLock();
    private final Condition ikkeTom = lås.newCondition();

    public Monitor(){
        this.register = new Subsekvensregister();
    }

    //setter inn frekvenstabell og gir signal
    public void settInn(Frekvenstabell f){
        lås.lock();
        try{
            register.settInn(f);
            //ikkeTom.signalAll(); Siden lesetrådene og flettetrådene ikke jobber samtidig, trengs ikke denne i dette tilfellet. Hadde jeg klart å kjøre fletting samtidig hadde man trengt denne her også
        }finally{
            lås.unlock();
        }
    }
    //vente om registeret er tomt, ta ut om noe er i registeret. 
    public Frekvenstabell taUt(){
        lås.lock();
        try{
            while(register.antall() == 0){ //venter på at noe skal settes inn
                ikkeTom.await();
            }
            return register.taUT();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //stoppe tråden på kontrollert vis
            return null;
        } finally{
            lås.unlock();
        }
    }
    // henter ut to tabeller i en liste om det er nok tilgjengelig og ikke alle er ferdig flettet (antall = 1)
    public List<Frekvenstabell> taUtTo() {
        lås.lock();
        try {
            while (register.antall() < 2) {
                if(erFerdig()){ //dersom alle flettingene er ferdige så er det ikke poeng å vente mer
                    return null;
                }
                ikkeTom.await();
            }
            List<Frekvenstabell> tabeller = new ArrayList<>();
            tabeller.add(register.taUT());
            tabeller.add(register.taUT());
            
            return tabeller;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lås.unlock();
        }
    }

    //flette og sette inn flettet tabell i registeret, gi videre signal om at noe er satt inn igjen
    public void settInnFlettet(List<Frekvenstabell> f){
        lås.lock();
        try{
            if (f.size() != 2) {
                throw new IllegalArgumentException("Det må være nøyaktig to frekvenstabeller for fletting");
            }
            Frekvenstabell flettet = Frekvenstabell.flett(f.get(0), f.get(1)); //fletter sammen frekvenstabellene som ble returnert i taUtTo
            register.settInn(flettet);
            ikkeTom.signal();
        }finally{
            lås.unlock();
        }
    }

    //sjekke om alt er ferdig flettet for en signal-metode. Fungerer kun med slik oppgave som dette, der lesing fullføres før fletting begynner, ellers ville programmet trodd den er ferdig før tiden.
    public boolean erFerdig(){
        return register.antall() == 1;
    }

    public int antall(){
        return register.antall();
    }

    public Frekvenstabell les(String filnavn){
        return Subsekvensregister.les(filnavn);
    }

}
