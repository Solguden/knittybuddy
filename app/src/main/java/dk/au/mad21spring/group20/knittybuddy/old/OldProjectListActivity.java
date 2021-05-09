package dk.au.mad21spring.group20.knittybuddy.old;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.project.ProjectAdapter;
import dk.au.mad21spring.group20.knittybuddy.project.ViewModel.ProjectListViewModel;

public class OldProjectListActivity extends AppCompatActivity implements ProjectAdapter.IProjectItemClickedListener {

    //widgets
    Button btnAddNewProject;
    Button btnGoBack;
    RecyclerView rvProjectList;

    //app state
    private ProjectListViewModel listVM;

    //attributes
    private ProjectAdapter adapter;
    private List<Project> projectList;

    //life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_project_list);

        //instantiation of widgets
        btnAddNewProject = findViewById(R.id.btn_project_addProject);
        btnGoBack = findViewById(R.id.btn_projectList_back);
        rvProjectList = findViewById(R.id.rv_project_list);
        projectList = new ArrayList<>();

        //recyclerview
        adapter = new ProjectAdapter(this);
        rvProjectList.setLayoutManager(new LinearLayoutManager(this));
        rvProjectList.setAdapter(adapter);

        //view models
        listVM = new ViewModelProvider(this).get(ProjectListViewModel.class);
//        listVM.getAllProjects().observe(this, new Observer<ArrayList<Project>>() {
//            @Override
//            public void onChanged(ArrayList<Project> projects) {
//                if (projects.size() == 0){
//                    //create data
//                }
//                projectList = projects;
//                adapter.updateProjectList(projectList);
//            }
//        });

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
        Project testProject1 = new Project("1", "Sommerkjole", "Fin luftig kjole", 111, "pdfLink", false, "1");
        Project testProject2 = new Project("2", "Vinter trøje", "Dejlig varm sweather", 111, "pdfLink", false, "1");
        projectList.add(testProject1);
        projectList.add(testProject2);
        adapter.updateProjectList(projectList);
    }

    //methods
    //user can add a new project
    private void addProject() {
        Intent detailsIntent = new Intent(this, OldProjectDetailsActivity.class);
        startActivity(detailsIntent);
    }

    //user opens an existing project
    @Override
    public void onProjectClicked(int index) {
        Intent detailsIntent = new Intent(this, OldProjectDetailsActivity.class);
        detailsIntent.putExtra("id",projectList.get(index).getId()); //TO DO: hardcode name
//        startActivity(detailsIntent);
        startActivityForResult(detailsIntent, 100); //TO DO: hardcode requestCode
    }
}