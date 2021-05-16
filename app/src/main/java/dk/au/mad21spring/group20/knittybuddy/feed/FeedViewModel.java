package dk.au.mad21spring.group20.knittybuddy.feed;

import android.app.Application;
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
import java.util.List;
import java.util.Random;

import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

public class FeedViewModel extends ViewModel {

    Repository repository;

    public FeedViewModel(Application application){
        repository = Repository.getRepositoryInstance();
    }

    public LiveData<List<String>> getUsersThisUserFollows(String thisUserId) { return repository.getUsersThisUserFollows(thisUserId); }
    //public List<String> getUsers(String thisUser) { return repository.getUsersThisUserFollowsAsync(thisUser); }
    //public List<String> getUsers(String thisUser) { return repository.getUsersThisUserFollows2(thisUser); }
    public LiveData<List<Project>> getPublishedProjects(String userId) { return repository.getPublishedProjectsByUserId(userId); }
    public LiveData<List<Project>> getAllPublishedProjects() { return repository.getAllPublishedProjects(); }

    public void removeStar(String user, Project project){
        repository.removeStarFromProject(user, project);
        Log.d("STAR", "remove star from project " + project.getName() + " for user " + user);
    }
    public void addStar(String user, Project project){ Log.d("STAR", "add star from project " + project.getName() + " for user " + user); }
}
