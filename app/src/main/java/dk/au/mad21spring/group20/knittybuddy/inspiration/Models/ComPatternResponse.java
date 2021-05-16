package dk.au.mad21spring.group20.knittybuddy.inspiration.Models;

import java.util.List;

public class ComPatternResponse {
    public List<ComPattern> patterns;
    public Pageninator pageninator;


    public List<ComPattern> getPattern() {
        return patterns;
    }
}
