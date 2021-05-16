package dk.au.mad21spring.group20.knittybuddy.inspiration.Models;

import java.util.List;

public class PatternResponse {
    public List<Pattern> patterns;
    public Pageninator pageninator;

    public List<Pattern> getPattern() {
        return patterns;
    }
}
