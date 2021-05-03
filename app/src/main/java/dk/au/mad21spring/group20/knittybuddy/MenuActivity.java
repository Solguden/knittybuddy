package dk.au.mad21spring.group20.knittybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dk.au.mad21spring.group20.knittybuddy.Feed.FeedActivity;
import dk.au.mad21spring.group20.knittybuddy.Inspiration.InspirationListActivity;
import dk.au.mad21spring.group20.knittybuddy.Project.ProjectListActivity;

public class MenuActivity extends AppCompatActivity {

    //widgets
    private Button btnFeed;
    private Button btnProjects;
    private Button btnInspiration;

    //attributes
    public static final int REQUEST_CODE_FEED = 101;
    public static final int REQUEST_CODE_PROJECTS = 201;
    public static final int REQUEST_CODE_INSPIRATION = 301;

    //life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //instantiation of widgets
        btnFeed = findViewById(R.id.btn_menu_feed);
        btnProjects = findViewById(R.id.btn_menu_project);
        btnInspiration = findViewById(R.id.btn_menu_Inspiration);

        //button listeners
        btnFeed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToFeed();
            }
        });

        btnProjects.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToProjectList();
            }
        });

        btnInspiration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToInspiration();
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

    private void goToInspiration(){
        Intent intent = new Intent(this, InspirationListActivity.class);
        startActivityForResult(intent,REQUEST_CODE_INSPIRATION );
    }

}