package de.ulikoenig.asperge;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


/**
 * Created by ukoenig on 09.12.15.
 */
public class Essen {
    private String mName = null;
    private double mPreis = -1;
    NumberFormat numberDE = NumberFormat.getNumberInstance(new Locale("de"));
    NumberFormat numberEN = NumberFormat.getNumberInstance(new Locale("en"));

    public Essen(String name, double preis) {
        mName = name;
        mPreis = preis;
    }

    public Essen(String name, String preis) {

        //Newlines und € Zeichen entfernen.
        mName = name.replace("\\n", "").replace("\\r", "").replace("\n", "").replace("\r", "");
        String p  = preis.replace("\\n", "").replace("\\r", "").replace("\n", "").replace("\r", "").replaceAll("€", "").replaceAll(" ","");

              try {
                mPreis = numberDE.parse(p).doubleValue();
            } catch (ParseException e) {
                //e.printStackTrace();
                try {
                    mPreis = numberEN.parse(p).doubleValue();
                } catch (ParseException e1) {
                    if (p.contains("+++")) {
                        mPreis = -1;
                    } else if (p.contains("***")){
                        mPreis = -1;
                    } else if (p.length() == 0){
                        mPreis = -1;
                    } else
                    {
                        e1.printStackTrace();
                    }
                }
            }
    }

    public String getName(){
        return mName;
    }

    public double getPreis(){
        return mPreis;
    }

    public String getPreisToString(){
        return String.format("%10.2f", mPreis)+"€";
    }

}
