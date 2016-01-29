package de.ulikoenig.asperge;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ukoenig on 30.12.15.
 */
public class Util {

    /**
     * @param tag Montag: 2
     *            Dienstag: 3
     *            Mittwoch: 4
     *            Donnerstag: 5
     *            Freitag: 6
     * @return
     */
    public static Essen[] fetchEssen(int tag, Document doc) {
        if ((tag < 2) || (tag > 6)) {
            //Kein Essen
            return null;
        }
        ;
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("de"));
        String[] dayNames = symbols.getWeekdays();
        return Util.parseMenu(dayNames[tag], doc);
    }


    public static Essen[] calculate() throws InvalidParameterException, UnknownHostException, SocketTimeoutException {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.asperge.de/speiseplan/").timeout(5000).get();
            if (Asperge.test) {
                dayOfWeek = 3;
                doc = Jsoup.parse(StringEscapeUtils.unescapeJava(TestString.ASPERGE01));
            }
            return Util.fetchEssen(dayOfWeek, doc);
        } catch (UnknownHostException e) {
            //Kein Internet
            throw e;
        } catch (SocketTimeoutException e) {
            //Kein Internet
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Essen[] parseMenu(String dayName, Document doc) {
        Essen essen[] = new Essen[4];
        Elements qWochentag;
        try {
            Elements body = doc.select("tbody");
            Elements titelleiste = body.select("tr:contains(Asperge-Spezial)");

            Elements qspalte1 = titelleiste.select("td:eq(1)");
            Elements qspalte2 = titelleiste.select("td:eq(2)");
            Elements qspalte3 = titelleiste.select("td:eq(3)");
            Elements qspalte4 = titelleiste.select("td:eq(4)");


            Essen spalte1 = new Essen(qspalte1.select("b").text(), qspalte1.select("font:eq(1)").select("font:eq(0)").text().substring(2));
            Essen spalte2 = new Essen(qspalte2.select("b").text(), qspalte2.select("font:eq(1)").select("font:eq(0)").text().substring(2));
            Essen spalte3 = new Essen(qspalte3.select("b").text(), qspalte3.select("font:eq(1)").select("font:eq(0)").text().substring(2));
            Essen spalte4 = new Essen(qspalte4.select("b").text(), -1);

            qWochentag = body.select("tr:contains(" + dayName + ")");
            essen[0] = new Essen(qWochentag.select("td:eq(1)").text(), spalte1.getPreis());
            essen[1] = new Essen(qWochentag.select("td:eq(2)").text(), spalte2.getPreis());
            essen[2] = new Essen(qWochentag.select("td:eq(3)").text(), spalte3.getPreis());
        } catch (StringIndexOutOfBoundsException e) {
            //e.printStackTrace();
            return null;
        }


        String name = "";
        try {
            name = qWochentag.select("td:eq(4)").select("font:eq(0)[color=\"#9C6263\"]").html().split("<b>")[0];
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
        String preis = "-1";
        try {
            preis = qWochentag.select("td:eq(4)").select("font:eq(1)").select("font:eq(0)").select("b").text().substring(2);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
        essen[3] = new Essen(name, preis);
        return essen;
    }

}
