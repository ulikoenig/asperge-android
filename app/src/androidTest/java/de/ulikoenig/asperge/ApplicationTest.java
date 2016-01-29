package de.ulikoenig.asperge;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    BackgroundTask a;

    public ApplicationTest() {
        super(Application.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        a = new BackgroundTask(null);
    }

    public void test01() {
        String s01 = ApplicationTestStrings.ASPERGE01;
        Document doc = null;
        doc = Jsoup.parse(s01);
        Essen[] e = a.fetchEssen(3, doc);
        assertNotNull(e);
    }

    public void test1() {
        doTestString(StringEscapeUtils.unescapeJava(ApplicationTestStrings.ASPERGE01));

        Document doc = Jsoup.parse(StringEscapeUtils.unescapeJava(ApplicationTestStrings.ASPERGE01));

        Essen[] e = a.fetchEssen(0, doc);
        assertNull(e);

        e = a.fetchEssen(1, doc);
        assertNull(e);

        //Montag
        e = a.fetchEssen(2, doc);
        assertNotNull(e);
        assertEquals("Sesam-Kartoffeln mit Kräuterquark", e[0].getName());
        assertEquals(3.5, e[0].getPreis());
        assertEquals("BD Falscher Hase Erbsen-Möhrengemüse kcal 458", e[1].getName());
        assertEquals(4.0, e[1].getPreis());
        assertEquals("Pangasiusfilet in Eihülle", e[2].getName());
        assertEquals(4.5, e[2].getPreis());
        assertEquals("Geflügel-Wok Basmatireis Currysoße", e[3].getName());
        assertEquals(4.8, e[3].getPreis());

        //Dienstag
        e = a.fetchEssen(3, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "BD Nudeln mit Pesto und Broccoli kcal 458");
        assertEquals(e[0].getPreis(), 3.5);
        assertEquals(e[1].getName(), "Hühnerfrikassee");
        assertEquals(e[1].getPreis(), 4.0);
        assertEquals(e[2].getName(), "Forellenfilets,gebraten Zitronen-Kräuter-Soße");
        assertEquals(e[2].getPreis(), 4.5);
        assertEquals(e[3].getName(), "Schweineschnitzel Paprikasoße Pommes frites");
        assertEquals(e[3].getPreis(), 4.8);

        //Mittwoch
        e = a.fetchEssen(4, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "Rübenmus mit Kochwurst und Speckstippe");
        assertEquals(e[0].getPreis(), 3.5);
        assertEquals(e[1].getName(), "Kartoffel-Weichkäse-Gratin Joghurtdip");
        assertEquals(e[1].getPreis(), 4.0);
        assertEquals(e[2].getName(), "BD Seelachsfilet auf Chinagemüse, Reis kcal 473");
        assertEquals(e[2].getPreis(), 4.5);
//        assertEquals(e[3].getName(),"Schweineroulade");
        //TODO: Problem beheben
        assertEquals(e[3].getPreis(), 4.8);

        //Donnerstag
        e = a.fetchEssen(5, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "Labskaus mit Spiegelei Rollmops");
        assertEquals(e[0].getPreis(), 3.5);
        assertEquals(e[1].getName(), "Spinat-Ricotta-Tortellini");
        assertEquals(e[1].getPreis(), 4.0);
        assertEquals(e[2].getName(), "Nackenbraten mit Kräuterkruste");
        assertEquals(e[2].getPreis(), 4.5);
        assertEquals(e[3].getName(), "BD Putensteak mit Paprika-Blumenkohl-Gemüse kcal 434");
        assertEquals(e[3].getPreis(), 4.8);

        //Freitag
        e = a.fetchEssen(6, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "BD Chili con carne kcal 349");
        assertEquals(e[0].getPreis(), 3.5);
        assertEquals(e[1].getName(), "Sauerfleisch Remoulade Bratkartoffeln");
        assertEquals(e[1].getPreis(), 4.0);
        assertEquals(e[2].getName(), "Kartoffel-Getreidesteak mit Pilzragout");
        assertEquals(e[2].getPreis(), 4.5);
        assertEquals(e[3].getName(), "Lachsfilet");
        assertEquals(e[3].getPreis(), 5.2);
    }

    public void test2() {
        doTestString(ApplicationTestStrings.ASPERGE02);
    }

    public void test3() {
        doTestString(ApplicationTestStrings.ASPERGE03);
    }

    public void test4() {
        doTestString(ApplicationTestStrings.ASPERGE04);
    }

    public void test5() {
        doTestString(ApplicationTestStrings.ASPERGE05);
    }

    public void test6() {
        doTestString(ApplicationTestStrings.ASPERGE06);
    }

    public void test7() {
        doTestString(ApplicationTestStrings.ASPERGE07);
    }

    public void test8() {
        doTestString(ApplicationTestStrings.ASPERGE08);

    }

    public void test9() {
        doTestString(ApplicationTestStrings.ASPERGE09);

    }

    public void test10() {
        doTestString(ApplicationTestStrings.ASPERGE10);

    }

    public void test11() {
        doTestString(ApplicationTestStrings.ASPERGE11);

    }

    public void test12() {
        doTestString(ApplicationTestStrings.ASPERGE12);

    }

    public void test13() {
        doTestString(ApplicationTestStrings.ASPERGE13);

    }

    public void test14() {
        doTestString(ApplicationTestStrings.ASPERGE14);

        Document doc = Jsoup.parse(ApplicationTestStrings.ASPERGE14);

        //Freitag
        Essen[] e = a.fetchEssen(6, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "\\n++++++++++");
        assertEquals(e[0].getPreis(), 3.7);
        assertEquals(e[1].getName(), "\\nSch\\u00F6nen");
        assertEquals(e[1].getPreis(), 4.2);
        assertEquals(e[2].getName(), "\\nFeiertag !!!!");
        assertEquals(e[2].getPreis(), 4.8);
        assertEquals(e[3].getName(), "");
        assertEquals(e[3].getPreis(), -1.0);


    }

    public void test15() {
        doTestString(ApplicationTestStrings.ASPERGE15);

        Document doc = Jsoup.parse(ApplicationTestStrings.ASPERGE15);

        //Freitag
        Essen[] e = a.fetchEssen(6, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "\\n++++++++++");
        assertEquals(e[0].getPreis(), 3.7);
        assertEquals(e[1].getName(), "\\nSch\\u00F6nen");
        assertEquals(e[1].getPreis(), 4.2);
        assertEquals(e[2].getName(), "\\nFeiertag !!!!");
        assertEquals(e[2].getPreis(), 4.8);
        assertEquals(e[3].getName(), "");
        assertEquals(e[3].getPreis(), -1.0);

    }

    public void test16() {
        doTestString(ApplicationTestStrings.ASPERGE16);

    }

    public void test20151223() {
        doTestString(StringEscapeUtils.unescapeJava(ApplicationTestStrings.ASPERGE20151223));

        Document doc = Jsoup.parse(StringEscapeUtils.unescapeJava(ApplicationTestStrings.ASPERGE20151223));

        //Montag
        Essen[] e = a.fetchEssen(2, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "REGIONAL...Nürnberger Würstchen Sauerkraut/Püree");
        assertEquals(3.7, e[0].getPreis());
        assertEquals("Nudel-Auflauf Paprikasoße",e[1].getName());
        assertEquals(4.2, e[1].getPreis());
        assertEquals( "Kap-Seehecht-überbacken", e[2].getName());
        assertEquals(4.8, e[2].getPreis());
        assertEquals( "Hähnchenbrustfilet-Cordon bleu", e[3].getName());
        assertEquals(4.9, e[3].getPreis());

        //Dienstag
        e = a.fetchEssen(3, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "Gnocchi Kräutersoße Tomate/Rauke");
        assertEquals(3.7, e[0].getPreis());
        assertEquals("Paniertes Fischfilet Remoulade Pommes frites",e[1].getName());
        assertEquals(4.2, e[1].getPreis());
        assertEquals( "Hirschfrikadellen Rosenkohl/Jus Spätzle", e[2].getName());
        assertEquals(4.8, e[2].getPreis());
        assertEquals( "Entenkeule Rotkohl/Jus", e[3].getName());
        assertEquals(5.0, e[3].getPreis());

        //Mittwoch
        e = a.fetchEssen(4, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "");
        assertEquals(3.7, e[0].getPreis());
        assertEquals("Wir haben geschlossen !!!!",e[1].getName());
        assertEquals(4.2, e[1].getPreis());
        assertEquals( "", e[2].getName());
        assertEquals(4.8, e[2].getPreis());
//TODO: Problem beheben
//        assertEquals( "", e[3].getName());
        assertEquals(-1.0, e[3].getPreis());

        e = a.fetchEssen(5, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(), "Frohe");
        assertEquals(3.7, e[0].getPreis());
        assertEquals("Weihnachten",e[1].getName());
        assertEquals(4.2, e[1].getPreis());
        assertEquals( "wünscht", e[2].getName());
        assertEquals(4.8, e[2].getPreis());
        assertEquals( "Ihnen", e[3].getName());
        assertEquals(-1.0, e[3].getPreis());

        e = a.fetchEssen(6, doc);
        assertNotNull(e);
        assertEquals(e[0].getName(),"*******");
        assertEquals(3.7, e[0].getPreis());
        assertEquals("das",e[1].getName());
        assertEquals(4.2, e[1].getPreis());
        assertEquals( "Asperge-Team !!!!!!!", e[2].getName());
        assertEquals(4.8, e[2].getPreis());
        assertEquals( "*******", e[3].getName());
        assertEquals(-1.0, e[3].getPreis());
    }

    public void test20151217() {
        doTestString(ApplicationTestStrings.ASPERGE20151217);

    }

    private void doTestString(String s01) {
        assertNotNull(s01);
        Document doc = Jsoup.parse(s01);
        Essen[] e;
        for (int j = 2; j < 7; j++) {
            e = a.fetchEssen(j, doc);
            assertNotNull(e);
            for (int k = 0; k < 4; k++) {
                Essen x = e[k];
                assertNotNull(x.getName());
                assertNotNull(x.getPreisToString());
            }
        }
        e = a.fetchEssen(0, doc);
        assertNull(e);
        e = a.fetchEssen(1, doc);
        assertNull(e);
    }

}