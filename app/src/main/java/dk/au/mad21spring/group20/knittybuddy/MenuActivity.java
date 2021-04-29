package dk.au.mad21spring.group20.knittybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dk.au.mad21spring.group20.knittybuddy.feed.FeedActivity;

public class MenuActivity extends AppCompatActivity {

    private Button feedBtn;
    public static final int REQUEST_CODE_FEED = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        feedBtn = findViewById(R.id.feedBtn);
        feedBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToFeed();
            }
        });

    }

    private void goToFeed(){
        Intent intent = new Intent(this, FeedActivity.class);
        startActivityForResult(intent,REQUEST_CODE_FEED );

    }

}