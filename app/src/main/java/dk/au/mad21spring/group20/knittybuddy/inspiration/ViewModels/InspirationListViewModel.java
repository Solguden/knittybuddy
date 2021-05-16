package dk.au.mad21spring.group20.knittybuddy.inspiration.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.ComPattern;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.Pattern;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

public class InspirationListViewModel extends AndroidViewModel {

    private static final String TAG = "InspirationListViewModel";
    Repository repository;
    Context context;
    LiveData<List<ComPattern>> comPatterns = new MutableLiveData<>();

    public InspirationListViewModel(@NonNull Application app)
    {
        super(app);
        repository = Repository.getRepositoryInstance();
        context = app.getApplicationContext();
        comPatterns = repository.getPatterns();
    }

    public LiveData<List<ComPattern>> getPatterns() { return comPatterns; }

    public void getRepoPatterns(String input) { repository.getSearchPatterns(input, context); }


}
