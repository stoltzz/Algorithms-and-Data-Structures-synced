package hjelpeklasser;

public class Main2 {

    public static void main(String[] args) {

        int kajaAlder = 32 ;
        boolean kvinne = true ;
        char tegn = 'A' ;
        double kajaHøyde = 1.68 ;
        String kajaAdresse = "Skiferveien2";

        if (kvinne != false && kajaHøyde < 1.67) {
            System.out.println("kaja er " + kajaAlder + " år gammel og hun er " + kajaHøyde + " cm. høy.");
        } else {
            System.out.println("kaja bor i " + kajaAdresse + ".");
        }

        /*
        while (kajaAlder > 30) {
            System.out.println("Kaja er " + kajaAlder + " år gammel."); // gfghjhjfhghhdgfhd
            kajaAlder--; // Tatt i bruk igjen.
        }*/

        for (int tellerAntallLoop = 0 ; tellerAntallLoop <= kajaAlder ; tellerAntallLoop++  ) {
            System.out.println("Kaja blir eldre, og er nå: " + tellerAntallLoop + " år gammel.");
        }


    }

}
