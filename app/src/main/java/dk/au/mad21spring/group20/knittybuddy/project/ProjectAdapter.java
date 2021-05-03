package dk.au.mad21spring.group20.knittybuddy.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
    private List<Project> projectList = new ArrayList<Project>();
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

        //widget
        Button projectName;

        ProjectAdapter.IProjectItemClickedListener listener;

        public ProjectViewHolder(@NonNull View itemView, ProjectAdapter.IProjectItemClickedListener projectItemClickedListener) {
            super(itemView);

            listener = projectItemClickedListener;

            //instantiation of widget
            projectName = itemView.findViewById(R.id.btn_project);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onProjectClicked(getAdapterPosition());
        }
    }

    //methods from internal class "ProjectViewHolder"
    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item,parent,false);

        ProjectViewHolder vh = new ProjectViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        holder.projectName.setText(projectList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (projectList != null)
        {
            return projectList.size();
        }
        else return 0;
    }

}
