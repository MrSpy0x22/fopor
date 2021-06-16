package pl.fopor.serwis.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FoPorDataUtils {
    public static String[] iconNames = {
            "home" , "accessible" , "truck" , "trophy" , "weather"
    };

    public static Map<String , String> colorsMap= Stream.of(new String[][] {
            { "lightLime" , "Limonkowy (jasny)" } , { "lime" , "Limonkowy" } ,
            { "darkLime" , "Limonkowy (ciemny)" } , { "lightGreen" , "Zielony (jasny)" } ,
            { "green" , "Zielony" } , { "darkGreen" , "Zielony (ciemny)" } ,
            { "lightEmerald" , "Szmaragdowy (jasny)" } , { "emerald" , "Szmaragdowy" } ,
            { "darkEmerald" , "Szmaragdowy (ciemny)" } , { "lightBlue" , "Niebieski (jasny)" } ,
            { "blue" , "Niebieski" } , { "darkBlue" , "Niebieski (ciemny)" } ,
            { "lightTeal" , "Turkusowy (jasny)" } , { "teal" , "Turkusowy" } ,
            { "darkTeal" , "Turkusowy (ciemny)" } , { "lightCyan" , "Cyjan (jasny)" } ,
            { "cyan" , "Cyjan" } , { "darkCyan" , "Cyjan (ciemny)" } ,
            { "lightCobalt" , "Kobaltowy (jasny)" } , { "cobalt" , "Kobaltowy" } ,
            { "darkCobalt" , "Kobaltowy (ciemny)" } , { "lightIndigo" , "Indygo (jasny)" } ,
            { "indigo" , "Indygo" } , { "darkIndigo" , "Indygo (ciemny)" } ,
            { "lightViolet" , "Fioletowy (jasny)" } , { "violet" , "Fioletowy" } ,
            { "darkViolet" , "Fioletowy (ciemny)" } , { "lightPink" , "Różowy (jasny)" } ,
            { "pink" , "Różowy" } , { "darkPink" , "Różowy (ciemny)" } ,
            { "lightMagenta" , "Magenta (jasny)" } , { "magenta" , "Magenta" } ,
            { "darkMagenta" , "Magenta (ciemny)" } , { "lightCrimson" , "Karmazynowy (jasny)" } ,
            { "crimson" , "Karmazynowy" } , { "darkCrimson" , "Karmazynowy (ciemny)" } ,
            { "lightRed" , "Czerwony (jasny)" } , { "red" , "Czerwony" } ,
            { "darkRed" , "Czerwony (ciemny)" } , { "lightOrange" , "Pomarańczowy (jasny)" } ,
            { "orange" , "Pomarańczowy" } , { "darkOrange" , "Pomarańczowy (ciemny)" } ,
            { "lightAmber" , "Byrsztynowy (jasny)" } , { "amber" , "Byrsztynowy" } ,
            { "darkAmber" , "Byrsztynowy (ciemny)" } , { "lightYellow" , "Zółty (jasny)" } ,
            { "yellow" , "Zółty" } , { "darkYellow" , "Zółty (ciemny)" } ,
            { "lightBrown" , "Brązowy (jasny)" } , { "brown" , "Brązowy" } ,
            { "darkBrown" , "Brązowy (ciemny)" } , { "lightOlive" , "Oliwkowy (jasny)" } ,
            { "olive" , "Oliwkowy" } , { "darkOlive" , "Oliwkowy (ciemny)" } ,
            { "lightSteel" , "Stalowy (jasny)" } , { "steel" , "Stalowy" } ,
            { "darkSteel" , "Stalowy (ciemny)" } , { "lightMauve" , "Fiołkowy (jasny)" } ,
            { "mauve" , "Fiołkowy" } , { "darkMauve" , "Fiołkowy (ciemny)" } ,
            { "lightTaupe" , "Szarobrązowy (jasny)" } ,
            { "taupe" , "Szarobrązowy" } , { "darkTaupe" , "Szarobrązowy (ciemny)" } ,
            { "lightGray" , "Szary (jasny)" } , { "gray" , "Szary" } ,
            { "darkGray" , "Szary (ciemny)" } , { "lightGrayBlue" , "Szaro-niebieski (jasny)" } ,
            { "grayBlue" , "Szaro-niebieski" } , { "darkGrayBlue" , "Szaro-niebieski (ciemny)"}
    }).collect(Collectors.collectingAndThen(
            Collectors.toMap(k -> k[0] , v -> v[1]) ,
            Collections::<String , String> unmodifiableMap
    ));
}
