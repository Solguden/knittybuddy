package dk.au.mad21spring.group20.knittybuddy.Project.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dk.au.mad21spring.group20.knittybuddy.project.Project;


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
