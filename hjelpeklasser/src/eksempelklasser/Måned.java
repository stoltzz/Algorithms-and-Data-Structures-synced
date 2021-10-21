package eksempelklasser;

import java.util.Arrays;

public enum Måned {

    JAN (1, "januar"),
    FEB (2, "februar"),
    MAR (3, "mars"),
    APR (4, "april"),
    MAI (5, "mai"),
    JUN (6, "juni"),
    JUL (7, "juli"),
    AUG (8, "august"),
    SEP (9, "september"),
    OKT (10, "oktober"),
    NOV (11, "november"),
    DES (12, "desember");

    private final int mndnr;      // instansvariabel
    private final String fulltnavn;      // instansvariabel

    private Måned(int mndnr, String fulltnavn) {
        this.mndnr = mndnr;
        this.fulltnavn = fulltnavn;
    }

    public int getMndnr() {
        return mndnr;
    }

    @Override
    public String toString() { return fulltnavn; }

    public static String toString(int mnd) {
        if (mnd < 1 || mnd > 12) {
            throw new IllegalArgumentException("Ulovlig månedsnummer");
        }

        return values()[mnd - 1].toString();
    }


    public static Måned[] vår() {
        return Arrays.copyOfRange(values(), 3, 5);  // eksempel fra LF
    }

    public static Måned[] sommer() {
        return new Måned[]{JUN, JUL, AUG};
    }

    public static Måned[] høst() {
        return new Måned[]{SEP, OKT};
    }

    public static Måned[] vinter() {
        return new Måned[]{NOV, DES, JAN, FEB, MAR};
    }
}
