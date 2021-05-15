package dk.au.mad21spring.group20.knittybuddy.inspiration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.ComPattern;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.DetailPattern;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.User;

public class InspirationDetailActivity extends AppCompatActivity {

    private DetailPattern patternDetails;
    int patternID;

    private ImageView patternImage;
    private ImageView authorImage;
    private TextView patternName;
    private TextView authorName;

    private Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspiration_detail);

        patternImage = findViewById(R.id.InspirationDetailPatternPhoto);
        authorImage = findViewById(R.id.InspirationDetailAuthorPhoto);
        patternName = findViewById(R.id.InspirationDetailPatternName);
        authorName = findViewById(R.id.InspirationDetailAuthorName);

        backBtn = findViewById(R.id.btn_inspirationDetail_back);

        patternDetails = new DetailPattern();

        Intent detailsIntent = getIntent();
        patternDetails.id = detailsIntent.getIntExtra("thisID", 0);
        patternDetails.patternName = detailsIntent.getStringExtra("thisPatternName");
        patternDetails.patternPhoto = detailsIntent.getStringExtra("thisPatternPhoto");
        patternDetails.authorName = detailsIntent.getStringExtra("thisAuthorName");
        patternDetails.authorPhoto = detailsIntent.getStringExtra("thisAuthorPhoto");


        Glide.with(patternImage.getContext()).load(patternDetails.patternPhoto).into(patternImage);
        patternName.setText(patternDetails.patternName);
        Glide.with(authorImage.getContext()).load(patternDetails.authorPhoto).into(authorImage);
        authorName.setText(patternDetails.authorName);




        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}