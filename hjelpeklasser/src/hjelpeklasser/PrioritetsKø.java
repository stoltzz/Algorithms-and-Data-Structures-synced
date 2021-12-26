package hjelpeklasser;

public interface PrioritetsKø<T>           // Java: PriorityQueue
{
    public void leggInn(T verdi);            // Java: add/offer
    public T kikk();                         // Java: element/peek
    public T taUt();                         // Java: remove/poll
    public boolean taUt(T verdi);            // Java: remove
    public int antall();                     // Java: size
    public boolean tom();                    // Java: isEmpty
    public void nullstill();                 // Java: clear
}
