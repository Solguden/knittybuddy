package dk.au.mad21spring.group20.knittybuddy.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dk.au.mad21spring.group20.knittybuddy.feed.Feed;

public class Repository {
    private ExecutorService executor;       //for asynch processing
    private MutableLiveData<List<Feed>> feedList;
    private FirebaseFirestore db;
    //RequestQueue queue; //For Volley
    Context context;
    private static Repository instance;
    private static final String TAG = "Repository";

    public Repository(/*Application app*/){
        //context = app.getApplicationContext(); // Kun nødvendig for room?
        executor = Executors.newSingleThreadExecutor();
        db = FirebaseFirestore.getInstance();
        initFeed();
    }

    public static Repository getRepositoryInstance(){
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }

    //Feed
    void initFeed(){
        feedList = new MutableLiveData<List<Feed>>();
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
                    feedList.setValue(updatedFeed);
                }
            }
        });
    }

    public void addFeed(Feed feed){
        db.collection("feed")
        .add(new Feed(feed.getTopic(),feed.getDifficilty(),feed.getDescription(),feed.getOwnerId()))
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                //Tilføj manuelt til liste her?
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
    }

    public LiveData<List<Feed>> getFeed(){return feedList;}

    public MutableLiveData<List<Feed>> getByOwnerId(int ownerId){ //bruge executer run her?
        feedList = new MutableLiveData<List<Feed>>();
        db.collection("feed")
        .whereEqualTo("ownerId", ownerId)
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
                    feedList.setValue(updatedFeed);
                }
            }
        });
        return feedList;
    }

    public ArrayList<Feed> getByOwnerIdAsynch(int ownerId){ //bruge executer run her?
        MutableLiveData<ArrayList<Feed>> list = new MutableLiveData<ArrayList<Feed>>();
        Future<ArrayList<Feed>> feed = executor.submit(new Callable<ArrayList<Feed>>() {
            @Override
            public ArrayList<Feed> call() {
                feedList = new MutableLiveData<List<Feed>>();
                db.collection("feed").whereEqualTo("ownerId", 84)
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
                                    feedList.setValue(updatedFeed);
                                }
                            }
                        });
                return list.getValue();
            }
        });

        try {
            Log.d(TAG, "Fetched feed by TEEEST: " + feed.get() );
            return feed.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
