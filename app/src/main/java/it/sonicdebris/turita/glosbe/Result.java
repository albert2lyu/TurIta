package it.sonicdebris.turita.glosbe;

import java.util.ArrayList;
import java.util.List;

public class Result {

    public String result;
    public String phrase;
    public String from;
    public String dest;
    public List<Tuc> tuc = new ArrayList<Tuc>();

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Result: ").append(result).append("\n");
        sb.append("Phrase: ").append(phrase).append("\n");
        sb.append("From: ").append(from).append(" To: ").append(dest).append("\n");

        for (Tuc t : tuc)
        {
            sb.append("  Tuc elem: ");
            if (null!=t.phrase)
                sb.append(t.phrase.text).append(" (").append(t.phrase.language).append(")\n");

            for(Meaning m : t.meanings)
            {
                sb.append("    [").append(m.language).append("] ").append(m.text).append("\n");
            }
        }
        return sb.toString();
    }

}
