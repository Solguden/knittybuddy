package dk.au.mad21spring.group20.knittybuddy.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dk.au.mad21spring.group20.knittybuddy.Constants;
import dk.au.mad21spring.group20.knittybuddy.feed.Feed;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.ComPattern;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.ComPatternResponse;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.Pattern;
import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.model.User;

public class Repository {
    private ExecutorService executor;       //for asynch processing
    private MutableLiveData<List<Feed>> feedList;
    private MutableLiveData<List<Project>> projectList;
    private MutableLiveData<List<Pattern>> patternList;
    private MutableLiveData<List<ComPattern>> comPatternList;
    private LiveData<List<ComPattern>> comPatterns;
    private FirebaseFirestore db;

    private RequestQueue queue;
    private static Repository instance;
    private static final String TAG = "Repository";

    public Repository(/*Application app*/){
        //context = app.getApplicationContext(); // Kun nødvendig for room?
        executor = Executors.newSingleThreadExecutor();
        db = FirebaseFirestore.getInstance();
        initFeed();
        comPatternList = new MutableLiveData<>();
//        comPatterns = (LiveData<List<ComPattern>>) comPatternList.getValue();

        Log.d(TAG, "comPatterns: " + comPatterns);
        //projectList = getAllProjectsFromDB();
    }

    public static Repository getRepositoryInstance(){
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }

    //Login
    public MutableLiveData<Boolean> createUser(User user){
        MutableLiveData<Boolean> created = new MutableLiveData<Boolean>(false);
        db.collection("users").add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        created.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        created.setValue(false);
                    }
                });
        return created;
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

    //project
    public LiveData<List<Project>> getAllProjects(){return projectList;}

    public MutableLiveData<List<Project>> getAllProjectsFromDB(){
        MutableLiveData<List<Project>> projects = new MutableLiveData<List<Project>>();
        db.collection(Constants.COLLECTION_PROJECT)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        ArrayList<Project> newProjectList = new ArrayList<>();
                        if(snapshot!=null && !snapshot.isEmpty()){
                            for(DocumentSnapshot doc : snapshot.getDocuments()){
                                Log.d("Id from database", "" + doc.toObject(Project.class).getId());
                                Log.d("Name from database", "" + doc.toObject(Project.class).getName());
                                Project p = doc.toObject(Project.class);
                                p.setId(doc.getId());
                                if(p!=null) {
                                    newProjectList.add(p);
                                }
                            }
                            projects.setValue(newProjectList);
                        }
                    }
                });
        return projects;
    }

    public MutableLiveData<List<Project>> getAllProjectsByUserId(String userId) {
        MutableLiveData<List<Project>> projects = new MutableLiveData<List<Project>>();
        db.collection(Constants.COLLECTION_PROJECT)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        ArrayList<Project> newProjectList = new ArrayList<>();
                        if(snapshot!=null && !snapshot.isEmpty()){
                            for(DocumentSnapshot doc : snapshot.getDocuments()){
                                Log.d("userId in db", "" + doc.toObject(Project.class).getUserId());
                                Log.d("project name in db", "" + doc.toObject(Project.class).getName());
                                Project p = doc.toObject(Project.class);
                                p.setId(doc.getId());
                                updateProjectId(p.getId());
                                if(p!=null) {
                                    if (p.getUserId().equals(userId)){
                                        newProjectList.add(p);
                                    }
                                }
                            }
                            projects.setValue(newProjectList);
                        }
                    }
                });
        return projects;
    }

    public MutableLiveData<List<Project>> getProjectById(String userId, String name){
        projectList = new MutableLiveData<List<Project>>();
        db.collection(Constants.COLLECTION_PROJECT)
                .whereEqualTo("userid", userId)
                .whereEqualTo("name", name)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        ArrayList<Project> updatedProject = new ArrayList<>();
                        if(snapshot!=null && !snapshot.isEmpty()){
                            for(DocumentSnapshot doc : snapshot.getDocuments()){
                                Project p = doc.toObject(Project.class);
                                p.setId(doc.getId());

                                if(p!=null) {
                                    updatedProject.add(p);
                                }
                            }
                            projectList.setValue(updatedProject);
                        }
                    }
                });
        return projectList;
    }

    public void addProject(Project project){
        db.collection(Constants.COLLECTION_PROJECT)
                .add(project)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Document added: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    //reference: https://www.youtube.com/watch?v=KxY5-dBSdgk&ab_channel=yoursTRULY
    public void updateProject(Project project){
        DocumentReference ref = db.collection(Constants.COLLECTION_PROJECT).document(project.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("description", project.getDescription());
        map.put("name", project.getName());
        map.put("published", project.getPublished());
        map.put("pdf", project.getPdf());

        ref.update(map);

        Log.d("Update", "Document updated: " + project.getId() + "name: " + project.getName());
    }

    public void updateUserId(Project project, String userId){
        DocumentReference ref = db.collection(Constants.COLLECTION_PROJECT).document(project.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        ref.update(map);

        Log.d("Update", "UserID updated for project: " + project.getId() + "userid: " + userId);
    }

    public void updateProjectId(String projectId){
        DocumentReference ref = db.collection(Constants.COLLECTION_PROJECT).document(projectId);

        Map<String, Object> map = new HashMap<>();
        map.put("id", projectId);
        ref.update(map);

        Log.d("Update", "Id updated for project: " + projectId);
    }

    public void updateImageUrl(String imageURL, String projectId){
        DocumentReference ref = db.collection(Constants.COLLECTION_PROJECT).document(projectId);

        Map<String, Object> map = new HashMap<>();
        map.put("imageUrl", imageURL);

        ref.update(map);
        Log.d(TAG, "Document updated: " + projectId + "imageUrl: " + imageURL);
    }

    public void deleteProject(Project project){
        db.collection(Constants.COLLECTION_PROJECT).document(project.getId())
                .delete();

        Log.d(TAG, "Document deleted: " + project.getId() + "name: " + project.getName());
    }


    //--------------------------------------------

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

//    public List<Pattern> searchPatternsAsync(String input){
//        Future<List<Pattern>> p = executor.submit(new Callable<List<Pattern>>() {
//            @Override
//            public List<Pattern> call() throws Exception {
//                return null;
//            }
//        })
//
//    }

    public LiveData<List<ComPattern>> getPatterns() { return comPatternList; }

//    public LiveData<ComPattern> getPatternData(int id) {
//        comPatternList
//    }

//    public void setPatterns(ArrayList<ComPattern> patterns) {
//        comPatternList.setValue(patterns);
//    }



    public void getSearchPatterns(String input, Context context) //
    {
//        Context context = app.getApplicationContext();
        String url = urlBase(input);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                sendRequest(url, context, new VolleyCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(String result) {
                        MutableLiveData<List<ComPattern>> parsed = parseJSON(result);
                        Log.d(TAG, "Parsed: " + parsed.getValue());
                        if (parsed.getValue() == null)
                        {
                            Toast.makeText(context, "No results found!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String result) {

                    }
                });
            }
        });

        Log.d(TAG, "Send request to get patterns from search term: " + input);
    }

    private String urlBase(String input)
    {
        String base = "https://api.ravelry.com/patterns/search.json?query=" + input;
        return base;
    }

    public interface VolleyCallback {
        void onSuccess(String result);
        void onError(String result);
    }

    private void sendRequest(String url, Context context, final VolleyCallback callback) // void
    {

        if (queue == null)
        {
            queue = Volley.newRequestQueue(context);
        }

        LiveData<List<ComPattern>> data = new MutableLiveData<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response);
//                    parseJSON(response);
                    callback.onSuccess(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "sendRequest failed!", error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Basic cmVhZC1iOGU5MzQwM2I1ZmRmMDBkNmQ0NTI0ZmRjMTNjOGU5MDpReERWQnhSbmR1YUhWNHBqSkE3Z1h0L0FOU0k2T2dQamdNNjVFRGkz");

                return params;
            }
        };
        queue.add(stringRequest);

        Log.d(TAG, "StringRequest: " + stringRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private MutableLiveData<List<ComPattern>> parseJSON(String json)
    {
        Gson gson = new GsonBuilder().create();
        ComPatternResponse response = gson.fromJson(json, ComPatternResponse.class);

        Log.d(TAG, "Respons: " + response);

//        ComPattern p0 = new ComPattern(response.getPattern().get(0).getId(), response.getPattern().get(0).getName(), response.getPattern().get(0).getAuthor(), response.getPattern().get(0).getPhoto());

        if (response.getPattern().size() != 0)
        {
//            ComPattern p1 = response.getPattern().get(0);
//            ComPattern p2 = response.getPattern().get(1);
//            ComPattern p3 = response.getPattern().get(2);
//            ComPattern p4 = response.getPattern().get(3);
//            ComPattern p5 = response.getPattern().get(4);

            List<ComPattern> lp = response.getPattern();


//            ComPattern pattern1 = new ComPattern(p1.getId(), p1.getName(), p1.getAuthor(), p1.getPhoto());
//            ComPattern pattern2 = new ComPattern(p2.getId(), p2.getName(), p2.getAuthor(), p2.getPhoto());
//            ComPattern pattern3 = new ComPattern(p3.getId(), p3.getName(), p3.getAuthor(), p3.getPhoto());
//            ComPattern pattern4 = new ComPattern(p4.getId(), p4.getName(), p4.getAuthor(), p4.getPhoto());
//            ComPattern pattern5 = new ComPattern(p5.getId(), p5.getName(), p5.getAuthor(), p5.getPhoto());
//
//            int id = p1.getId();

//            Log.d(TAG, "Pattern 1: " + p1);
//            Log.d(TAG, "ID: " + id);
//            Log.d(TAG, "Pattern Object 1 ID: " + pattern1.id);
//
//            ArrayList<ComPattern> lcp = new ArrayList<ComPattern>();
//
//            lcp.add(pattern1);
//            lcp.add(pattern2);
//            lcp.add(pattern3);
//            lcp.add(pattern4);
//            lcp.add(pattern5);

            comPatternList.setValue(lp);

            Log.d(TAG, "lcp + comPatternList: " + lp + " " + comPatternList.getValue());
        }


        return comPatternList;
    }
}
