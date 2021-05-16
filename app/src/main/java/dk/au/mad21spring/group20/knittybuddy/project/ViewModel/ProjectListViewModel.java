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
    }

    //method
    public LiveData<List<Project>> getAllProjectsByUserId(String userId) { return repository.getAllProjectsByUserId(userId); }
}
