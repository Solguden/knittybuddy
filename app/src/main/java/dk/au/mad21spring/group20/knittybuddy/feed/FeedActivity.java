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
        searchUser = findViewById(R.id.searchUser);
        feed = new ArrayList<>();
        usersThisUserFollows = new ArrayList<>();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //recyclerview
        feedListRv.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new FeedAdaptor(this);
        feedListRv.setAdapter(adaptor);

        //view models
        feedVM = new ViewModelProvider(this, new FeedViewModelFactory(getApplication())).get(FeedViewModel.class);
        //usersThisUserFollows = feedVM.getUsers(userId);

        //listeners
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //the viewmodel observes on the users this user is following. This can be used to observe on the projects the users have published. These projects will appear in the feed
        //the getUsersThisUSerFollows calls an async method since we need to know the users before we get the published projects
//        feedVM.getUsersThisUserFollows(userId).observe(this, new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> users) {
//                for (String user : users) {
//                    Log.d("FEED", "This user " + userId + "is following user " + user);
//                }
//                usersThisUserFollows = users;
//            }
//        });

        //there will only be observed on published projects if the user is following other users
//        if (usersThisUserFollows.size() == 0){
//            //ignore
//        } else {
//            for (String user : usersThisUserFollows) {
                feedVM.getAllPublishedProjects().observe(this, new Observer<List<Project>>() {
                    @Override
                    public void onChanged(List<Project> projects) {
                        startForegroundService();

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
//            }
//        }

        //listeners
//        followBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //insert code
//            }
//        });
//
//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //insert code
//            }
//        });

//        searchUser.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (searchUser.getText().equals("")){
//                    followBtn.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });


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

