public class Lesetråd implements Runnable{
    private final String filnavn;
    private final Monitor monitor;

    public Lesetråd(Monitor monitor, String filnavn){
        this.monitor = monitor;
        this.filnavn = filnavn;
    }

    @Override
    public void run(){
        //leser inn filen 
        Frekvenstabell frekvenstabell = monitor.les(filnavn);
        //setter inn frekvenstabell i monitor
        if (frekvenstabell != null){
            monitor.settInn(frekvenstabell);
        }
    }
}
