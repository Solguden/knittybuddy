package dk.au.mad21spring.group20.knittybuddy.project;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.auth.User;

import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.project.fragment.ProjectDetailsFragment;
import dk.au.mad21spring.group20.knittybuddy.project.fragment.ProjectListFragment;

//This class is inspired by the "FragmentsArnieMovies" example from lecture L7
public class ProjectMainActivity extends AppCompatActivity implements IProjectSelector{

    //device type
    public enum DeviceType {MOBILE, TABLET}
    public enum UserView {LIST_VIEW, DETAIL_VIEW}
    private DeviceType deviceType;
    private UserView userView;

    //tags used for the fragments
    private static final String LIST_FRAG = "list_fragment";
    private static final String DETAIL_FRAG = "detail_fragment";

    //fragments
    private ProjectListFragment projectList;
    private ProjectDetailsFragment projectDetail;

    //containers used to insert fragments into
    private LinearLayout listContainer;
    private LinearLayout detailContainer;

    //list of project objects
    private List<Project> projects;
    private int selectedProjectIndex;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_project_main);

        Log.d("create main project", "project main created");
        //instantiation of containers
        listContainer = findViewById(R.id.list_container);
        detailContainer = findViewById(R.id.details_container);

        //determine device type
        if (!isTablet(this)){
            deviceType = DeviceType.MOBILE;
        } else {
            deviceType = DeviceType.TABLET;
        }

        //check if its the first time the activity is opened
        if (savedInstanceState == null) {
            selectedProjectIndex = 0;
            userView = UserView.LIST_VIEW;

            //instantiation of fragments
            projectList = new ProjectListFragment();
            projectDetail = new ProjectDetailsFragment();

            //setProjects
            //projectDetail.setProject(projects.get(selectedProjectIndex));

            //both fragments get added to the containers, but one is invisible
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_container, projectList, LIST_FRAG)
                    .add(R.id.details_container, projectDetail, DETAIL_FRAG)
                    .commit();

            //load projects
            getProjects();

        } else {
            //selectedProjectIndex = savedInstanceState.getInt("index");
            //userView = (UserView) savedInstanceState.getSerializable("user");

            //load projects
            getProjects();
            if (userView == null){
                userView = UserView.LIST_VIEW; //default
            }

            projectList = (ProjectListFragment)getSupportFragmentManager().findFragmentByTag(LIST_FRAG);
            if (projectList == null){
                projectList = new ProjectListFragment();
            }

            projectDetail = (ProjectDetailsFragment)getSupportFragmentManager().findFragmentByTag(DETAIL_FRAG);
            if (projectDetail == null){
                projectDetail = new ProjectDetailsFragment();
            }
        }

        updateFragmentViewState(userView);
    }

    //methods
    private void updateFragmentViewState(UserView view) {
        //this methods updates the view
        if (view == UserView.LIST_VIEW) {
            userView = UserView.LIST_VIEW;
            switchFragment(view);
        } if (view == UserView.DETAIL_VIEW) {
            userView = UserView.DETAIL_VIEW;
            switchFragment(view);
        } else {
            //ignore
        }
    }

    private boolean switchFragment(UserView view) {
        if (deviceType == DeviceType.MOBILE) {
            if (view == UserView.LIST_VIEW) {
                listContainer.setVisibility(View.VISIBLE);
                detailContainer.setVisibility(View.GONE);
                changeDetailContainerFragment(UserView.DETAIL_VIEW);
            } else if (view == UserView.DETAIL_VIEW) {
                listContainer.setVisibility(View.GONE);
                detailContainer.setVisibility(View.VISIBLE);
                changeDetailContainerFragment(view);
            }
        } else {
            if (view == UserView.LIST_VIEW) {
                changeDetailContainerFragment(UserView.DETAIL_VIEW);
                detailContainer.setVisibility(View.INVISIBLE);

            } else {
                changeDetailContainerFragment(view);
                detailContainer.setVisibility(View.VISIBLE);
            }
        }
        return true;
    }

    private void changeDetailContainerFragment(UserView view) {
        if (view == UserView.DETAIL_VIEW) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.animation_slide_in,R.anim.animation_slide_out)
                    .replace(R.id.details_container, projectDetail, DETAIL_FRAG)
                    .commit();
        }
    }

    //reference: https://stackoverflow.com/questions/5832368/tablet-or-phone-android
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    //method from implemented interface
    //this method will open an empty project in the detail view
    @Override
    public void addNewProject() {
        Project project = new Project();
        project.setUserId(userId);
        projectDetail.setProject(project);
        updateFragmentViewState(UserView.DETAIL_VIEW);
    }

    //method from implemented interface
    @Override
    public void onProjectSelected(Project project) {
        projectDetail.setProject(project);
        updateFragmentViewState(UserView.DETAIL_VIEW);
    }

    @Override
    public void makeDetailInvisible() {
        updateFragmentViewState(UserView.LIST_VIEW);
    }

    public void getProjects(){
        projects = projectList.getAllProjects();
    }
}
