package dk.au.mad21spring.group20.knittybuddy.project.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.model.Project;


public class ProjectDetailViewModel extends AndroidViewModel {

    //attributes
    private static final String TAG = "ProjectDetailViewModel";
    LiveData<Project> thisProject;

    //constructor
    public ProjectDetailViewModel(@NonNull Application application) {
        super(application);
    }

    //methods
    public LiveData<Project> getProject(String id){
        if (thisProject == null){
            //thisProject = getProject(); TO DO: build this method in the repository
        }
        return thisProject;
    }
}
