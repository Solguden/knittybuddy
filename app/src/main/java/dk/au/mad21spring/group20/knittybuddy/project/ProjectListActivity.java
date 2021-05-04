package dk.au.mad21spring.group20.knittybuddy.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;

public class ProjectListActivity extends AppCompatActivity implements ProjectAdapter.IProjectItemClickedListener {

    //widgets
    Button btnAddNewProject;
    Button btnGoBack;
    RecyclerView rvProjectList;

    //attributes
    private ProjectAdapter adapter;
    private List<Project> projectList;

    //life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        //instantiation of widgets
        btnAddNewProject = findViewById(R.id.btn_project_addProject);
        btnGoBack = findViewById(R.id.btn_projectList_back);
        rvProjectList = findViewById(R.id.rv_project_list);
        projectList = new ArrayList<>();

        //recyclerview
        adapter = new ProjectAdapter(this);
        rvProjectList.setLayoutManager(new LinearLayoutManager(this));
        rvProjectList.setAdapter(adapter);

        //button listeners
        btnAddNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProject();
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        //test
        Project testProject1 = new Project("1", "Sommerkjole", "Fin luftig kjole", "111", "pdfLink");
        Project testProject2 = new Project("2", "Vinter trøje", "Dejlig varm sweather", "222", "pdfLink");
        projectList.add(testProject1);
        projectList.add(testProject2);
        adapter.updateProjectList(projectList);
    }

    //methods
    //user can add a new project
    private void addProject() {
        //her skal detail activity åbnes
        //den skal være tom
    }

    //user opens an existing project
    @Override
    public void onProjectClicked(int index) {

        Intent detailsIntent = new Intent(this, ProjectDetailsActivity.class);
        detailsIntent.putExtra("id",projectList.get(index).getId()); //TO DO: hardcode name
        startActivity(detailsIntent);
        //startActivityForResult(detailsIntent, 100); //TO DO: hardcode requestCode
    }
}