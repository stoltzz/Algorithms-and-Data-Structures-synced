package hjelpeklasser;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Integer[] a = {8,8,8,1,6,9,14,4,7,12,15,3,5,8,8};      // en tabell

        SBinTre<Integer> tre1 = SBinTre.sbintre(Stream.of(a));    // et binært søketre

        System.out.println(tre1.antall(8));

    }
}