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
import java.util.Random;

public class FeedViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    MutableLiveData<ArrayList<Feed>> feed;

    public LiveData<ArrayList<Feed>> getFeed() {
        if(feed==null){
            loadData();
        }
        return feed;
    }

    private void loadData() {
        feed = new MutableLiveData<ArrayList<Feed>>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("feed")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        ArrayList<Feed> updatedFeed = new ArrayList<>();
                        if(snapshot!=null && !snapshot.isEmpty()){
                            for(DocumentSnapshot doc : snapshot.getDocuments()){
                                Feed f = doc.toObject(Feed.class);
                                if(f!=null) {
                                    updatedFeed.add(f);
                                }
                            }
                            feed.setValue(updatedFeed);
                        }
                    }
                });
    }

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("feed").
                add(new Feed(topic,difficulty,description,ownerId))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void deleteAll(){
        FirebaseFirestore.getInstance()
                .collection("feed")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                for(DocumentSnapshot doc : snapshots){
                    doc.getReference().delete();
                }
            }
        });

    }
}
