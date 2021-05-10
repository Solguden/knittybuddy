package dk.au.mad21spring.group20.knittybuddy.inspiration.Models;

import org.json.JSONObject;

public class Pattern extends JSONObject {
    public PatternAuthor designer;
    public Photo first_photo;
    public Boolean free;
    public int id;
    public String name;
    public PatternAuthor pattern_author;
    public String permalink;
}


//data class Pattern(
//    val id: Long,
//    val name: String,
//    val permalink: String,
//    @Json(name = "first_photo") val firstPhoto: Photo,
//    val designer: PatternAuthor,
//    @Json(name = "pattern_author") val patternAuthor: PatternAuthor
//)