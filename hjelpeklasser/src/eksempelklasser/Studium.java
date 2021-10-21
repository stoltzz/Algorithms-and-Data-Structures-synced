package eksempelklasser;

public enum Studium
{
    Anvendt ("Anvendt datateknologi"),   // enumkonstanten Anvendt
    Data ("Ingeniørfag - data"),         // enumkonstanten Data
    IT ("Informasjonsteknologi"),        // enumkonstanten IT
    Elektro ("Ingeniørfag - elektronikk og informasjonsteknologi"),     // enumkonstanten Elektro
    Enkeltemne ("Enkeltemnestudent");    // enumkonstanten Enkeltemne

    private final String fulltnavn;      // instansvariabel
    private Studium(String fulltnavn) { this.fulltnavn = fulltnavn; }

    public String toString() { return fulltnavn; }
}
