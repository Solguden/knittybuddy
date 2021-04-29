package dk.au.mad21spring.group20.knittybuddy.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;

//this class is inspired by lecture 3 "ListAndGrid" demo code
//this class handles the scrolling of projects in the project list
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    //internal interface
    public interface IProjectItemClickedListener{
        void onProjectClicked(int index);
    }

    //attributes
    private List<Project> projectList;
    private IProjectItemClickedListener listener;

    //constructor
    public ProjectAdapter(IProjectItemClickedListener listener) {this.listener = listener;}

    //methods
    public void updateProjectList(List<Project> list){
        projectList = list;
        notifyDataSetChanged();
    }

    //internal class
    public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }


    //methods from internal class "ProjectViewHolder"
    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
