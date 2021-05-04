package dk.au.mad21spring.group20.knittybuddy.feed;

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

import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

public class FeedViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    LiveData<List<Feed>> feed;
    Repository repository;
    public FeedViewModel(){
        repository = Repository.getRepositoryInstance();
        feed = repository.getFeed();
    }

    public LiveData<List<Feed>> getFeed() { return feed; }

    public void generateFeedBasedOnFollowers(){

    }

    public void addRandomFeed(){
        Random random = new Random();
        String[] topics = new String[]{"A", "B", "C", "D","E"};
        String[] difficulties = new String[]{"1", "2", "3", "4","5"};
        String[] descriptions = new String[]{"Good","Bad","Fun","Ugly"};

        String topic = topics[random.nextInt(topics.length)];
        String difficulty = difficulties[random.nextInt(difficulties.length)];
        String description = descriptions[random.nextInt(descriptions.length)];
        int ownerId = random.nextInt(80) + 20;

        repository.addFeed(new Feed(topic,difficulty,description,ownerId));
    }

    public List<Feed> getByOwnerId(int ownerId){ return repository.getByOwnerId(ownerId); }

    public void deleteAll(){
//        FirebaseFirestore.getInstance()
//                .collection("feed")
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot snapshots) {
//                for(DocumentSnapshot doc : snapshots){
//                    doc.getReference().delete();
//                }
//            }
//        });
    }
}
