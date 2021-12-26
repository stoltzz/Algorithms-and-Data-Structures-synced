////////////////// class HeapPrioritetsKø //////////////////////////////

package hjelpeklasser;

import java.util.*;

public class HeapPrioritetsKø<T> implements PrioritetsKø<T>
{
    private T[] heap;                          // heaptabellen
    private int antall;                        // antall verdier i køen
    private Comparator<? super T> comp;        // for sammenligninger

    @SuppressWarnings("unchecked")
    public HeapPrioritetsKø(int kapasitet, Comparator<? super T> c)
    {
        if (kapasitet < 0) throw new IllegalArgumentException("Negativ kapasitet!");

        heap = (T[])new Object[kapasitet + 1];   // indeks 0 brukes ikke
        antall = 0;
        comp = c;
    }

    public HeapPrioritetsKø(Comparator<? super T> c)
    {
        this(8,c);  // bruker 8 som startkapasitet
    }

    public static <T extends Comparable<? super T>>
    HeapPrioritetsKø<T> naturligOrden(int kapasitet)
    {
        // bruker en komparator for datatypens naturlige orden
        return new HeapPrioritetsKø<>(kapasitet, Comparator.naturalOrder());
    }

    public static
    <T extends Comparable<? super T>> HeapPrioritetsKø<T> naturligOrden()
    {
        return naturligOrden(8);
    }

    @Override
    public T kikk()
    {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return heap[1];
    }

    @Override
    public void nullstill()
    {
        for (int i = 0; i <= antall; i++) heap[i] = null;
        antall = 0;
    }

    @Override
    public void leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "verdi er null!");

        // øker antaller først og "dobler" tabellen hvis den er full
        if (++antall == heap.length) heap = Arrays.copyOf(heap, 2*antall);

        int k = antall;                    // første ledige plass i tabellen
        heap[0] = verdi;                   // stoppverdi for while-løkken

        while (comp.compare(verdi, heap[k/2]) < 0)
        {
            heap[k] = heap[k/2];             // trekker verdien i heap[k/2] nedover
            k /= 2;                          // k går opp til forelderen
        }
        heap[0] = null;                    // fjerner referansen
        heap[k] = verdi;                   // verdi skal ligge i posisjon k
    }

    @Override
    public T taUt()
    {
        if (tom()) throw new NoSuchElementException("Køen er tom!");

        T min = heap[1];                   // den minste ligger øverst
        T verdi = heap[antall];            // skal omplasseres

        heap[antall] = null;               // for resirkulasjon
        antall--;                          // en verdi mindre i køen

        int k = 1;                         // nodeposisjon
        while ((k << 1) < antall)          // så lenge k har to barn
        {
            k <<= 1;                         // til venstre ved å doble k

            // hvis høyre barn k + 1 er minst, setter vi k dit, dvs. k++
            if (comp.compare(heap[k + 1], heap[k]) < 0) k++;

            heap[k >>> 1] = heap[k];         // forskyver oppover
        }

        if (2*k == antall)                 // har k et barn?
        {
            k *= 2;                          // går til venstre barn
            heap[k/2] = heap[k];             // forskyver oppover
        }

        heap[0] = verdi;                   // blir vaktpost

        while (comp.compare(verdi, heap[k/2]) < 0)
        {
            heap[k] = heap[k/2];             // trekker verdien nedover
            k /= 2;                          // k går opp til forelderen
        }
        heap[k] = verdi;                   // verdi skal ligge i posisjon k

        heap[0] = null;                    // fjerner referansen
        return min;                        // returnerer minste verdi
    }

    @Override
    public boolean taUt(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append('[');

        if (antall > 0) s.append(heap[1]);  // roten er i posisjon 1

        for (int i = 2; i <= antall; ++i)   // går gjennom tabellen
        {
            s.append(',').append(' ').append(heap[i]);
        }

        s.append(']');

        return s.toString();
    }

    public String minimumsGrenen()
    {
        StringBuilder s = new StringBuilder();
        s.append('[');

        if (antall > 0) s.append(heap[1]);  // treet er ikke tomt

        int k = 1;
        while (2*k < antall)  // forsetter så lenge k har to barn
        {
            k *= 2;  // går til venstre barn ved å doble k
            if (comp.compare(heap[k+1], heap[k]) < 0) k++;  // er høyre barn minst?
            s.append(',').append(' ').append(heap[k]);      // det minste barnet
        }

        if (2*k == antall)  // har treet et enebarn?
        {
            s.append(',').append(' ').append(heap[2*k]);
        }

        s.append(']');

        return s.toString();
    }

}  // HeapPrioritetsKø