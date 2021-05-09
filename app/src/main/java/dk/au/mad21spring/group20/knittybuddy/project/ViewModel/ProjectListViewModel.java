package dk.au.mad21spring.group20.knittybuddy.project.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import dk.au.mad21spring.group20.knittybuddy.model.Project;

public class ProjectListViewModel extends AndroidViewModel {

    //attributes
    private static final String TAG = "ProjectListViewModel";
    MutableLiveData<ArrayList<Project>> projects;

    //constructor
    public ProjectListViewModel(@NonNull Application application) {
        super(application);

        projects = getAllProjects();
    }

    //methods
    public MutableLiveData<ArrayList<Project>> getAllProjects() {
        if(projects==null){
            //loadData(); TO DO: build this method in the repository
        }
        return projects;
    }

    public void addProject(Project project){ //TO DO: build this method in the repository
    }

    public Project getProject(String id){ //TO DO: build this method in the repository
        return new Project();
    }
}
