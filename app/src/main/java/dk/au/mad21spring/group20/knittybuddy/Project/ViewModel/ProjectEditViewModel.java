package dk.au.mad21spring.group20.knittybuddy.Project.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dk.au.mad21spring.group20.knittybuddy.project.Project;

public class ProjectEditViewModel extends AndroidViewModel {

    //attributes
    private static final String TAG = "ProjectEditViewModel";
    LiveData<Project> thisProject;

    //constructor
    public ProjectEditViewModel(@NonNull Application application) {
        super(application);
    }

    //methods
    public LiveData<Project> getProject() {
        if (thisProject == null){
            //thisProject = getProject(); TO DO: build this method in the repository
        }
        return thisProject;
    }

    public void updateProject(Project project){ //TO DO: build this method in the repository
    }
}
