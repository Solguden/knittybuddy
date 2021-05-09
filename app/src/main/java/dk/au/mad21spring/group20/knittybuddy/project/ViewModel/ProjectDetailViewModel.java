package dk.au.mad21spring.group20.knittybuddy.project.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.feed.Feed;
import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;


public class ProjectDetailViewModel extends AndroidViewModel {

    //attributes
    private static final String TAG = "ProjectDetailViewModel";
    LiveData<List<Project>> thisProject;
    Repository repository;

    //constructor
    public ProjectDetailViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepositoryInstance();
    }

    //methods
    public LiveData<List<Project>> getProject(String userid, String name){
        if (thisProject == null){
            thisProject = repository.getProjectById(userid, name);
        }
        return thisProject;
    }

    public void updateProject(Project project){
        repository.updateProject(project);
    }

    public void addNewProject(Project project){
        repository.addProject(project);
    }
}
