package hjelpeklasser;

import java.util.*;

public class BinTre<T> implements Iterable<T>           // et generisk binærtre
{
    private static final class Node<T>  // en indre nodeklasse
    {
        private T verdi;            // nodens verdi
        private Node<T> venstre;    // referanse til venstre barn/subtre
        private Node<T> høyre;      // referanse til høyre barn/subtre

        private Node(T verdi, Node<T> v, Node<T> h)    // konstruktør
        {
            this.verdi = verdi; venstre = v; høyre = h;
        }

        private Node(T verdi) { this.verdi = verdi; }  // konstruktør

    } // class Node<T>

    private Node<T> rot;      // referanse til rotnoden
    private int antall;       // antall noder i treet
    private int endringer;      // antall endringer i treet

    public BinTre() { rot = null; antall = 0; }          // konstruktør

    public BinTre(int[] posisjon, T[] verdi)  // konstruktør
    {
        if (posisjon.length > verdi.length) throw new
                IllegalArgumentException("Verditabellen har for få elementer!");

        for (int i = 0; i < posisjon.length; i++) leggInn(posisjon[i],verdi[i]);
    }


    public final void leggInn(int posisjon, T verdi)  // final: kan ikke overstyres
    {
        if (posisjon < 1) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") < 1!");

        Node<T> p = rot, q = null;    // nodereferanser

        int filter = Integer.highestOneBit(posisjon) >> 1;   // filter = 100...00

        while (p != null && filter > 0)
        {
            q = p;
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;
            filter >>= 1;  // bitforskyver filter
        }

        if (filter > 0) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") mangler forelder!");
        else if (p != null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes fra før!");

        p = new Node<>(verdi);          // ny node

        if (q == null) rot = p;         // tomt tre - ny rot
        else if ((posisjon & 1) == 0)   // sjekker siste siffer i posisjon
            q.venstre = p;                // venstre barn til q
        else
            q.høyre = p;                  // høyre barn til q

        antall++;                       // en ny verdi i treet
        endringer++;
    }


    public T fjern(int posisjon)
    {
        if (posisjon < 1) throw new
                IllegalArgumentException("Posisjon(" + posisjon + ") < 1!");

        Node<T> p = rot, q = null;               // hjelpepekere
        int filter = Integer.highestOneBit(posisjon >> 1);   // binært siffer

        while (p != null && filter > 0)
        {
            q = p;
            p = (filter & posisjon) == 0 ? p.venstre : p.høyre;
            filter >>= 1;
        }

        if (p == null) throw new
                IllegalArgumentException("Posisjon(" + posisjon + ") er utenfor treet!");

        if (p.venstre != null || p.høyre != null) throw new
                IllegalArgumentException("Posisjon(" + posisjon + ") er ingen bladnode!");

        if (p == rot) rot = null;
        else if (p == q.venstre) q.venstre = null;
        else q.høyre = null;

        antall--;  //
        endringer++;
        return p.verdi;
    }


    public int antall() { return antall; }               // returnerer antallet

    private static int antall(Node<?> p)  // ? betyr vilkårlig type
    {
        if (p == null) return 0;            // et tomt tre har 0 noder

        return 1 + antall(p.venstre) + antall(p.høyre);
    }

    public int antall2()
    {
        return antall(rot);                 // kaller hjelpemetoden
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


    public boolean tom() { return antall == 0; }         // tomt tre?


    private Node<T> finnNode(int posisjon)  // finner noden med gitt posisjon
    {
        if (posisjon < 1) return null;

        Node<T> p = rot;   // nodereferanse
        int filter = Integer.highestOneBit(posisjon >> 1);   // filter = 100...00

        for (; p != null && filter > 0; filter >>= 1)
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;

        return p;   // p blir null hvis posisjon ikke er i treet
    }


    public boolean finnes(int posisjon)
    {
        return finnNode(posisjon) != null;
    }


    private static <T> boolean inneholder(Node<T> p, T verdi)
    {
        if (p == null) return false;    // kan ikke ligge i et tomt tre
        return verdi.equals(p.verdi) || inneholder(p.venstre,verdi)
                || inneholder(p.høyre,verdi);
    }

    public boolean inneholder(T verdi)
    {
        return inneholder(rot,verdi);   // kaller den private metoden
    }


    private static <T> int posisjon(Node<T> p, int k, T verdi)
    {
        if (p == null) return -1;                  // ligger ikke i et tomt tre
        if (verdi.equals(p.verdi)) return k;       // verdi ligger i p
        int i = posisjon(p.venstre,2*k,verdi);     // leter i venstre subtre
        if (i > 0) return i;                       // ligger i venstre subtre
        return posisjon(p.høyre,2*k+1,verdi);      // leter i høyre subtre
    }

    public int posisjon(T verdi)
    {
        return posisjon(rot,1,verdi);  // kaller den private metoden
    }


    public T hent(int posisjon)
    {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        return p.verdi;
    }


    public T oppdater(int posisjon, T nyverdi)
    {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        T gammelverdi = p.verdi;
        p.verdi = nyverdi;

        endringer++;
        return gammelverdi;
    }

    public void nivåorden(Oppgave<? super T> oppgave)    // ny versjon
    {
        if (tom()) return;                   // tomt tre
        Kø<Node<T>> kø = new TabellKø<>();   // Se Avsnitt 4.2.3
        kø.leggInn(rot);                     // legger inn roten

        while (!kø.tom())                    // så lenge køen ikke er tom
        {
            Node<T> p = kø.taUt();             // tar ut fra køen
            oppgave.utførOppgave(p.verdi);     // den generiske oppgaven

            if (p.venstre != null) kø.leggInn(p.venstre);
            if (p.høyre != null) kø.leggInn(p.høyre);
        }
    }

    public int[] nivåer()   // returnerer en tabell som inneholder nivåantallene
    {
        if (tom()) return new int[0];       // en tom tabell for et tomt tre

        int[] a = new int[8];               // hjelpetabell
        Kø<Node<T>> kø = new TabellKø<>();  // hjelpekø
        int nivå = 0;                       // hjelpevariabel

        kø.leggInn(rot);    // legger roten i køen

        while (!kø.tom())   // så lenge som køen ikke er tom
        {
            // utvider a hvis det er nødvendig
            if (nivå == a.length) a = Arrays.copyOf(a,2*nivå);

            int k = a[nivå] = kø.antall();  // antallet på dette nivået

            for (int i = 0; i < k; i++)  // alle på nivået
            {
                Node<T> p = kø.taUt();

                if (p.venstre != null) kø.leggInn(p.venstre);
                if (p.høyre != null) kø.leggInn(p.høyre);
            }

            nivå++;  // fortsetter på neste nivå
        }

        return Arrays.copyOf(a, nivå);  // fjerner det overflødige
    }

    // rekursiv preorden
    private static <T> void preorden(Node<T> p, Oppgave<? super T> oppgave)
    {
        oppgave.utførOppgave(p.verdi);                       // utfører oppgaven

        if (p.venstre != null) preorden(p.venstre,oppgave);  // til venstre barn
        if (p.høyre != null) preorden(p.høyre,oppgave);      // til høyre barn
    }

    // uten halerekursjon
    private static <T> void preorden2(Node<T> p, Oppgave<? super T> oppgave)
    {
        while (true)
        {
            oppgave.utførOppgave(p.verdi);
            if (p.venstre != null) preorden(p.venstre,oppgave);
            if (p.høyre == null) return;      // metodekallet er ferdig
            p = p.høyre;
        }
    }

    public void preorden(Oppgave<? super T> oppgave)
    {
        if (!tom()) preorden(rot,oppgave);  // sjekker om treet er tomt
    }

    // iterativ preorden
    public void preorden3(Oppgave<? super T> oppgave)   // ny versjon
    {
        if (tom()) return;

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;    // starter i roten

        while (true)
        {
            oppgave.utførOppgave(p.verdi);

            if (p.venstre != null)
            {
                if (p.høyre != null) stakk.leggInn(p.høyre);
                p = p.venstre;
            }
            else if (p.høyre != null)  // her er p.venstre lik null
            {
                p = p.høyre;
            }
            else if (!stakk.tom())     // her er p en bladnode
            {
                p = stakk.taUt();
            }
            else                       // p er en bladnode og stakken er tom
                break;                   // traverseringen er ferdig
        }
    }


    // rekursiv inorden
    private static <T> void inorden(Node<T> p, Oppgave<? super T> oppgave)
    {
        if (p.venstre != null) inorden(p.venstre,oppgave);
        oppgave.utførOppgave(p.verdi);
        if (p.høyre != null) inorden(p.høyre,oppgave);
    }

    // uten halerekursjon
    private static <T> void inorden2(Node<T> p, Oppgave<? super T> oppgave)
    {
        while (true) {
            if (p.venstre != null) inorden(p.venstre, oppgave);
            oppgave.utførOppgave(p.verdi);
            if (p.høyre == null) return;      // metodekallet er ferdig
            p = p.høyre;
        }
    }

    public void inorden(Oppgave <? super T> oppgave)
    {
        if (!tom()) inorden(rot,oppgave);
    }

    // iterativ inorden
    public void inorden3(Oppgave<? super T> oppgave)  // iterativ inorden
    {
        if (tom()) return;            // tomt tre

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;   // starter i roten og går til venstre
        for ( ; p.venstre != null; p = p.venstre) stakk.leggInn(p);

        while (true)
        {
            oppgave.utførOppgave(p.verdi);      // oppgaven utføres

            if (p.høyre != null)          // til venstre i høyre subtre
            {
                for (p = p.høyre; p.venstre != null; p = p.venstre)
                {
                    stakk.leggInn(p);
                }
            }
            else if (!stakk.tom())
            {
                p = stakk.taUt();   // p.høyre == null, henter fra stakken
            }
            else break;          // stakken er tom - vi er ferdig

        } // while
    }

    // rekursiv postorden
    private static <T> void postorden(Node<T> p, Oppgave<? super T> oppgave)
    {
        if (p.venstre != null) postorden(p.venstre,oppgave);
        if (p.høyre != null) postorden(p.høyre,oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public void postorden(Oppgave<? super T> oppgave)
    {
        if (rot != null) postorden(rot,oppgave);
    }

    private static int antallBladnoder(Node<?> p) {

        if (p.venstre == null && p.høyre == null) return 1;

        return (p.venstre == null ? 0 : antallBladnoder(p.venstre)) +
                (p.høyre == null ? 0 : antallBladnoder(p.høyre));
    }

    public int antallBladnoder()
    {
        return rot == null ? 0 : antallBladnoder(rot);
    }

    private static void makspos(Node<?> p, int pos, IntObject o) {
        if (pos > o.get()) o.set(pos);
        if (p.venstre != null) makspos(p.venstre, 2 * pos, o);
        if (p.høyre != null) makspos(p.høyre, 2 * pos + 1, o);
    }

    public int makspos() {
        IntObject o = new IntObject(-1);
        if (!tom()) makspos(rot, 1, o);
        return o.get();
    }

    // sjekker om treet er et mintre
    public boolean erMintre(Comparator<? super T> c) // legges i BinTre
    {
        if (rot == null) return true;    // et tomt tre er et minimumstre
        else return erMintre(rot,c);     // kaller den private hjelpemetoden
    }

    private static <T> boolean erMintre(Node<T> p, Comparator<? super T> c)
    {
        if (p.venstre != null)
        {
            if (c.compare(p.venstre.verdi,p.verdi) < 0) return false;
            if (!erMintre(p.venstre,c)) return false;
        }
        if (p.høyre != null)
        {
            if (c.compare(p.høyre.verdi,p.verdi) < 0) return false;
            if (!erMintre(p.høyre,c)) return false;
        }
        return true;
    }

    public String minimumsGrenen(Comparator<? super T> c) {

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = rot;

        while (p != null) {
            s.add(p.verdi.toString());
            if (p.venstre == null) {
                p = p.høyre;
            } else if (p.høyre == null) {
                p = p.venstre;
            } else {
                if (c.compare(p.høyre.verdi, p.venstre.verdi) < 0) {
                    p = p.høyre;
                } else {
                    p = p.venstre;
                }
            }
        }
        return s.toString();
    }




    // iterable Interface
    public Iterator<T> iterator()     // skal ligge i class BinTre
    {
        return new InordenIterator();
    }

    private class InordenIterator implements Iterator<T>
    {
        private Stakk<Node<T>> stakk;   // hjelpestakk
        private Node<T> p = null;       // hjelpevariabel
        private int iteratorendringer;


        // en privat hjelpemetoder skal inn her
        private Node<T> først(Node<T> q)   // en hjelpemetode
        {
            while (q.venstre != null)        // starter i q
            {
                stakk.leggInn(q);              // legger q på stakken
                q = q.venstre;                 // går videre mot venstre
            }
            return q;                        // q er lengst ned til venstre
        }

        private InordenIterator()   // konstruktør
        {
             if (tom()) return;               // treet er tomt
             stakk = new TabellStakk<>();     // oppretter stakken
             p = først(rot);                  // bruker hjelpemetoden
            iteratorendringer = endringer;
        }

        public T next()
        {
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException("Treet er endret");
            }

            T verdi = p.verdi;                        // tar vare på verdien

            if (p.høyre != null) p = først(p.høyre);  // p har høyre subtre
            else if (stakk.tom()) p = null;           // stakken er tom
            else p = stakk.taUt();                    // tar fra stakken

            return verdi;                             // returnerer verdien
        }

        public boolean hasNext()
        {
            return p != null;
        }
    }


    // iterable Interface - OMVENDT ITERATOR
    public Iterator<T> omvendtInordenIterator()     // skal ligge i class BinTre
    {
        return new OmvendtInordenIterator();
    }

    private class OmvendtInordenIterator implements Iterator<T>
    {
        private Stakk<Node<T>> stakk;   // hjelpestakk
        private Node<T> p = null;       // hjelpevariabel


        // en privat hjelpemetoder skal inn her
        private Node<T> først(Node<T> q)   // en hjelpemetode
        {
            while (q.høyre != null)        // starter i q
            {
                stakk.leggInn(q);              // legger q på stakken
                q = q.høyre;                 // går videre mot høyre
            }
            return q;                        // q er lengst ned til høyre
        }

        private OmvendtInordenIterator()   // konstruktør
        {
            if (tom()) return;               // treet er tomt
            stakk = new TabellStakk<>();     // oppretter stakken
            p = først(rot);                  // bruker hjelpemetoden
        }

        public T next()
        {
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            T verdi = p.verdi;                        // tar vare på verdien

            if (p.venstre != null) p = først(p.venstre);  // p har høyre subtre
            else if (stakk.tom()) p = null;           // stakken er tom
            else p = stakk.taUt();                    // tar fra stakken

            return verdi;                             // returnerer verdien
        }

        public boolean hasNext()
        {
            return p != null;
        }
    }


    // iterable Interface preordenIterator
    public Iterator<T> preordenIterator()     // skal ligge i class BinTre
    {
        return new PreordenIterator();
    }

    private class PreordenIterator implements Iterator<T>
    {
        private Stakk<Node<T>> stakk;   // hjelpestakk
        private Node<T> p = null;       // hjelpevariabel


        // en privat hjelpemetoder skal inn her
        private Node<T> først(Node<T> q)   // en hjelpemetode
        {
            while (q.venstre != null)        // starter i q
            {
                stakk.leggInn(q);              // legger q på stakken
                q = q.venstre;                 // går videre mot venstre
            }
            return q;                        // q er lengst ned til venstre
        }

        private PreordenIterator()   // konstruktør
        {
            if (tom()) return;               // treet er tomt
            stakk = new TabellStakk<>();     // oppretter stakken
            p = rot;
        }

        public T next()
        {
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            T verdi = p.verdi;                        // tar vare på verdien

            if (p.venstre != null) {
                if (p.høyre != null) stakk.leggInn(p.høyre);
                p = p.venstre;
            }
            else if (p.høyre != null) p = p.høyre;
            else if (stakk.tom()) p = null;           // stakken er tom
            else p = stakk.taUt();                    // tar fra stakken

            return verdi;                             // returnerer verdien
        }

        public boolean hasNext()
        {
            return p != null;
        }
    }





} // class BinTre<T>
