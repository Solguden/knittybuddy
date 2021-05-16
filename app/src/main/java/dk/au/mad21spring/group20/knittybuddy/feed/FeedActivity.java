package dk.au.mad21spring.group20.knittybuddy.feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.ForegroundService;
import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;


public class FeedActivity extends AppCompatActivity implements FeedAdaptor.IStarClicker {

    //widgets
    Button goBackBtn;
    RecyclerView feedListRv;
    EditText searchUser;

    //app state
    private FeedViewModel feedVM;

    //attributes
    private List<Project> feed;
    private List<String> usersThisUserFollows;

    private FeedAdaptor adaptor;
    private String userId;

    //lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //instantiates widgets and objects
        goBackBtn = findViewById(R.id.goBackFeedBtn);
        feedListRv = findViewById(R.id.projectFeedRv);
        feed = new ArrayList<>();
        usersThisUserFollows = new ArrayList<>();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //recyclerview
        feedListRv.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new FeedAdaptor(this);
        feedListRv.setAdapter(adaptor);

        //view models
        feedVM = new ViewModelProvider(this, new FeedViewModelFactory(getApplication())).get(FeedViewModel.class);
                feedVM.getAllPublishedProjects().observe(this, new Observer<List<Project>>() {
                    @Override
                    public void onChanged(List<Project> projects) {
                        feed.clear();
                        List<Project> feedReverse = new ArrayList<>();

                        for (Project p : projects) {
                            Log.d("FEED", "project published: " + p.getName() + "by user " + p.getUserId());
                            if (!p.getUserId().equals(userId)){
                                feedReverse.add(p);
                            }
                        }
                        //reverses the list of projects for the feed
                        int size = feedReverse.size()-1;
                        for(int i=size;i>=0;i--){
                            feed.add(feedReverse.get(i));
                        }

                        //feed = projects;
                        adaptor.updateFeed(feed);
                    }
                });

        //listeners
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startForegroundService();
    }

    //from IStarClicker
    @Override
    public void onRemoveStar(int projectIndex) {
        feedVM.removeStar(userId, feed.get(projectIndex));
        Toast.makeText(this, R.string.starRemoved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddStar(int projectIndex) {
        feedVM.addStar(userId, feed.get(projectIndex));
        Toast.makeText(this, R.string.starAdded, Toast.LENGTH_SHORT).show();
    }

    //notification
    private void startForegroundService()
    {
        Intent foregroundIntent = new Intent(this, ForegroundService.class);
        startService(foregroundIntent);
    }
}

