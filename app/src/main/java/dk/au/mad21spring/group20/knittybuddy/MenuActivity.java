package dk.au.mad21spring.group20.knittybuddy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import dk.au.mad21spring.group20.knittybuddy.feed.FeedActivity;
import dk.au.mad21spring.group20.knittybuddy.inspiration.InspirationListActivity;
import dk.au.mad21spring.group20.knittybuddy.project.ProjectMainActivity;

public class MenuActivity extends AppCompatActivity {

    //widgets
    private Button btnFeed;
    private Button btnProjects;
    private Button btnInspiration;

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
        startActivityForResult(intent,Constants.REQUEST_CODE_FEED );
    }

    private void goToProjectList(){
        Intent intent = new Intent(this, ProjectMainActivity.class);
        startActivityForResult(intent,Constants.REQUEST_CODE_PROJECTS );
        Log.d("create project", "projectMainActivity has been created");
    }

    private void goToInspiration(){
        Intent intent = new Intent(this, InspirationListActivity.class);
        startActivityForResult(intent,Constants.REQUEST_CODE_INSPIRATION );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.REQUEST_CODE_PROJECTS)
        {
            if(resultCode == RESULT_OK)
            {
                Log.d("Finish", "projectMainActivity has been finished");
            }
        }
    }

}