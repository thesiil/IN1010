import java.util.Scanner;

class GameOfLife{
    public static void main (String[] args){
        Scanner input = new Scanner(System.in);
        GameOfLife spill = new GameOfLife();
        spill.startSpill(input);
        input.close();
    }

    public void startSpill(Scanner input){
        System.out.println("Velkommen! Hvor mange rader skal brettet ha (heltall)?\n");
        int rad = Integer.parseInt(input.nextLine());
        
        System.out.println("\nOg hvor mange kolonner (heltall)?\n ");
        int kolonne = Integer.parseInt(input.nextLine());
        
        Verden verden = new Verden(rad, kolonne);
        fortsettSpillet(verden, input); 
    }

    public void fortsettSpillet(Verden verden, Scanner input){
        System.out.println("Trykk enter for 0-te generasjon, eller x for å avslutte: ");
        String inp = input.nextLine();
        
        while (!inp.equalsIgnoreCase("x")) {
            verden.tegn();
            verden.oppdatering();
            System.out.println("Trykk enter for neste generasjon, eller x for å avslutte: ");
            inp = input.nextLine();
        }    
    }
}