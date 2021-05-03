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
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.Project.Project;


public class ProjectListViewModel extends AndroidViewModel {

    private static final String TAG = "ProjectListViewModel";
    MutableLiveData<ArrayList<Project>> projects;

    public ProjectListViewModel(@NonNull Application application) {
        super(application);

        projects = getAllProjects();
    }

    public MutableLiveData<ArrayList<Project>> getAllProjects() {
        if(projects==null){
            loadData();
        }
        return projects;
    }

    private void loadData() { //TO DO: build this method in the repository
    }

    public void addProject(Project project){ //TO DO: build this method in the repository
    }

    public void getProject(int id){ //TO DO: build this method in the repository
    }
}
