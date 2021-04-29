package dk.au.mad21spring.group20.knittybuddy.feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import dk.au.mad21spring.group20.knittybuddy.R;


public class FeedActivity extends AppCompatActivity {
    FeedViewModel feedVM;
    Button addButton;
    ArrayList<Feed> feedList = new ArrayList<>();
    TextView txtFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        feedVM = new ViewModelProvider(this).get(FeedViewModel.class);
        feedVM.getFeed().observe(this, new Observer<ArrayList<Feed>>() {
            @Override
            public void onChanged(ArrayList<Feed> feed) {
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

        txtFeed = findViewById(R.id.txtFeed);

        updateUI();
    }

    private void AddRandom(){
        feedVM.addRandomFeed();
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