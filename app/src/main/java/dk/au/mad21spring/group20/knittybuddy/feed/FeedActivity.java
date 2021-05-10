package dk.au.mad21spring.group20.knittybuddy.feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.PDFActivity;
import dk.au.mad21spring.group20.knittybuddy.R;


public class FeedActivity extends AppCompatActivity {
    FeedViewModel feedVM;
    Button addButton;
    Button ownerButton;
    Button pdfButton;
    List<Feed> feedList = new ArrayList<>();
    TextView txtFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        feedVM = new ViewModelProvider(this).get(FeedViewModel.class);
        feedVM.getFeed().observe(this, new Observer<List<Feed>>() {
            @Override
            public void onChanged(List<Feed> feed) {
                feedList = feed;
                updateUI();
            }
        });

        addButton = findViewById(R.id.randomFeedBtn);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AddRandom();
            }
        });

        ownerButton = findViewById(R.id.ownerIdBtn);
        ownerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getBasedOnOwnerId();
            }
        });

        pdfButton = findViewById(R.id.pdfBtn);
        pdfButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotoPDF();
            }
        });
        
        txtFeed = findViewById(R.id.txtFeed);

        updateUI();
    }

    private void gotoPDF(){
        Intent intent = new Intent(this, PDFActivity.class);
        startActivityForResult(intent,123 );
    }

    private void AddRandom(){
        feedVM.addRandomFeed();
    }

    private void getBasedOnOwnerId(){
        feedVM.getByOwnerId(84).observe(this, new Observer<List<Feed>>() {
            @Override
            public void onChanged(List<Feed> feed) {
                feedList = feed;
                updateUI();
            }
        });
    }

    private void updateUI(){
        if(feedList.size()<1) {
            txtFeed.setText("No feed");
        } else {
            String display = "Feed count: "+feedList.size() ;
            for(Feed f : feedList){
                display += "\n" + f.getTopic()+ " " + f.getDescription() + " " + f.getDifficilty() +  " " + f.getOwnerId();
            }
            txtFeed.setText(display);
        }
    }
}