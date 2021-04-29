package dk.au.mad21spring.group20.knittybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dk.au.mad21spring.group20.knittybuddy.feed.FeedActivity;
import dk.au.mad21spring.group20.knittybuddy.project.ProjectEditActivity;
import dk.au.mad21spring.group20.knittybuddy.project.ProjectListActivity;

public class MenuActivity extends AppCompatActivity {

    //widgets
    private Button feedBtn;
    private Button projectsBtn;

    //attributes
    public static final int REQUEST_CODE_FEED = 101;
    public static final int REQUEST_CODE_PROJECTS = 201;

    //life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        feedBtn = findViewById(R.id.feedBtn);
        projectsBtn = findViewById(R.id.btn_menu_project);

        feedBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToFeed();
            }
        });

        projectsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToProjectList();
            }
        });
    }

    //methods
    private void goToFeed(){
        Intent intent = new Intent(this, FeedActivity.class);
        startActivityForResult(intent,REQUEST_CODE_FEED );
    }

    private void goToProjectList(){
        Intent intent = new Intent(this, ProjectListActivity.class);
        startActivityForResult(intent,REQUEST_CODE_PROJECTS );
    }

}