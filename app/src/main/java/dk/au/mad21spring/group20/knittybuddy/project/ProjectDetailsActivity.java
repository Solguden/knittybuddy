package dk.au.mad21spring.group20.knittybuddy.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;

public class ProjectDetailsActivity extends AppCompatActivity {

    //widgets
    TextView txtProjectName;
    TextView txtProjectDetails;
    Button btnGoBack;

    //attributes
    private int projectId;
    //test
    private List<Project> projectList;

    //life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        //instantiation of widgets
        txtProjectName = findViewById(R.id.txt_projectDetailHeader);
        txtProjectDetails = findViewById(R.id.txt_projectDetailText);
        btnGoBack = findViewById(R.id.btn_projectDetail_back);
        projectId = 0; //default value

        //get data from ProjectListActivity, who started this activity
        Intent data = getIntent();
        projectId = data.getIntExtra("id",0); //TO DO: hardcode name and default

        //button listener
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //test
        projectList = new ArrayList<Project>();
        Project testProject1 = new Project(1, "Sommerkjole", "Fin luftig kjole", "111", "pdfLink");
        Project testProject2 = new Project(2, "Vinter trøje", "Dejlig varm sweather", "222", "pdfLink");
        projectList.add(testProject1);
        projectList.add(testProject2);
        updateUI(projectId);
    }

    //methods
    private void updateUI(int id){
        if  (id != 0){
            txtProjectName.setText(projectList.get(id).getName());
            txtProjectDetails.setText(projectList.get(id).getDescription());
        }
        else{
            Project prjDefault = new Project(0, "default", "default", "default", "default");
            txtProjectName.setText(prjDefault.getName());
            txtProjectDetails.setText(prjDefault.getDescription());
        }

    }
}