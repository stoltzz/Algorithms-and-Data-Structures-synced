package hjelpeklasser;

import eksempelklasser.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringJoiner;

public class Main {

    public static void main(String... args)   // hovedprogram
    {

        int[] posisjon = {1,2,3,4,5,6,7,10,11,13,14,22,23,28,29};  // posisjoner og
        String[] verdi = "EIBGAHKLODNMCJF".split("");              // verdier i nivåorden
        BinTre<String> tre = new BinTre<>(posisjon, verdi);        // en konstruktør

        StringJoiner s = new StringJoiner(", " ,"[", "]");         // StringJoiner
        tre.preorden(tegn -> s.add(tegn));                         // tegn = String

        System.out.println(s);
        // Utskrift: [E, I, G, A, L, O, M, C, B, H, D, K, N, J, F]
        }
    }
