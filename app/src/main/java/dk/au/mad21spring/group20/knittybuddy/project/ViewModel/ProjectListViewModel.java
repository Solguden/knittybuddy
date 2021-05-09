package dk.au.mad21spring.group20.knittybuddy.project.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

public class ProjectListViewModel extends AndroidViewModel {

    //attributes
    private static final String TAG = "ProjectListViewModel";
    LiveData<List<Project>> projects;
    Repository repository;

    //constructor
    public ProjectListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepositoryInstance();
        projects = getAllProjects();
    }

    //methods
    public LiveData<List<Project>> getAllProjects() { return projects; }

    public void addProject(Project project){ //TO DO: build this method in the repository
    }

    public Project getProject(String id){ //TO DO: build this method in the repository
        return new Project();
    }
}
