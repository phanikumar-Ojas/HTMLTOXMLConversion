package com.org.xml.util;

import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeUtils {
    public static Map<String, Integer> getMonthsMap(Locale locale) {

        // Create a map to store months and their corresponding integers
        Map<String, Integer> monthMap = new HashMap<>();

        // Iterate over each month and add it to the map
        for (Month month : Month.values()) {
            // Get the localized month name
            String monthName = month.getDisplayName(TextStyle.SHORT, locale);

            // Get the month value as an integer (1-based index)
            int monthValue = month.getValue();

            // Add the month to the map
            monthMap.put(monthName, monthValue);
        }
        return monthMap;
    }

    public static List<String> getYears(){
        return IntStream.rangeClosed(2000, Year.now().getValue())
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

}
