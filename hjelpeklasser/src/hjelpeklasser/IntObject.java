package hjelpeklasser;

public class IntObject
{
    private int value;    // kun denne som instansvariabel

    public IntObject(int value) { this.value = value; }

    public void add(int value) { this.value += value; }

    public void subtract(int value) { this.value -= value; }

    public void set(int value) { this.value = value; }

    public int get() { return value; }
}
