package dk.au.mad21spring.group20.knittybuddy.project.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.project.IProjectSelector;
import dk.au.mad21spring.group20.knittybuddy.project.ProjectAdapter;
import dk.au.mad21spring.group20.knittybuddy.project.ViewModel.ProjectListViewModel;

public class ProjectListFragment extends Fragment implements ProjectAdapter.IProjectItemClickedListener {

    //widgets
    Button btnAddNewProject;
    Button btnGoBack;
    RecyclerView rvProjectList;

    //app state
    private ProjectListViewModel listVM;

    //attributes
    private ProjectAdapter adapter;
    private List<Project> projectList;
    private Project thisProject;

    private IProjectSelector projectSelector;

    //empty constructor
    public ProjectListFragment () { }

    //life cycle methods
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project_list, container, false);

        Log.d("create", "create  view project list");
        //instantiation of widgets
        btnAddNewProject = v.findViewById(R.id.btn_project_addProject);
        btnGoBack = v.findViewById(R.id.btn_projectList_back);
        rvProjectList = v.findViewById(R.id.rv_project_list);
        projectList = new ArrayList<>();

        //button listeners
        btnAddNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProject();
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onResume() { super.onResume(); }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("create", "create activity (fragment) project list");
        //recyclerview
        adapter = new ProjectAdapter(this);
        rvProjectList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProjectList.setAdapter(adapter);

        //view models
        listVM = new ViewModelProvider(getActivity()).get(ProjectListViewModel.class);
        Log.d("project list", ""+ getActivity());
        Log.d("User", ""+FirebaseAuth.getInstance().getCurrentUser().getUid());

        listVM.getAllProjectsByUserId(FirebaseAuth.getInstance().getCurrentUser().getUid()).observe(getViewLifecycleOwner(), new Observer<List<Project>>() { //er i tvivl om "getViewLifecycleOwner()" - https://blog.usejournal.com/observe-livedata-from-viewmodel-in-fragment-fd7d14f9f5fb
            @Override
            public void onChanged(List<Project> projects) {
                Log.d("onChanged", "fragment project list");
                if (projects.size() == 0){
                    projectList = new ArrayList<Project>();
                }
                setProjects(projects);
                adapter.updateProjectList(projectList);
            }
        });

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            projectSelector = (IProjectSelector) activity;
        } catch (ClassCastException ex) {
            //Activity does not implement correct interface
            throw new ClassCastException(activity.toString() + " must implement IProjectSelector");
        }
    }

    //methods
    public void setProjects(List<Project> list){
        projectList = list;
    }

    public List<Project> getAllProjects(){
        return projectList;
    }

    //method from IProjectItemClickedListener
    @Override
    public void onProjectClicked(int index){
        thisProject = projectList.get(index);
        onProjectSelected(thisProject);
    }

    //method from IProjectSelector
    public void onProjectSelected(Project project){
        if(projectSelector!=null) {
            projectSelector.onProjectSelected(project);
        }
    }

    private void addProject() {
        if(projectSelector!=null) {
            projectSelector.addNewProject();
        }
    }

    //reference: https://stackoverflow.com/questions/5832368/tablet-or-phone-android
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
