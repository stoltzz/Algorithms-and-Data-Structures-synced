package hjelpeklasser;

import java.util.*;
import java.util.stream.Stream;

public class SBinTre<T> implements Beholder<T>
{
    private static final class Node<T> // en indre nodeklasse
    {
        private T verdi;                 // nodens verdi
        private Node<T> venstre, høyre;  // venstre og høyre barn

        private Node(T verdi, Node<T> v, Node<T> h)  // konstruktør
        {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // class Node

    ////////////////// class InordenIterator //////////////////////////////

    private class InordenIterator implements Iterator<T>
    {
        private Stakk<Node<T>> s = new TabellStakk<>();
        private Node<T> p = null;
        private int iteratorendringer;                   // iteratorendringer

        private Node<T> først(Node<T> q)   // en hjelpemetode
        {
            while (q.venstre != null)        // starter i q
            {
                s.leggInn(q);                  // legger q på stakken
                q = q.venstre;                 // går videre mot venstre
            }
            return q;                        // q er lengst ned til venstre
        }

        private InordenIterator()          // konstruktør
        {
            if (tom()) return;               // treet er tomt
            p = først(rot);                  // bruker hjelpemetoden
            iteratorendringer = endringer;   // setter treets endringer
        }

        public InordenIterator(T verdi)    // konstruktør
        {
            if (verdi == null)
                throw new IllegalArgumentException("Treet har ikke nullverdier!");

            Node<T> q = rot;

            while (q != null)
            {
                int cmp = comp.compare(verdi, q.verdi);

                if (cmp < 0)
                {
                    s.leggInn(q);
                    q = q.venstre;
                }
                else if (cmp > 0)
                {
                    q = q.høyre;
                }
                else break;
            }

            if (q != null) p = q;
            else if (!s.tom()) p = s.taUt();

            iteratorendringer = endringer;   // setter treets endringer
        }

        @Override
        public T next()
        {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException();

            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            T verdi = p.verdi;                        // tar vare på verdien

            if (p.høyre != null) p = først(p.høyre);  // p har høyre subtre
            else if (s.tom()) p = null;               // stakken er tom
            else p = s.taUt();                        // tar fra stakken

            return verdi;                             // returnerer verdien
        }

        @Override
        public boolean hasNext()
        {
            return p != null;
        }

    } // InordenIterator

    private class OmvendtInordenIterator implements Iterator<T>
    {
        private Stakk<Node<T>> s = new TabellStakk<>();  // for traversering
        private Node<T> p = null;                        // nodepeker
        private Node<T> q = null;                        // nodepeker
        private int iteratorendringer;                   // iteratorendringer

        private Node<T> sist(Node<T> q)    // en hjelpemetode
        {
            while (q.høyre != null)          // starter i q
            {
                s.leggInn(q);                  // legger q på stakken
                q = q.høyre  ;                 // går videre mot høyre
            }
            return q;                        // q er lengst ned til høyre
        }

        public OmvendtInordenIterator()  // konstruktør
        {
            if (rot == null) return;         // treet er tomt
            p = sist(rot);                   // bruker hjelpemetoden
            iteratorendringer = endringer;   // setter treets endringer
        }

        public OmvendtInordenIterator(T verdi)    // konstruktør
        {
            if (verdi == null)
                throw new IllegalArgumentException("Treet har ikke nullverdier!");

            Node<T> q = rot;

            while (q != null)
            {
                int cmp = comp.compare(verdi, q.verdi);

                if (cmp < 0)
                {
                    q = q.venstre;
                }
                else
                {
                    s.leggInn(q);
                    q = q.høyre;
                }
            }
            if (!s.tom()) p = s.taUt();

            iteratorendringer = endringer;   // setter treets endringer
        }

        public T next()
        {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException();

            if (!hasNext()) throw new NoSuchElementException();

            T verdi = p.verdi;               // tar vare på verdien i noden p
            q = p;                           // q oppdateres før p flyttes


            if (p.venstre != null) p = sist(p.venstre);  // p har høyre subtre
            else if (!s.tom()) p = s.taUt();             // p har ikke høyre subtre
            else p = null;                               // stakken er tom

            return verdi;                    // returnerer verdien
        }

        public boolean hasNext()
        {
            return p != null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

    } // OmvendtInordenIterator

    private Node<T> rot;                       // peker til rotnoden
    private int antall;                        // antall noder
    private final Comparator<? super T> comp;  // komparator
    private int endringer;                     // antall endringer

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public static <T extends Comparable<? super T>> SBinTre<T> naturligOrdenTre()
    {
        return new SBinTre<>(Comparator.naturalOrder());
    }

    public static <T> SBinTre<T> komparatorTre(Comparator<? super T> c)
    {
        return new SBinTre<>(c);
    }

    public static <T> SBinTre<T> komparatorTre(T[] a, Comparator<? super T> c)
    {
        SBinTre<T> tre = new SBinTre<>(c);          // komparatoren c
        for (T verdi : a) tre.leggInn(verdi);       // bygger opp treet
        return tre;                                 // treet returneres
    }

    public static <T extends Comparable<? super T>> SBinTre<T> naturligOrdenTre(T[] a)
    {
        return komparatorTre(a, Comparator.naturalOrder());  // naturlig ordning
    }

    public static <T> SBinTre<T> sbintre(Stream<T> s, Comparator<? super T> c)
    {
        SBinTre<T> tre = new SBinTre<>(c);             // komparatoren c
        s.forEach(tre::leggInn);                       // bygger opp treet
        return tre;                                    // treet returneres
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre(Stream<T> s)
    {
        return sbintre(s, Comparator.naturalOrder());  // naturlig ordning
    }

    public Iterator<T> iterator()  // returnerer en iterator
    {
        return new InordenIterator();
    }

    public Iterator<T> iterator(T verdi)  // returnerer en iterator
    {
        return new InordenIterator();
    }

    public Iterator<T> riterator()
    {
        return new OmvendtInordenIterator();
    }

    public Iterator<T> riterator(T verdi)
    {
        return new OmvendtInordenIterator(verdi);
    }

    private static <T> Node<T> balansert(T[] a, int v, int h)  // en rekursiv metode
    {
        if (v > h) return null;                       // tomt intervall -> tomt tre

        int m = (v + h)/2;                            // midten
        T verdi = a[m];                               // midtverdien

        while (v < m && verdi.equals(a[m-1])) m--;    // til venstre

        Node<T> p = balansert(a, v, m - 1);           // venstre subtre
        Node<T> q = balansert(a, m + 1, h);           // høyre subtre

        return new Node<>(verdi, p, q);               // rotnoden
    }

    public static <T> SBinTre<T> balansert(T[] a, Comparator<? super T> c)
    {
        SBinTre<T> tre = new SBinTre<>(c);          // oppretter et tomt tre
        tre.rot = balansert(a, 0, a.length - 1);    // bruker den rekursive metoden
        tre.antall = a.length;                      // setter antallet
        return tre;                                 // returnerer treet
    }

    public static <T extends Comparable<? super T>> SBinTre<T> balansert(T[] a)
    {
        return balansert(a, Comparator.naturalOrder());
    }

    public int antall()        // antall verdier i treet
    {
        return antall;
    }

    public int antall(T verdi)
    {
        Node<T> p = rot;
        int antallVerdi = 0;

        while (p != null)
        {
            int cmp = comp.compare(verdi,p.verdi);
            if (cmp < 0) p = p.venstre;
            else
            {
                if (cmp == 0) antallVerdi++;
                p = p.høyre;
            }
        }
        return antallVerdi;
    }

    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    public boolean tom()       // er treet tomt?
    {
        return antall == 0;
    }


    public void nullstill()
    {
        if (!tom()) nullstill(rot);  // nullstiller
        rot = null; antall = 0;      // treet er nå tomt
        endringer++;         // det er gjort en endring i treet
    }

    private void nullstill(Node<T> p)
    {
        if (p.venstre != null)
        {
            nullstill(p.venstre);      // venstre subtre
            p.venstre = null;          // nuller peker
        }
        if (p.høyre != null)
        {
            nullstill(p.høyre);        // høyre subtre
            p.høyre = null;            // nuller peker
        }
        p.verdi = null;              // nuller verdien
    }


    public boolean leggInn(T verdi)    // skal ligge i class SBinTre
    {
        if (verdi == null) throw new NullPointerException("Ulovlig nullverdi!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

        endringer++;                            // det er gjort en endring i treet
        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }

    public boolean fjern(T verdi)  // hører til klassen SBinTre
    {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi,p.verdi);      // sammenligner
            if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
            else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
        }
        else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null)
            {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        endringer++;         // det er gjort en endring i treet
        antall--;   // det er nå én node mindre i treet
        return true;
    }

    public void fjernMin()  // hører til klassen SBinTre
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        if (rot.venstre == null) rot = rot.høyre;  // rotverdien er minst
        else
        {
            Node<T> p = rot.venstre, q = rot;
            while (p.venstre != null)
            {
                q = p;  // q er forelder til p
                p = p.venstre;
            }
            // p er noden med minst verdi
            q.venstre = p.høyre;
        }
        endringer++;         // det er gjort en endring i treet
        antall--;  // det er nå én node mindre i treet
    }

    public int fjernAlle(T verdi) {

        int antall = 0;

        while (fjern(verdi)) {
            antall++;
        }

        return antall;
    }

    public Liste<T> intervallsøk(T fraverdi, T tilverdi)
    {
        Stakk<Node<T>> s = new TabellStakk<>();

        Node<T> p = rot;
        while (p != null)    // leter etter fraverdi
        {
            int cmp = comp.compare(fraverdi,p.verdi);
            if (cmp < 0)
            {
                s.leggInn(p); p = p.venstre;
            }
            else if (cmp > 0) p = p.høyre;
            else break;
        }

        if (p == null) p = s.taUt();  // neste i inorden

        Liste<T> liste = new TabellListe<>();

        while (p != null && comp.compare(p.verdi,tilverdi) < 0)
        {
            liste.leggInn(p.verdi);

            if (p.høyre != null)
            {
                p = p.høyre;
                while (p.venstre != null)
                {
                    s.leggInn(p); p = p.venstre;
                }
            }
            else if (!s.tom()) p = s.taUt();
            else p = null;
        }

        return liste;
    }

    private static int høyde(Node<?> p)  // ? betyr vilkårlig type
    {
        if (p == null) return -1;          // et tomt tre har høyde -1

        return 1 + Math.max(høyde(p.venstre), høyde(p.høyre));
    }

    public int høyde()
    {
        return høyde(rot);                 // kaller hjelpemetoden
    }

    public String toString()                   // hører til SBinTre
    {
        StringBuilder s = new StringBuilder();   // StringBuilder
        s.append('[');                           // starter med [
        if (!tom()) toString(rot,s);             // den rekursive metoden
        s.append(']');                           // avslutter med ]
        return s.toString();                     // returnerer
    }

    // gulv, tak, maks, min
    public T min()                 // skal returnere treets minste verdi
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;                            // starter i roten
        while (p.venstre != null) p = p.venstre;    // går mot venstre
        return p.verdi;                             // den minste verdien
    }

    public T maks()     // skal returnere treets største verdi, siste forekomst inorden
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;                            // starter i roten
        while (p.høyre != null) p = p.høyre;    // går mot høyre
        return p.verdi;                             // den største verdien
    }

    public T maks2()    // skal returnere treets største verdi, første forekomst inorden
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot, q = rot;           // starter i roten

        while (p.høyre != null) {
            p = p.høyre;                    // går mot høyre
            if (comp.compare(p.verdi, q.verdi) > 0) q = p;
        }
        return q.verdi;                     // den største verdien
    }

    public T gulv(T verdi)  // finner første i inorden av like verdier
    {
        Objects.requireNonNull(verdi, "Treet har ingen nullverdier!");
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot; T gulv = null;

        while (p != null)
        {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) p = p.venstre;  // gulvet ligger til venstre
            else if (cmp > 0)
            {
                gulv = p.verdi;            // nodeverdien er en kandidat
                p = p.høyre;
            }
            else return p.verdi;         // verdi ligger i treet
        }
        return gulv;
    }

    public T gulv2(T verdi) // finner den siste i inorden av like verdier som verdi
    {
        Objects.requireNonNull(verdi, "Treet har ingen nullverdier!");
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot; T gulv = null;

        while (p != null)
        {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) p = p.venstre;  // gulvet ligger til venstre
            else
            {
                gulv = p.verdi;            // nodeverdien er en kandidat
                p = p.høyre;
            }
        }
        return gulv;
    }

    public T tak(T verdi) {

        Objects.requireNonNull(verdi, "Treet har ingen nullverdier!");
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot; T tak = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp > 0) p = p.høyre;
            else if (cmp < 0) {
                tak = p.verdi;
                p = p.venstre;
            }
            else return p.verdi;
        }
        return tak;
    }

    public T mindre(T verdi) {

        Objects.requireNonNull(verdi, "Treet har ingen nullverdier!");
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot; T mindre = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp <= 0) p = p.venstre;  // mindre ligger til venstre
            else {
                mindre = p.verdi;            // nodeverdien er en kandidat
                p = p.høyre;
            }
        }
        return mindre;
    }


    private static <T> void toString(Node<T> p, StringBuilder s)
    {
        if (p.venstre != null)                   // p har et venstre subtre
        {
            toString(p.venstre, s);                // komma og mellomrom etter
            s.append(',').append(' ');             // den siste i det venstre
        }                                        // subtreet til p

        s.append(p.verdi);                       // verdien i p

        if (p.høyre != null)                     // p har et høyre subtre
        {
            s.append(',').append(' ');             // komma og mellomrom etter
            toString(p.høyre, s);                  // p siden p ikke er den
        }                                        // siste noden i inorden
    }

} // class SBinTre 