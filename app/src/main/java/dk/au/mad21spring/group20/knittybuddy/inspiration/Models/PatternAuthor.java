package dk.au.mad21spring.group20.knittybuddy.inspiration.Models;

import org.json.JSONObject;

public class PatternAuthor extends JSONObject {
    public int crochet_pattern_count;
    public int favorites_count;
    public int id;
    public int knitting_pattern_count;
    public String name;
    public int patterns_count;
    public String permalink;
    public User[] users;
}

//"pattern_author": {
//                "crochet_pattern_count": 0,
//                "favorites_count": 19854,
//                "id": 45502,
//                "knitting_pattern_count": 179,
//                "name": "tincanknits",
//                "patterns_count": 179,
//                "permalink": "tincanknits",
//                "users": [
//                    {
//                        "id": 1415368,
//                        "username": "tincanknits",
//                        "tiny_photo_url": "https://avatars-d.ravelrycache.com/tincanknits/633077163/IMG_0463_tiny.jpg",
//                        "small_photo_url": "https://avatars-d.ravelrycache.com/tincanknits/633077163/IMG_0463_small.jpg",
//                        "photo_url": "https://avatars-d.ravelrycache.com/tincanknits/633077163/IMG_0463_large.jpg"
//                    }
//                ]
//            },
