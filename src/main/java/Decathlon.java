/**
 * Created by doom on 07.08.2016.
 */
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class Decathlon {

    public static void main(String[] args) throws IOException
    {
        Document doc;
        String url = "https://en.wikipedia.org/wiki/List_of_world_records_in_athletics";

        doc = Jsoup.connect(url).get();
        Elements rows = doc.select("tr");
        Map<String, String> map = new HashMap<String, String>();

        Elements cols = rows.select("td");

        int i = 0, j = 0;
        String[][] array = new String[rows.size()][cols.size()];

        for (Element row : rows) {
            Elements columns = row.select("td");
            for (Element column : columns)
            {
                array[i][j] = column.text();
                j++;
            }
            i++;
            j = 0;
        }

        // main problem so far - men and women record lists are COMBINED
        // why not separate lists??? so actually records i need are somewhere in the first 50 table rows
        // so i can't calculate sum for women so far
        for (int k = 0; k < 50; k++){
            if (array[k][0] != null && array[k][0].contains("100 m (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
            else if (array[k][0] != null && array[k][0].contains("Long jump (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
            else if (array[k][0] != null && array[k][0].contains("Shot put (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
            else if (array[k][0] != null && array[k][0].contains("High jump (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
            else if (array[k][0] != null && array[k][0].contains("400 m (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
            else if (array[k][0] != null && array[k][0].contains("110 m hurdles (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
            else if (array[k][0] != null && array[k][0].contains("Discus throw (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
            else if (array[k][0] != null && array[k][0].contains("Pole vault (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
            else if (array[k][0] != null && array[k][0].contains("Javelin (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
            else if (array[k][0] != null && array[k][0].contains("1500 m (progression)"))
                map.put(array[k][0].substring(0, array[k][0].indexOf(" (")), array[k][1]);
        }

        HashMap<String, Double> finalMap = new HashMap<String, Double>();

        // strip results to actual numbers
        for (Map.Entry<String, String> pair : map.entrySet())
        {
            String s = pair.getValue();
            if (s.indexOf(' ') > -1)
            {
                s = s.substring(0, s.indexOf(' '));
                map.put(pair.getKey(), s);
            }
        }

        // creating new map with double values, don't forget about 1500m result w/ minutes
        for (Map.Entry<String, String> pair : map.entrySet())
        {
            String s = pair.getValue();
            String s1;
            if (s.indexOf(':') > -1)
            {
                s1 = s.substring(s.indexOf(':') + 1, s.length());
                s = s.substring(0, s.indexOf(':'));
                finalMap.put(pair.getKey(), Double.parseDouble(s)*60 + Double.parseDouble(s1));
            } else finalMap.put(pair.getKey(), Double.parseDouble(s));
        }

        // list of WRs, actually just in case
        for (Map.Entry<String, String> pair : map.entrySet()) {
            System.out.println(pair.getKey() + "\t" + pair.getValue());
        }

        /*for (Map.Entry<String, Double> pair : finalMap.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }*/

        // compiling decathlon points sum for our perfect athlete
        double sum = 0;
        for (Map.Entry<String, Double> pair : finalMap.entrySet())
        {
            if (pair.getKey() != null && pair.getKey().contains("100 m"))
            {
                sum += 25.4348*(Math.pow(abs(pair.getValue() - 18), 1.81));
            }
            if (pair.getKey() != null && pair.getKey().contains("Long jump"))
            {
                sum += 90.5674*(Math.pow(abs(pair.getValue() - 2.2), 1.4));
            }
            if (pair.getKey() != null && pair.getKey().contains("Shot put"))
            {
                sum += 51.39*(Math.pow(abs(pair.getValue() - 1.5), 1.05));
            }
            if (pair.getKey() != null && pair.getKey().contains("High jump"))
            {
                sum += 585.64*(Math.pow(abs(pair.getValue() - 0.75), 1.42));
            }
            if (pair.getKey() != null && pair.getKey().contains("400 m"))
            {
                sum += 1.53775*(Math.pow(abs(pair.getValue() - 82), 1.81));
            }
            if (pair.getKey() != null && pair.getKey().contains("110 m"))
            {
                sum += 5.74354*(Math.pow(abs(pair.getValue() - 28.5), 1.92));
            }
            if (pair.getKey() != null && pair.getKey().contains("Discus throw"))
            {
                sum += 12.91*(Math.pow(abs(pair.getValue() - 4), 1.1));
            }
            if (pair.getKey() != null && pair.getKey().contains("Pole vault"))
            {
                sum += 140.182 * (Math.pow(abs(pair.getValue() - 1), 1.35));
            }
            if (pair.getKey() != null && pair.getKey().contains("Javelin throw"))
            {
                sum += 10.14*(Math.pow(abs(pair.getValue() - 7), 1.08));
            }
            if (pair.getKey() != null && pair.getKey().contains("1500 m"))
            {
                sum += 0.03768*(Math.pow(abs(pair.getValue() - 480), 1.85));
            }
        }
        System.out.println("Decathlon maximum sum is currently : " + round(sum));

    }
}
