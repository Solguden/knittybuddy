package dk.au.mad21spring.group20.knittybuddy.inspiration.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.ComPattern;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

public class InspirationDetailViewModel extends AndroidViewModel {

    private static final String TAG = "InspirationListViewModel";
    Repository repository;

    MutableLiveData<ComPattern> patternData = new MutableLiveData<>();

    public InspirationDetailViewModel(@NonNull Application application, int id) {
        super(application);
        repository = Repository.getRepositoryInstance();

        patternData.setValue(null); // NOT DONE

    }

    public LiveData<ComPattern> getPatternData() { return patternData; }

}
