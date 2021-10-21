package hjelpeklasser;

import java.util.*;

public class TabellStakk<T> implements Stakk<T>
{
    private T[] a;                     // en T-tabell
    private int antall;                // antall verdier på stakken

    public TabellStakk()               // konstruktør - tabellengde 8
    {
        this(8);
    }

    @SuppressWarnings("unchecked")     // pga. konverteringen: Object[] -> T[]
    public TabellStakk(int lengde)     // valgfri tabellengde
    {
        if (lengde < 0)
            throw new IllegalArgumentException("Negativ tabellengde!");

        a = (T[])new Object[lengde];     // oppretter tabellen
        antall = 0;                      // stakken er tom
    }

    public void leggInn(T verdi) {  // eng: push

        if (antall == a.length)
            a = Arrays.copyOf(a, antall == 0 ? 1 : 2 * antall);   // dobler

        a[antall++] = verdi;
    }

    public T kikk() {   // eng: peek
        if (antall == 0)       // sjekker om stakken er tom
            throw new NoSuchElementException("Stakken er tom!");

        return a[antall-1];    // returnerer den øverste verdien
    }

    public T taUt() {   // eng: pop

        if (antall == 0)       // sjekker om stakken er tom
            throw new NoSuchElementException("Stakken er tom!");

        antall--;             // reduserer antallet

        T temp = a[antall];   // tar var på det øverste objektet
        a[antall] = null;     // tilrettelegger for resirkulering

        return temp;          // returnerer den øverste verdien
    }

    public int antall() {   // eng: size
        return antall;
    }

    public boolean tom() {  // eng: isEmpty
        return antall == 0;
    }

    public void nullstill() {   // eng: clear

        for (int i = 0; i < antall; i++) {
            a[i] = null;
        }
        antall = 0;
    }

    public static <T> void snu(Stakk<T> A) {

        Stakk<T> B = new TabellStakk<>();
        Stakk<T> C = new TabellStakk<>();

        while (!A.tom()) B.leggInn(A.taUt());
        while (!B.tom()) C.leggInn(B.taUt());
        while (!C.tom()) A.leggInn(C.taUt());

    }

    public static <T> void snu2(Stakk<T> A) {

        Stakk<T> B = new TabellStakk<T>();
        int n = A.antall() - 1;

        while (n > 0)
        {
            T temp = A.taUt();
            for (int j = 0; j < n; j++) B.leggInn(A.taUt());
            A.leggInn(temp);
            while (!B.tom()) A.leggInn(B.taUt());
            n--;
        }
    }

    public static <T> void snu3(Stakk<T> A) {

        Kø<T> B = new TabellKø<>();

        while (!A.tom()) B.leggInn(A.taUt());
        while (!B.tom()) A.leggInn(B.taUt());
    }

    public static <T> void kopier(Stakk<T> A, Stakk<T> B) {

        Stakk<T> C = new TabellStakk<>();
        T verdi;

        while (!A.tom()) C.leggInn(A.taUt());

        while (!C.tom()) {
            verdi = C.taUt();
            A.leggInn(verdi);
            B.leggInn(verdi);
        }
    }

    public static <T> void kopier2(Stakk<T> A, Stakk<T> B) {

        T temp;
        int n = A.antall() - 1;

        while (n >= 0) {
            for (int j = 0; j < n; j++) {
                B.leggInn(A.taUt());
            }
            temp = A.kikk();
            for (int j = 0; j < n; j++) {
                A.leggInn(B.taUt());
            }
            B.leggInn(temp);
            n--;
        }
    }

    // sorterer slik at den minste kommer øverst på stakken
    public static <T> void sorter(Stakk<T> A, Comparator<? super T> c)
    {
        Stakk<T> B = new TabellStakk<>();
        T temp; int n = 0;

        while (!A.tom())
        {
            temp = A.taUt();
            n = 0;
            while (!B.tom() && c.compare(temp,B.kikk()) < 0)
            {
                n++; A.leggInn(B.taUt());
            }
            B.leggInn(temp);
            for (int i = 0; i < n; i++) B.leggInn(A.taUt());
        }

        while (!B.tom()) A.leggInn(B.taUt());
    }

    @Override
    public String toString() {  // bruker StringBuilder

        StringBuilder s = new StringBuilder();
        s.append("[");

        for (int i = antall - 1; i >= 0; i--) {
            if (i != 0) {
                s.append(a[i]).append(",").append(" ");
            }
            else {
                s.append(a[i]);
            }
        }
        s.append("]");
        return s.toString();
    }

    public String toString2()   // bruker StringJoiner
    {
        StringJoiner s = new StringJoiner(", ", "[", "]");
        for (int i = antall - 1; i >= 0; i--)
            s.add(a[i] == null ? "null" : a[i].toString());
        return s.toString();
    }

}  // class TabellStakk
