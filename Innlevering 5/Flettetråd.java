import java.util.List;

public class Flettetråd implements Runnable{
    private final Monitor monitor;

    public Flettetråd(Monitor monitor){
        this.monitor = monitor;
    }
    @Override
    public void run() {
        // Løkken fortsetter til alle frekvenstabeller er flettet til én
        while (!monitor.erFerdig()) {
            // Hent ut to frekvenstabeller fra monitoren
            List<Frekvenstabell> tabeller = monitor.taUtTo();

            // Kontroller at vi faktisk fikk to tabeller til fletting
            if (tabeller != null && tabeller.size() == 2) {
                // Sett den flettede tabellen inn i monitoren
                monitor.settInnFlettet(tabeller);
            }
        }
    }
}