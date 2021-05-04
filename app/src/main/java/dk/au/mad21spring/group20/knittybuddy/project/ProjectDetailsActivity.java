package dk.au.mad21spring.group20.knittybuddy.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dk.au.mad21spring.group20.knittybuddy.R;

public class ProjectDetailsActivity extends AppCompatActivity {

    //widgets
    TextView txtProjectName;
    TextView txtProjectDetails;
    Button btnGoBack;

    //attributes
    private String projectId;

    //life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        //instantiation of widgets
        txtProjectName = findViewById(R.id.txt_projectDetailHeader);
        txtProjectDetails = findViewById(R.id.txt_projectDetailText);
        btnGoBack = findViewById(R.id.btn_projectDetail_back);
        projectId = ""; //default value

        //get data from ProjectListActivity, who started this activity
        Intent data = getIntent();
        projectId = data.getStringExtra("id"); //TO DO: hardcode name and default

        //button listener
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateUI(projectId);
    }

    //methods
    private void updateUI(String id){
        if  (id != ""){
            //implement code which gets the project info from db (viewmodel)
        }
    }
}