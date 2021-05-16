package dk.au.mad21spring.group20.knittybuddy.feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;


public class FeedActivity extends AppCompatActivity {

    //widgets
    Button followBtn;
    RecyclerView feedListRv;
    EditText searchUser;

    //app state
    private FeedViewModel feedVM;

    //attributes
    private List<Project> projectList;
    private FeedAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //instantiates widgets and objects
        followBtn = findViewById(R.id.followFeedButton);
        feedListRv = findViewById(R.id.projectFeedRv);
        searchUser = findViewById(R.id.searchUser);
        projectList = new ArrayList<>();

        //recyclerview
        feedListRv.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new FeedAdaptor();
        feedListRv.setAdapter(adaptor);

        //view models
        //feedVM = new ViewModelProvider(this);
    }

}