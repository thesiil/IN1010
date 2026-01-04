public class Hovedprogram {
    public static void main(String[] args) {
    //Medisiner
    Narkotisk medisin1 = new Narkotisk("Ritalin", 158, 30, 5);
    Vanedannende medisin2 = new Vanedannende("Morfin Orifarm", 250, 10, 10);
    Vanlig medisin3 = new Vanlig("Cerazette", 180, 5);

    //Lege for å lage resepter
    Lege lege = new Lege("Anna Johansen");
    Spesialist spesialistlege = new Spesialist("Ida Hansen", "AB903456");

    // Resepter
    HvitResept resept1 = new HvitResept(medisin3, lege, 35, 4);
    MilitærResept resept2 = new MilitærResept(medisin2, lege, 90);
    PResept resept3 = new PResept(medisin3, lege, 42, 5);
    BlåResept resept4 = new BlåResept(medisin1, spesialistlege, 45, 0);
    
    System.out.println("\nMedisiner: ");
    System.out.println(medisin1);
    System.out.println(medisin2);
    System.out.println(medisin3);

    
    System.out.println("\nResepter");
    System.out.println(resept1);
    System.out.println(resept2);
    System.out.println(resept3);
    System.out.println(resept4);

    System.out.println("\nLeger: ");
    System.out.println(resept1.hentLege());
    System.out.println(resept4.hentLege());

    //Prøver å bruke med 0 reit, og sjekker reit på militærresept
    System.out.println("**Tester bruk av resept med 0 reit, og reit på militærresept (skal være 3)**");
    System.out.println("Bruk av resept4 med 0 reit: " + (resept4.bruk()? "Lyktes, noe er galt med bruk(), man skal ikke kunne bruke resept med 0 reit" : "Mislyktes, alt er riktig - man kan ikke bruke resept med 0 reit"));
    System.out.println("Resept2 reit, skal være 3: " + (resept2.hentReit()));

    //Tester pris på militærresept og hvit resept
    System.out.println("**Tester pris å betale**");
    System.out.println("Henter pris å betale på militærresept, skal være 0: " + resept2.prisÅBetale());
    System.out.println("Henter pris å betale på vanlig hvit resept, skal være 180: " + resept1.prisÅBetale());
    }
}
