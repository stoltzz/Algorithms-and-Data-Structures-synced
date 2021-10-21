package hjelpeklasser;

import eksempelklasser.Komparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

// Samleklasse for tabellmetoder
public class Tabell {


    // Privat standardkonstruktør - hindrer instansiering
    private Tabell() {}


    // Metoden for å bytte om på to verdier i en array
    public static void bytt(int[] a, int i, int j)
    {
        int temp = a[i]; a[i] = a[j]; a[j] = temp;
    }


    // Metoden for å bytte om på to chars i en array
    public static void bytt(char[] a, int i, int j)
    {
        char temp = a[i]; a[i] = a[j]; a[j] = temp;
    }


    // Metode for å bytte om to generiske typer
    public static <T> void bytt(T[] a, int i, int j)
    {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    // snur intervallet a[v:h]
    public static void snu(int[] a, int v, int h)
    {
        while (v < h) bytt(a, v++, h--);
    }


    // snur fra og med v og ut tabellen
    public static void snu(int[] a, int v)
    {
        snu(a, v, a.length - 1);
    }


    // snur hele tabellen
    public static void snu(int[] a)
    {
        snu(a, 0, a.length - 1);
    }


    // Metode for å lage en permutasjon av en array med lengde n
    public static int[] randPerm(int n)  // en effektiv versjon
    {
        Random r = new Random();         // en randomgenerator
        int[] a = new int[n];            // en tabell med plass til n tall

        Arrays.setAll(a, i -> i + 1);    // legger inn tallene 1, 2, . , n

        for (int k = n - 1; k > 0; k--)  // løkke som går n - 1 ganger
        {
            int i = r.nextInt(k+1);        // en tilfeldig tall fra 0 til k
            bytt(a,k,i);                   // bytter om
        }

        return a;                        // permutasjonen returneres
    }


    // Metode for å lage en permutasjon av en Integer-array med lengde n
    public static Integer[] randPermInteger(int n)
    {
        Integer[] a = new Integer[n];               // en Integer-tabell
        Arrays.setAll(a, i -> i + 1);               // tallene fra 1 til n

        Random r = new Random();   // hentes fra  java.util

        for (int k = n - 1; k > 0; k--)
        {
            int i = r.nextInt(k+1);  // tilfeldig tall fra [0,k]
            bytt(a,k,i);             // bytter om
        }
        return a;  // tabellen med permutasjonen returneres
    }


    // Metode for å stokke om på verdiene i en array
    public static void randPerm(int[] a)  // stokker om a
    {
        Random r = new Random();     // en randomgenerator

        for (int k = a.length - 1; k > 0; k--)
        {
            int i = r.nextInt(k + 1);  // tilfeldig tall fra [0,k]
            bytt(a,k,i);
        }
    }


    //
    public static boolean nestePermutasjon(int[] a)
    {
        int i = a.length - 2;                    // i starter nest bakerst
        while (i >= 0 && a[i] > a[i + 1]) i--;   // går mot venstre
        if (i < 0) return false;                 // a = {n, n-1, . . . , 2, 1}

        int j = a.length - 1;                    // j starter bakerst
        while (a[j] < a[i]) j--;                 // stopper når a[j] > a[i]
        bytt(a,i,j); snu(a,i + 1);               // bytter og snur

        return true;                             // en ny permutasjon
    }


    // Metode for å finne maksimumsverdi med gitte grenser (halvåpent intervall)
    public static int maks(int[] a, int fra, int til)
    {
        if (a == null) {
            throw new NullPointerException("Tabellen er null");
        }

        fratilKontroll(a.length, fra, til);

        if (fra == til) {
            throw new NoSuchElementException("fra(" + fra + ") = til(" + til + ") - tomt tabellintervall!");
        }

        int m = fra;              // indeks til største verdi i a[fra:til>
        int maksverdi = a[fra];   // største verdi i a[fra:til>

        for (int i = fra + 1; i < til; i++)
        {
            if (a[i] > maksverdi)
            {
                m = i;                // indeks til største verdi oppdateres
                maksverdi = a[m];     // største verdi oppdateres
            }
        }

        return m;  // posisjonen til største verdi i a[fra:til>
    }


    // Metode for å finne maksimumsverdi ved bruk av hele arrayen
    public static int maks(int[] a)  // bruker hele tabellen
    {
        return maks(a,0,a.length);     // kaller metoden over
    }


    // Metode for å finne maksimumsverdi en double-array
    public static int maks(double[] a)     // legges i class Tabell
    {
        int m = 0;                           // indeks til største verdi
        double maksverdi = a[0];             // største verdi

        for (int i = 1; i < a.length; i++) if (a[i] > maksverdi)
        {
            maksverdi = a[i];     // største verdi oppdateres
            m = i;                // indeks til største verdi oppdaters
        }
        return m;     // returnerer posisjonen til største verdi
    }


    // Metode for å finne maksimumsverdi en char-array
    public static int maks(char[] a)     // legges i class Tabell
    {
        int m = 0;                           // indeks til største verdi
        char maksverdi = a[0];             // største verdi

        for (int i = 1; i < a.length; i++) if (a[i] > maksverdi)
        {
            maksverdi = a[i];     // største verdi oppdateres
            m = i;                // indeks til største verdi oppdaters
        }
        return m;     // returnerer posisjonen til største verdi
    }


    // Metode for å finne maksimumsverdi av en Integer-array
    public static int maks(Integer[] a)    // legges i class Tabell
    {
        int m = 0;                          // indeks til største verdi
        Integer maksverdi = a[0];            // største verdi

        for (int i = 1; i < a.length; i++) if (a[i].compareTo(maksverdi) > 0)
        {
            maksverdi = a[i];  // største verdi oppdateres
            m = i;             // indeks til største verdi oppdaters
        }
        return m;  // returnerer posisjonen til største verdi
    }


    // Metode for å finne maksimumsverdi med T som referansetype
    public static <T extends Comparable<? super T>> int maks(T[] a)
    {
        int m = 0;                     // indeks til største verdi
        T maksverdi = a[0];            // største verdi

        for (int i = 1; i < a.length; i++) if (a[i].compareTo(maksverdi) > 0)
        {
            maksverdi = a[i];  // største verdi oppdateres
            m = i;             // indeks til største verdi oppdaters
        }
        return m;  // returnerer posisjonen til største verdi
    } // maks


    // Metode for å finne maksimumsverdi med T som referansetype og bruk av Komparator
    public static <T> int maks(T[] a, int fra, int til, Comparator<? super T> c)
    {
        fratilKontroll(a.length, fra, til);

        if (fra == til) {
            throw new NoSuchElementException("fra(" + fra + ") = til(" + til + ") - tomt tabellintervall!");
        }

        int m = 0;                     // indeks til største verdi
        T maksverdi = a[fra];            // største verdi

        for (int i = 1; i < a.length; i++) {
            if (c.compare(a[i], maksverdi) > 0)     // bruker komparatoren
            {
                maksverdi = a[i];  // største verdi oppdateres
                m = i;             // indeks til største verdi oppdaters
            }
        }
        return m;  // returnerer posisjonen til største verdi
    } // maks


    // Metode for å finne de to største verdiene
    public static int[] nestMaks(int[] a)
    {
        int n = a.length;   // tabellens lengde

        if (n < 2) throw   // må ha minst to verdier!
                new java.util.NoSuchElementException("a.length(" + n + ") < 2!");

        int m = maks(a);  // m er posisjonen til tabellens største verdi

        int nm;           // nm skal inneholde posisjonen til nest største verdi

        if (m == 0)                            // den største ligger først
        {
            nm = maks(a, 1, n);                  // leter i a[1:n>
        }
        else if (m == n - 1)                   // den største ligger bakerst
        {
            nm = maks(a, 0, n - 1);              // leter i a[0:n-1>
        }
        else
        {
            int mv = maks(a, 0, m);              // leter i a[0:m>
            int mh = maks(a, m + 1, n);          // leter i a[m+1:n>
            nm = a[mh] > a[mv] ? mh : mv;        // hvem er størst?
        }

        return new int[] {m,nm};      // m i posisjon 0 , nm i posisjon 1

    } // nestMaks


    // Metode for å finne de to største verdiene. Den største verdien legges forrest (oppg. 1.2.4.2)
    public static int[] nestMaks2(int[] a)
    {
        int n = a.length;   // tabellens lengde

        if (n < 2) throw   // må ha minst to verdier!
                new java.util.NoSuchElementException("a.length(" + n + ") < 2!");

        int m = maks(a);  // m er posisjonen til tabellens største verdi

        bytt(a,0,m); // bytter om slik at den største kommer forrest

        int nm = maks(a, 1, n);           // nm skal inneholde posisjonen til nest største verdi

        if (nm == m) nm = 0;    // Hvis nm har samme indeks som m betyr det at nm lå på indeks 0

        bytt(a,0,m);    // bytter tilbake

        return new int[] {m,nm};      // m i posisjon 0 , nm i posisjon 1

    } // nestMaks2


    // Metode for å finne de to største verdiene. Den største verdien legges bakerst (oppg. 1.2.4.3)
    public static int[] nestMaks3(int[] a)
    {
        int n = a.length;   // tabellens lengde

        if (n < 2) throw   // må ha minst to verdier!
                new java.util.NoSuchElementException("a.length(" + n + ") < 2!");

        int m = maks(a);  // m er posisjonen til tabellens største verdi

        bytt(a,m,n-1); // bytter om slik at den største kommer bakerst

        int nm = maks(a, 0, n-1); // nm skal inneholde posisjonen til nest største verdi

        if (nm == m) nm = n-1; // hvis nm = n-1 betyr det at nest største verdi lå bakerst, settes da til n-1

        bytt(a,n-1,m);    // bytter tilbake

        return new int[] {m,nm};      // m i posisjon 0 , nm i posisjon 1

    } // nestMaks3


    public static int[] nestMaks4(int[] a) // ny versjon (oppg. 1.2.6.1)
    {
        int n = a.length;     // tabellens lengde
        if (n < 2) throw      // må ha minst to verdier
                new java.util.NoSuchElementException("a.length(" + n + ") < 2!");

        int m = 0;      // m er posisjonen til største verdi
        int nm = 1;     // nm er posisjonen til nest største verdi
        int count = 0;

        // bytter om m og nm hvis a[1] er større enn a[0]
        if (a[1] > a[0]) { m = 1; nm = 0; }
        count++;

        int maksverdi = a[m];                // største verdi
        int nestmaksverdi = a[nm];           // nest største verdi

        for (int i = 2; i < n; i++)
        {
            if (a[i] > nestmaksverdi)
            {
                count++;
                if (a[i] > maksverdi)
                {
                    count++;
                    nm = m;
                    nestmaksverdi = maksverdi;     // ny nest størst

                    m = i;
                    maksverdi = a[m];              // ny størst
                }
                else
                {
                    count++;
                    nm = i;
                    nestmaksverdi = a[nm];         // ny nest størst
                }
            }
        } // for
        System.out.println(count);
        return new int[] {m,nm};    // n i posisjon 0, nm i posisjon 1

    } // nestMaks4


    // Turneringsalgoritme
    public static int[] nestMaks5(int[] a)   // en turnering
    {
        int n = a.length;                // for å forenkle notasjonen

        if (n < 2) // må ha minst to verdier!
            throw new IllegalArgumentException("a.length(" + n + ") < 2!");

        int[] b = new int[2*n];          // turneringstreet
        System.arraycopy(a,0,b,n,n);     // legger a bakerst i b

        for (int k = 2*n-2; k > 1; k -= 2)   // lager turneringstreet
            b[k/2] = Math.max(b[k],b[k+1]);
        System.out.println(Arrays.toString(b));

        int maksverdi = b[1], nestmaksverdi = Integer.MIN_VALUE;

        for (int m = 2*n - 1, k = 2; k < m; k *= 2)
        {
            int tempverdi = b[k+1];  // ok hvis maksverdi er b[k]
            if (maksverdi != b[k]) { tempverdi = b[k]; k++; }
            if (tempverdi > nestmaksverdi) nestmaksverdi = tempverdi;
        }

        return new int[] {maksverdi,nestmaksverdi}; // størst og nest størst

    } // nestMaks5


    // Metode for å finne minimumsverdi med gitte grenser
    public static int min(int[] a, int fra, int til)
    {
        if (fra < 0 || til > a.length || fra >= til)
        {
            throw new IllegalArgumentException("Illegalt intervall!");
        }

        int minIndeks = fra;        // indeks til minste verdi i a[fra:til>
        int minVerdi = a[fra];      // minste verdi i a[fra:til>

        for (int i = fra + 1; i < til; i++)
        {
            if (a[i] < minVerdi)
            {
                minIndeks = i;              // indeks til minste verdi oppdateres
                minVerdi = a[minIndeks];    // minste verdi oppdateres
            }
        }

        return minIndeks;  // posisjonen til minste verdi i a[fra:til>
    }


    // Metode for å finne minimumsverdi med gitte grenser, med Comparator
    public static <T> int min(T[] a, int fra, int til, Comparator<? super T> c)
    {
        if (fra < 0 || til > a.length || fra >= til)
        {
            throw new IllegalArgumentException("Illegalt intervall!");
        }

        int minIndeks = fra;        // indeks til minste verdi i a[fra:til>
        T minVerdi = a[fra];      // minste verdi i a[fra:til>

        for (int i = fra + 1; i < til; i++)
        {
            if (c.compare(a[i], minVerdi) < 0)
            {
                minIndeks = i;              // indeks til minste verdi oppdateres
                minVerdi = a[minIndeks];    // minste verdi oppdateres
            }
        }
        return minIndeks;  // posisjonen til minste verdi i a[fra:til>
    }


    // Metode for å finne minimumsverdi ved bruk av hele arrayen
    public static int min(int[] a)  // bruker hele tabellen
    {
        return min(a,0,a.length);     // kaller metoden over
    }


    // Metode som kopierer array inn i et annet array fra og til angitte posisjoner
    public static void kopier(int[] a, int i, int[] b, int j, int ant) {
        if (a == null || b == null) {
            throw new NullPointerException("En av arrayene er null");
        }
        if (i < 0 || i >= a.length || j < 0 || j >= b.length || ant < 0 ||
            i + ant > a.length || j + ant > b.length) {
            throw new IndexOutOfBoundsException("Feil input...");
        }

        for (i = i; i < ant; i++, j++) {
            b[j] = a[i];
        }
    }


    // Kontrollmetode av halvåpent tabellintervall
    public static void fratilKontroll(int lengde, int fra, int til) { // tar inn lengde av array, til og fra
        if (fra < 0) {
            throw new ArrayIndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }

        if (til > lengde) {
            throw new ArrayIndexOutOfBoundsException("til(" + til + ") er utenfor tabellen");
        }

        if (fra > til) {
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall");
        }
    }

    // Kontrollmetode av lukket tabellintervall
    public static void vhKontroll(int tablengde, int v, int h)
    {

        if (v < 0)
            throw new ArrayIndexOutOfBoundsException("v(" + v + ") < 0");

        if (h >= tablengde)
            throw new ArrayIndexOutOfBoundsException
                    ("h(" + h + ") >= tablengde(" + tablengde + ")");

        if (v > h + 1)
            throw new IllegalArgumentException
                    ("v = " + v + ", h = " + h);
    }

// UTSKRIFTSMETODER


    // Skriver ut heltall fra et array i et gitt intervall
    public static void skriv(int[] a, int fra, int til) {
        fratilKontroll(a.length, fra, til);
        if (til - fra > 0) {
            System.out.print(a[fra]);
        }
        for (int i = fra+1; i < til; i++) {
            System.out.print(" " + a[i]);
        }
    }


    // Skriver ut ett heltallsarray
    public static void skriv(int[] a) {
        skriv(a, 0, a.length);
    }


    // Skriver ut heltall fra et array i et gitt intervall, med linjeskift til slutt
    public static void skrivln(int[] a, int fra, int til) {
        skriv(a, fra, til);
        System.out.println();
    }


    // Skriver ut ett heltallsarray, med linjeskift til slutt
    public static void skrivln(int[] a) {
        skrivln(a, 0, a.length);
    }


    // Skriver ut en array med objekter i halvåpent intervall
    public static void skriv(Object[] a, int fra, int til) {

        fratilKontroll(a.length, fra, til);

        for (int i = fra; i < til; i++) {
            if (i != til - 1) {
                System.out.print(a[i] + " ");
            } else {
                System.out.print(a[i]);
            }
        }
    }


    // Skriver ut hele arrayen med objekter
    public static void skriv(Object[] a) {
        skriv(a, 0, a.length);
    }


    // Skriver ut en array med objekter i halvåpent intervall, avslutter med linjeskift
    public static void skrivln(Object[] a, int fra, int til) {
        skriv(a, fra, til);
        System.out.println();
    }


    // Skriver ut hele arrayen med objekter, avslutter med linjeskift
    public static void skrivln(Object[] a) {
        skrivln(a, 0, a.length);
    }


// SORTERINGSALGORITMER++


    // Sjekker om tabellen er sortert stigende
    public static boolean erSortert(int[] a)
    {
        for (int i = 1; i < a.length; i++)      // starter med i = 1
            if (a[i-1] > a[i]) return false;      // en inversjon

        return true;
    } // erSortert


    // Teller antall inversjoner
    public static int inversjoner(int[] a)
    {
        int antall = 0;  // antall inversjoner
        for (int i = 0; i < a.length - 1; i++)
        {
            for (int j = i + 1; j < a.length; j++)
            {
                if (a[i] > a[j]) antall++;  // en inversjon siden i < j
            }
        }
        return antall;
    }


    // Metode for å sortere en tabell ved at største verdi legges bakerst (oppg. 1.2.4.4)
    public static void sortering(int[] a)
    {
        for (int i = a.length; i > 1; i--) {    // i > 1 fordi da blir siste sjekk maks(a, 0, 1)
            int m = Tabell.maks(a, 0, i); // m er posisjonen til tabellens største verdi i gitt intervall
            bytt(a, m, i-1);    // bytter om slik at den største kommer bakerst
        }
    } // sortering


    // boble - en gjennomgang med bobling gjennom en tabell
    public static int boble(int[] a)
    {
        int antall = 0;                     // antall ombyttinger i tabellen
        for (int i = 1; i < a.length; i++)  // starter med i = 1
        {
            if (a[i - 1] > a[i])              // sammenligner to naboverdier
            {
                bytt(a, i - 1, i);              // bytter om to naboverdier
                antall++;                       // teller opp ombyttingene
            }
        }
        return antall;                      // returnerer
    }


    // boblesortering - største verdi flyttes bakerst i tabellen hver runde
    public static void boblesortering(int[] a)
    {
        for (int n = a.length; n > 1; )            // n er intervallgrense
        {
            int byttindeks = 0;                      // hjelpevariabel
            for (int i = 1; i < n; i++)              // går fra 1 til n
            {
                if (a[i - 1] > a[i])                   // sammenligner
                {
                    bytt(a, i - 1, i);                   // bytter
                    byttindeks = i;                      // høyre indeks i ombyttingen
                }
            }
            n = byttindeks;                          // ny intervallgrense
        }
    }


    // utvalgssortering - starter fra i=0 med å finne minste verdi og bytter om. Fortsetter ut lengden av tabellen
    public static void utvalgssortering(int[] a)
    {
        for (int i = 0; i < a.length - 1; i++) {
            int m = min(a, i, a.length); // posisjonen til den minste
            if (m != i) {
                bytt(a, i, m);  // byttemetode
            }
        }
    }


    // utvalgssortering med Comparator - starter fra i=0 med å finne minste verdi og bytter om. Fortsetter ut lengden av tabellen
    public static <T> void utvalgssortering(T[] a, Comparator<? super T> c)
    {
        for (int i = 0; i < a.length - 1; i++) {
            int m = min(a, i, a.length, c); // posisjonen til den minste
            if (m != i) {
                bytt(a, i, m);  // byttemetode
            }
        }
    }


    // utvalgssortering - starter fra i=0 med å finne minste verdi og bytter om. Fortsetter ut lengden av tabellen
    public static void utvalgssortering(int[] a, int fra, int til)
    {
        Tabell.fratilKontroll(a.length, fra, til);

        for (int i = fra; i < til - 1; i++)
            bytt(a, i, min(a, i, til));  // to hjelpemetoder
    }



    // utvalgssorteringClean - uten hjelpemetoder
    public static void utvalgssorteringClean(int[] a) {

        int aLength = a.length;

        for (int i = 0; i < aLength - 1; i++) {
            int minIndeks = i;  // antar at første element er det minste
            int minVerdi = a[i]; // verdien til den foreløpig minste

            for (int j = i + 1; j < aLength; j++ ) {    // tester mot resten av tabellen for å finne minste
                if (a[j] < minVerdi) {  // tester om neste verdi er mindre enn den foreløpig minste verdien
                    minVerdi = a[j];    // ny minste verdi
                    minIndeks = j;      // indeks til ny minste verdi
                }
            }
            int temp = a[i];
            a[i] = a[minIndeks];
            a[minIndeks] = temp;
        }
    }


    // Innsettingssortering
    public static void innsettingssortering(int[] a)
    {
        for (int i = 1; i < a.length; i++)  // starter med i = 1
        {
            int verdi = a[i], j = i - 1;      // verdi er et tabellelemnet, j er en indeks
            for (; j >= 0 && verdi < a[j]; j--) a[j+1] = a[j];  // sammenligner og flytter
            a[j + 1] = verdi;                 // verdien settes inn på rett sortert plass
        }
    }


    // Innsettingssortering i halvåpent tabellintervall
    public static void innsettingssorteringFraTil(int[] a, int fra, int til)
    {
        fratilKontroll(a.length, fra, til);

        for (int i = fra + 1; i < til; i++)  // a[fra] er første verdi
        {
            int verdi = a[i], j = i - 1;      // flytter a[i] til en hjelpevariabel
            for (; j >= fra && verdi < a[j]; j--) a[j+1] = a[j];  // verdier flyttes inntil rett sortert plass i a[fra:i> er funnet
            a[j + 1] = verdi;                 // verdien settes inn på rett sortert plass
        }
    }


    // Innsettingssortering av T[]
    public static <T extends Comparable<? super T>> void innsettingssortering(T[] a)
    {
        for (int i = 1; i < a.length; i++)  // starter med i = 1
        {
            T verdi = a[i];        // verdi er et tabellelement
            int  j = i - 1;        // j er en indeks
            // sammenligner og forskyver:
            for (; j >= 0 && verdi.compareTo(a[j]) < 0 ; j--) a[j+1] = a[j];

            a[j + 1] = verdi;      // j + 1 er rett sortert plass
        }
    }

    // Innsettingssortering av T[] med Komparator
    public static <T> void innsettingssortering(T[] a, Comparator<? super T> c)
    {
        for (int i = 1; i < a.length; i++)  // starter med i = 1
        {
            T temp = a[i];        // verdi er et tabellelemnet
            int  j = i - 1;        // j er en indeks

            // sammenligner og forskyver:
            for (; j >= 0 && c.compare(temp,a[j]) < 0 ; j--) a[j+1] = a[j];

            a[j + 1] = temp;      // j + 1 er rett sortert plass
        }
    }


    // Kvikksorteringsmetoder med Comparator
    public static <T>
    int parter(T[] a, int v, int h, T skilleverdi, Comparator<? super T> c)
    {
        while (v <= h && c.compare(a[v],skilleverdi) < 0) v++;
        while (v <= h && c.compare(skilleverdi,a[h]) <= 0) h--;

        while (true)
        {
            if (v < h) Tabell.bytt(a,v++,h--); else return v;
            while (c.compare(a[v],skilleverdi) < 0) v++;
            while (c.compare(skilleverdi,a[h]) <= 0) h--;
        }
    }

    public static <T> int parter(T[] a, T skilleverdi, Comparator<? super T> c)
    {
        return parter(a,0,a.length-1,skilleverdi,c);  // kaller metoden over
    }

    public static <T>
    int sParter(T[] a, int v, int h, int k, Comparator<? super T> c)
    {
        if (v < 0 || h >= a.length || k < v || k > h) throw new
                IllegalArgumentException("Ulovlig parameterverdi");

        bytt(a,k,h);   // bytter - skilleverdien a[k] legges bakerst
        int p = parter(a,v,h-1,a[h],c);  // partisjonerer a[v:h-1]
        bytt(a,p,h);   // bytter for å få skilleverdien på rett plass

        return p;    // returnerer posisjonen til skilleverdien
    }

    public static <T>
    int sParter(T[] a, int k, Comparator<? super T> c)   // bruker hele tabellen
    {
        return sParter(a,0,a.length-1,k,c); // v = 0 og h = a.lenght-1
    }

    private static <T>
    void kvikksortering(T[] a, int v, int h, Comparator<? super T> c)
    {
        if (v >= h) return;  // hvis v = h er a[v:h] allerede sortert

        int p = sParter(a,v,h,(v + h)/2,c);
        kvikksortering(a,v,p-1,c);
        kvikksortering(a,p+1,h,c);
    }

    public static <T>
    void kvikksortering(T[] a, Comparator<? super T> c) // sorterer hele tabellen
    {
        kvikksortering(a,0,a.length-1,c);
    }


    // Flettesortering
    private static <T>
    void flett(T[] a, T[] b, int fra, int m, int til, Comparator<? super T> c)
    {
        int n = m - fra;   // antall elementer i a[fra:m>
        System.arraycopy(a,fra,b,0,n); // kopierer a[fra:m> over i b[0:n>

        int i = 0, j = m, k = fra;     // løkkevariabler og indekser

        while (i < n && j < til)  // fletter b[0:n> og a[m:til>, legger
            a[k++] = c.compare(b[i],a[j]) <= 0 ? b[i++] : a[j++];  // resultatet i a[fra:til>

        while (i < n) a[k++] = b[i++];  // tar med resten av b[0:n>
    }

    public static <T>
    void flettesortering(T[] a, T[] b, int fra, int til, Comparator<? super T> c)
    {
        if (til - fra <= 1) return;     // a[fra:til> har maks ett element

        int m = (fra + til)/2;          // midt mellom fra og til

        flettesortering(a,b,fra,m,c);   // sorterer a[fra:m>
        flettesortering(a,b,m,til,c);   // sorterer a[m:til>

        flett(a,b,fra,m,til,c);         // fletter a[fra:m> og a[m:til>
    }

    public static <T> void flettesortering(T[] a, Comparator<? super T> c)
    {
        T[] b = Arrays.copyOf(a, a.length/2);
        flettesortering(a,b,0,a.length,c);  // kaller metoden over
    }

// SØKEALGORITMER --------------------------------------------------------------------------------------------


    // Lineærsøk - søker fra start til ende (dvs. indeksen til den første av identiske verdier vil bli returnert)
    public static int lineærsøk(int[] a, int verdi) {
        if (a.length == 0 || verdi > a[a.length - 1])
            return -(a.length + 1);  // verdi er større enn den største

        int i = 0; for( ; a[i] < verdi; i++);  // siste verdi er vaktpost

        return verdi == a[i] ? i : -(i + 1);   // sjekker innholdet i a[i]
    }


    // Lineærsøk - søker fra start til ende med innlagte hopp: k (dvs. indeksen til den første av identiske verdier vil bli returnert)
    public static int lineærsøk(int[] a, int k, int verdi) {
        if (a.length == 0 || verdi > a[a.length - 1])
            return -(a.length + 1);  // verdi er større enn den største

        if (k < 1)
            throw new IllegalArgumentException("Må ha k > 0!");

        int j = k - 1; for( ; j < a.length && a[j] < verdi; j += k);

        int i = j - k + 1; for( ; i < a.length && a[i] < verdi; i++);  // siste verdi er vaktpost

        if ( i < a.length && a[i] == verdi) return i;   // sjekker innholdet i a[i] mot verdi
        else return -(i + 1);
    }


    // Lineærsøk - søker fra ende -> start (dvs. indeksen til den siste av identiske verdier vil bli returnert)
    public static int lineærsøkStartBakerst(int[] a, int verdi)
        {
            if (a.length == 0 || verdi > a[a.length - 1])
                return -(a.length + 1);  // verdi er større enn den største

            int i = a.length-1; for( ; a[i] > verdi; i--);  // siste verdi er vaktpost

            return verdi == a[i] ? i : -(i + 2);   // sjekker innholdet i a[i]
        }


    // kvadratrotsøk i et lineærsøk
    public static int kvadratrotsøk(int[] a, int verdi) {
        return Tabell.lineærsøk(a, (int)Math.sqrt(a.length), verdi);
    }


    // Binærsøk v.1
    public static int binærsøkVersjon1(int[] a, int fra, int til, int verdi)
    {
        Tabell.fratilKontroll(a.length,fra,til);
        int v = fra, h = til - 1;  // v og h er intervallets endepunkter

        while (v <= h)    // fortsetter så lenge som a[v:h] ikke er tom
        {
            int m = (v + h)/2;      // heltallsdivisjon - finner midten
            int midtverdi = a[m];   // hjelpevariabel for midtverdien

            if (verdi == midtverdi) return m;          // funnet
            else if (verdi > midtverdi) v = m + 1;     // verdi i a[m+1:h]
            else  h = m - 1;                           // verdi i a[v:m-1]
        }

        return -(v + 1);    // ikke funnet, v er relativt innsettingspunkt
    }

    public static int binærsøkVersjon1(int[] a, int verdi)  // søker i hele a
    {
        return binærsøkVersjon1(a,0,a.length,verdi);  // bruker metoden over
    }


    // Binærsøk v.1 med Comparator
    public static <T> int binærsøkVersjon1(T[] a, int fra, int til, T verdi,
                                           Comparator<? super T> c)
    {
        Tabell.fratilKontroll(a.length,fra,til);
        int v = fra, h = til - 1;  // v og h er intervallets endepunkter

        while (v <= h)    // fortsetter så lenge som a[v:h] ikke er tom
        {
            int m = (v + h)/2;      // heltallsdivisjon - finner midten
            T midtverdi = a[m];   // hjelpevariabel for midtverdien

            int cmp = c.compare(verdi, midtverdi);

            if (cmp == 0) return m;          // funnet
            else if (cmp > 0) v = m + 1;     // verdi i a[m+1:h]
            else  h = m - 1;                           // verdi i a[v:m-1]
        }

        return -(v + 1);    // ikke funnet, v er relativt innsettingspunkt
    }

    public static <T> int binærsøkVersjon1(T[] a, T verdi, Comparator<? super T> c)  // søker i hele a
    {
        return binærsøkVersjon1(a,0, a.length, verdi, c);  // bruker metoden over
    }


    // Binærsøk v.3
    public static int binærsøk(int[] a, int fra, int til, int verdi)
    {
        Tabell.fratilKontroll(a.length, fra, til);
        int v = fra, h = til - 1;  // v og h er intervallets endepunkter

        while (v < h)  // obs. må ha v < h her og ikke v <= h
        {
            int m = (v + h) / 2;  // heltallsdivisjon - finner midten

            if (verdi > a[m]) v = m + 1;   // verdi må ligge i a[m+1:h]
            else  h = m;                   // verdi må ligge i a[v:m]
        }
        if (h < v || verdi < a[v]) return -(v + 1);  // ikke funnet
        else if (verdi == a[v]) return v;            // funnet
        else  return -(v + 2);                       // ikke funnet
    }


}
