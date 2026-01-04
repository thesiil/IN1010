import java.util.*;
import java.io.*;

public class Legesystem {
    
    IndeksertListe<Pasient> pasientliste = new IndeksertListe<>();
    IndeksertListe<Legemiddel> legemiddelliste = new IndeksertListe<>();
    Prioritetskø<Lege> legeliste = new Prioritetskø<>();
    IndeksertListe<Resept> reseptliste = new IndeksertListe<>();

    public void lesInnFraFil(String filnavn){
        try(Scanner lesFil = new Scanner(new File(filnavn))){ //ny Scanner objekt
            String kategori = "";
            while (lesFil.hasNextLine()){ //leser filen mens det er flere linjer igjen
                String linje = lesFil.nextLine().trim(); //henter neste linje, fjerner whitespace

                if (linje.isEmpty()) continue; //hoppe over om linja er tom

                //hent ut kategoriene på resept, lege osv
                if (linje.contains("# Pasienter")){
                    kategori = "pasienter"; 
                    continue;
                } else if (linje.contains("# Legemidler")){
                    kategori = "legemidler";
                    continue;
                }else if (linje.contains("# Leger")){
                    kategori = "leger";
                    continue;
                }else if (linje.contains("# Resepter")){
                    kategori = "resepter";
                    continue;
                }

                String[] detaljer = linje.split(","); //henter ut detaljene på linja ved å skille på komma
                try{
                    switch(kategori){
                        case "pasienter":
                            if (detaljer.length < 2) {throw new IllegalArgumentException("Ugyldig format på pasient");} //sjekker at linja inneholder minst to ting, gjentas på hver kategori med tilsvarende antall
                            pasientliste.leggTil(new Pasient(detaljer[0].trim(), detaljer[1].trim())); //legger til basert på indeks på linja
                            break;

                        case "legemidler":
                            if (detaljer.length < 4){throw new IllegalArgumentException("Ugyldig format på legemiddel");}
                            String navn = detaljer[0].trim();
                            String type = detaljer[1].trim();
                            int pris = Integer.parseInt(detaljer[2].trim());
                            double virkestoff = Double.parseDouble(detaljer[3].trim());
                            int styrke = (detaljer.length > 4) ? Integer.parseInt(detaljer[4].trim()) : 0; // Sjekk om det er styrke/indeks 4, hvir ikke så settes den til 0
                                
                            if (type.equalsIgnoreCase("vanlig")){ //legger til legemidler basert på type
                                legemiddelliste.leggTil(new Vanlig(navn, pris, virkestoff));
                            } else if (type.equalsIgnoreCase("narkotisk")){
                                legemiddelliste.leggTil(new Narkotisk(navn, pris, virkestoff, styrke));
                            }else if (type.equalsIgnoreCase("vanedannende")){
                                legemiddelliste.leggTil(new Vanedannende(navn, pris, virkestoff, styrke));
                            }
                            break;
                        
                        case "leger":
                            if (detaljer.length < 2) {throw new IllegalArgumentException("Ugyldig format på lege");}
                            if (detaljer[1].equals("0")){ //legger til leger basert på type lege
                                legeliste.leggTil(new Lege(detaljer[0]));
                            } else{
                                legeliste.leggTil(new Spesialist(detaljer[0], detaljer[1]));
                            }
                            break;
                        case "resepter":
                            if (detaljer.length < 4) {throw new IllegalArgumentException("Ugyldig format på resept");}
                            Legemiddel legemiddel = legemiddelliste.hent(Integer.parseInt(detaljer[0]));
                            Lege lege = finnLegeEtterNavn(detaljer[1].trim(), legeliste);
                            Pasient pasient = pasientliste.hent(Integer.parseInt(detaljer[2]));
                            int reit = (detaljer.length > 4) ? Integer.parseInt(detaljer[4]) : 0; //sjekker om det finnes reit, ellers setter den til 0 (mtp militærresept)
                            
                            try { //kunne hatt switch-case her, men gikk for if for å klare beholde egen logikk
                                if (detaljer[3].trim().equalsIgnoreCase("hvit")) {
                                    reseptliste.leggTil(lege.skrivHvitResept(legemiddel, pasient, reit));
                                } else if (detaljer[3].trim().equalsIgnoreCase("blaa")) {
                                    reseptliste.leggTil(lege.skrivBlåResept(legemiddel, pasient, reit));
                                } else if (detaljer[3].trim().equalsIgnoreCase("p")) {
                                    reseptliste.leggTil(lege.skrivPResept(legemiddel, pasient, reit));
                                } else if (detaljer[3].trim().equalsIgnoreCase("militaer")) {
                                    reseptliste.leggTil(lege.skrivMilResept(legemiddel, pasient));
                                } else {
                                    throw new IllegalArgumentException("Ugyldig resepttype" + detaljer[3]);
                                }
                            } catch (UlovligUtskrift e) {
                                System.out.println("Feil: Ikke lov å skrive ut den resepten.");
                            } catch (NumberFormatException e) { //catche unntak for om parseint ikke går bra
                                System.out.println("Feil: Ugyldig tallformat i reseptlinje.");
                            }
                            break;
                        }
                } catch (NumberFormatException e){
                    System.err.println("Feil i nummerformat på linje " + linje + e.getMessage());
                }
            }
        } catch (FileNotFoundException e){
                System.err.println("Filen ikke funnet, sjekk filnavn: " + filnavn);
            }
    }

    public void hovedinteraksjon(){
        try (Scanner input = new Scanner(System.in)){
        
            while (true){
            System.out.println("\nAlternativer:\n0: Avslutt\n1: Skriv ut alle data\n2: Skriv ut statistikk\n3: Legg til lege\n4: Legg til legemiddel\n5: Legg til pasient\n6: Legg til resept\n7: Bruk resept\n8: Skriv ut alle data på fil\n");
            
            if (!input.hasNextInt()){ //sjekk at man har tastet inn et tall
                System.out.println("Ugyldig input. Skriv et tall mellom 0-8.");
                input.next();
                continue;
            }
            int inp = Integer.parseInt(input.nextLine());

            if (inp < 0 || inp > 8){ //sjekk at man har tastet inn gyldig tall
                System.out.println("Ugyldig valg. Skriv et tall mellom 0-8.");
                continue;
            }

            switch (inp) { //ulike options
                case 0:
                    System.out.println("Programmet avsluttes");
                    input.close();
                    return;
                
                case 1:
                    System.out.println("Pasienter: ");
                    for (Pasient pasient : pasientliste){
                        System.out.println(pasient);
                    }
                    System.out.println("Legemidler: ");
                    for (Legemiddel legem : legemiddelliste){
                        System.out.println(legem);
                    }
                    System.out.println("Leger: ");
                    for (Lege lege : legeliste){
                        System.out.println(lege);
                    }
                    System.out.println("Resepter: ");
                    for (Resept res : reseptliste){
                        System.out.println(res);
                    }
                    
                    break;

                case 2: // statistikk: antall utskrevne resepter vanedannende, antall utskevne resepter narkotiske, leger som har skrevet narkotiske+antall, pasienter med narkotiske resepter
                    int vaneTeller = tellResepterAvType(Vanedannende.class);
                    int narkTeller = tellResepterAvType(Narkotisk.class);
                    StringBuilder leger = new StringBuilder("Leger som har skrevet ut resept på narkotiske: ");
                    StringBuilder pasienter = new StringBuilder("Pasienter som har fått resept på narkotiske: ");
                    
                    for (Lege lege : legeliste){
                        if (lege.tellNarkotiskeResepter() != 0){
                            leger.append("\n -> " + lege + " (" + lege.tellNarkotiskeResepter() + " stk)");
                        }else{
                            leger.append("\n -> Ingen leger med narkotiske resepter.");
                        }
                    }
                    for (Pasient pasient : pasientliste){
                        if (pasient.tellNarkotiskeResepter() != 0){
                            pasienter.append("\n -> " + pasient + " (" + pasient.tellNarkotiskeResepter() + " stk)");
                        }else{
                            pasienter.append("\n -> Ingen pasienter med narkotiske resepter.");
                        }
                    }
                    System.out.println("Totalt " + vaneTeller + " resepter på vanedannende");
                    System.out.println("Totalt " + narkTeller + " resepter på narkotiske");
                    System.out.println(leger);
                    System.out.println(pasienter);
        
                    break;

                case 3: // legg til lege
                    System.out.print("Hva er navnet på legen?: ");
                    String lnavn = input.nextLine();
                    
                    System.out.print("Kontroll-id? (Skriv 0 hvis legen ikke er en spesialist): ");
                    String kNummer = input.nextLine();
                    if (kNummer == "0"){
                        legeliste.leggTil(new Lege(lnavn));
                    }else{
                        legeliste.leggTil(new Spesialist(lnavn, kNummer));
                    }
                    System.out.println("Lege lagt til");
                    break;
                
                case 4: // legg til legemiddel
                    System.out.print("Hva er navnet på legemiddelet?: ");
                    String mnavn = input.nextLine();

                    System.out.println("Hva er prisen?");
                    int mpris = Integer.parseInt(input.nextLine());
                    
                    System.out.print("Mengde virkestoff: ");
                    Double virkestoff = Double.parseDouble(input.nextLine());

                    System.out.println("Type på legemiddel: 1. vanlig, 2. narkotisk, 3. vanedannende?");
                    int type = Integer.parseInt(input.nextLine());
                    switch (type) {
                        case 1:
                            legemiddelliste.leggTil(new Vanlig(mnavn, mpris, virkestoff));
                            break;
                        case 2:
                            System.out.println("Hva er styrken på narkotiske middelet?");
                            int nstyrke = Integer.parseInt(input.nextLine());
                            legemiddelliste.leggTil(new Narkotisk(mnavn, mpris, mpris, nstyrke));
                            break;
                        case 3:
                            System.out.println("Hva er styrken på vanedannende middelet?");
                            int vstyrke = Integer.parseInt(input.nextLine());
                            legemiddelliste.leggTil(new Narkotisk(mnavn, mpris, mpris, vstyrke));
                            break;
                    
                        default:
                            System.out.println("Ugyldig input, skriv et tall mellom 1-3");
                            type = Integer.parseInt(input.nextLine());
                            break;
                    }

                    break;

                case 5: //legg til pasient
                    System.out.println("Hva er navnet på pasienten?");
                    String pnavn = input.nextLine();
                    System.out.println("Hva er fødselsnummeret?");
                    String pfødselsnummer = input.nextLine();

                    pasientliste.leggTil(new Pasient(pnavn, pfødselsnummer));
                    break;

                case 6: // legg til resept
                    System.out.println("Legemiddel? ");
                    String l = input.nextLine();
                    Legemiddel detteLegemiddel = fillLegemiddelEtterNavn(l, legemiddelliste); //finne legemiddelet som skrives ut
                    System.out.println("Lege? ");
                    String leg = input.nextLine();
                    Lege denneLegen = finnLegeEtterNavn(leg, legeliste); //finne legen som skriver ut
                    
                    int pID; //finne pasienten
                    while (true){
                        try{
                            System.out.println("Pasient-ID? ");
                            pID = Integer.parseInt(input.nextLine());

                            if (pID < 0){ //sjekker at pasientid blir gyldig
                                System.out.println("Ugyldig input. Tallet må være positivt");
                                continue;
                            }

                            pasientliste.hent(pID); //kommer man hit så er det gyldig, ellers håndteres det nedenfor
                            
                            break;
                            
                        }catch (NumberFormatException e){
                            System.out.println("Ugyldig input, du må skrive inn et heltall");
                        } catch (UgyldigListeindeks e){
                            System.out.println("Pasient med indeks " + e.getMessage() + " finnes ikke, prøv igjen");
                        }
                    }

                    int alt; //hvilken resept som skal legges til
                    while (true){
                        try{
                            System.out.println("Alternativer:\n0. hvit resept\n1. blå resept\n2. militærresept\n3. resept på prevensjon");
                            alt = Integer.parseInt(input.nextLine());

                            if (alt < 0 || alt > 3) {
                                throw new IllegalArgumentException("Du må skrive inn et tall mellom 0-3");
                            }
                            break;
                        } catch (NumberFormatException e){
                            System.out.println("Du må skrive inn et heltall");
                            input.nextLine();
                        } 
                    }
                    // Forsøker å legge til resept
                        try {
                            if (alt == 2) { // Militærresept har ikke reit fra input
                                reseptliste.leggTil(denneLegen.skrivMilResept(detteLegemiddel, pasientliste.hent(pID)));
                            } else {
                                int reit;
                                while (true) { // Reit fra input
                                    try {
                                        System.out.print("Reit? ");
                                        reit = Integer.parseInt(input.nextLine().trim());

                                        if (reit < 0) {
                                            System.out.println("Reit må være et positivt tall, prøv igjen.");
                                            continue;
                                        }
                                        break;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Du må skrive inn et heltall.");
                                    }
                                }
                            switch (alt) {
                                case 0:
                                    reseptliste.leggTil(denneLegen.skrivHvitResept(detteLegemiddel, pasientliste.hent(pID), reit));
                                    break;
                                
                                case 1:
                                    reseptliste.leggTil(denneLegen.skrivBlåResept(detteLegemiddel, pasientliste.hent(pID), reit));
                                    break;

                                case 3:
                                    reseptliste.leggTil(denneLegen.skrivPResept(detteLegemiddel, pasientliste.hent(pID), reit));
                                    break;
                            }
                        }
                    }catch (Exception e){
                        System.out.println("Det oppsto en feil: " + e.getMessage());
                    }
                    break;

                case 7: // bruk resept
                    System.out.println("Resept ID?");
                    int indeks;
                    try {indeks = Integer.parseInt(input.nextLine());
                        reseptliste.hent(indeks).bruk();
                    } catch (NumberFormatException e){
                        System.out.println("Du må skrive inn et heltall, prøv igjen");
                    } catch (UgyldigListeindeks e){
                        System.out.println("Ugyldig ID, prøv igjen");
                    }
                       
                    break;

                case 8: // skrive ut alle data på fil
                String fnavn; // lar bruker bestemme filnavnet
                while (true) {
                    System.out.println("Navnet på filen du vil skrive ut? f.eks. data.txt");
                    fnavn = input.nextLine();
                
                    if (fnavn.endsWith(".txt")) {
                        break;  // Avslutter løkken hvis filnavnet er riktig
                    } else {
                        System.out.println("Feil: Filen må ha .txt som endelse. Prøv igjen.");
                    }
                }

                    PrintWriter f = null; //ny printwriter for å skrive til fil
                    try {
                        f = new PrintWriter(fnavn);
                    } catch (Exception e){
                        System.out.println("Feil ved oppretting av fil: " + e.getMessage());
                    }

                    //legger til  hver av kategoriene først, før iterering gjennom objektene
                    f.write("# Pasienter (navn, fnr)"); 
                    if (pasientliste == null || pasientliste.størrelse() == 0){
                        f.write("\nIngen pasienter i listen");
                    } else {
                        for (Pasient pasient : pasientliste){
                                f.write("\n" + pasient);
                        }
                    }

                    f.write("\n# Legemidler (navn,type,pris,virkestoff,[styrke])");
                    if (legemiddelliste == null || legemiddelliste.størrelse() == 0){
                        f.write("\nIngen legemidler i listen");
                    } else {
                        for (Legemiddel legemiddel : legemiddelliste){
                            f.write("\n" + legemiddel);
                        }
                    }

                    f.write("\n# Leger (navn,kontrollid / 0 hvis vanlig lege)");
                    if (legeliste == null || legeliste.størrelse() == 0){
                        f.write("\nIngen leger i listen");
                    } else {
                        for (Lege lege : legeliste){
                            f.write("\n" + lege);
                        }
                    }
                    
                    f.write("\n# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])");
                    if (reseptliste == null || reseptliste.størrelse() == 0){
                        f.write("\nIngen resepter i listen");
                    } else {
                        for (Resept resept : reseptliste){
                            f.write("\n" + resept);
                        }
                    }
                    f.close();
                    break;

                default:
                    System.out.println("Ugyldig input, du kan skrive et tall mellom 0-8");
                    break;
            }
        
            }   
        }       
    }
    public int tellResepterAvType(Class<?> type) { //hjelpemetode for å telle instanser av legemidler blant resepter. Returnerer antallet av instanser av type legemiddel
        if (type == null){throw new IllegalArgumentException("Typen kan ikke være tom");}
        int teller = 0;
        if (reseptliste != null) { //sjekker at lista ikke er tom
            for (Resept res : reseptliste) {
                if (type.isInstance(res.hentLegemiddel())) { //isInstance ikke instanceOf for å unngå kompileringsfeil, sjekker type under kjøring
                    teller++;
                }
            }
        }
        return teller;
    }

    public Lege finnLegeEtterNavn(String legeNavn, Prioritetskø<Lege> legeliste) { //hjelpemetode for å finne legeobjektet i lista for å legge til resept på objektet
        if (legeliste.antall() != 0){
            for (Lege lege : legeliste) { //itererer over listen
                if (lege.hentNavn().equalsIgnoreCase(legeNavn.trim())) {
                    return lege; // Lege found
                }
            }
        } throw new IllegalArgumentException("Legen med navn '" + legeNavn + "' ble ikke funnet."); //vurder om dette er fornuftig her
    }

    public Legemiddel fillLegemiddelEtterNavn(String lnavn, IndeksertListe<Legemiddel> lliste){
        if (lliste.antall() != 0){
            for (Legemiddel legemiddel : legemiddelliste) { //itererer over listen
                if (legemiddel.hentNavn().equalsIgnoreCase(lnavn.trim())) {
                    return legemiddel; // Lege found
                }
            }
        } throw new IllegalArgumentException("Legemiddelet med navn '" + lnavn + "' ble ikke funnet."); //vurder om dette er fornuftig her
    
    }

    public static void main(String[] args) {
        Legesystem sys = new Legesystem();
        sys.lesInnFraFil(args[0]);
        sys.hovedinteraksjon();
    }
}