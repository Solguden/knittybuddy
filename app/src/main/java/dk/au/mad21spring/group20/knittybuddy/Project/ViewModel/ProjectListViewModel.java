package dk.au.mad21spring.group20.knittybuddy.Project.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import dk.au.mad21spring.group20.knittybuddy.Project.Project;


public class ProjectListViewModel extends ViewModel {

    private static final String TAG = "ProjectListViewModel";
    MutableLiveData<ArrayList<Project>> projects;

    public LiveData<ArrayList<Project>> getProjects() {
        if(projects==null){
            loadData();
        }
        return projects;
    }

    private void loadData() {
        projects = new MutableLiveData<ArrayList<Project>>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("projects")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        ArrayList<Project> updatedProjects = new ArrayList<Project>();
                        if(snapshot!=null && !snapshot.isEmpty()){
                            for(DocumentSnapshot doc : snapshot.getDocuments()){
                                Project p = doc.toObject(Project.class);
                                if(p!=null) {
                                    updatedProjects.add(p);
                                }
                            }
                            projects.setValue(updatedProjects);
                        }
                    }
                });
    }

    public void addProject(Project project){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("projects").
                add(project)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Project added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding project", e);
                    }
                });
    }
}
