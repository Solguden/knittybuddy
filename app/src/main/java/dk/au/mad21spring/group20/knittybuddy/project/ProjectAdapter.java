package dk.au.mad21spring.group20.knittybuddy.project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;

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

        //widgets
        TextView projectName;
        ImageView projectImage;

        ProjectAdapter.IProjectItemClickedListener listener;

        public ProjectViewHolder(@NonNull View itemView, ProjectAdapter.IProjectItemClickedListener projectItemClickedListener) {
            super(itemView);

            listener = projectItemClickedListener;

            //instantiation of widget
            projectName = itemView.findViewById(R.id.projectNameListItemtxt);
            projectImage = itemView.findViewById(R.id.projectImageItem);

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
        //holder.projectImage.setImageResource(R.drawable.knittybuddy_launcher_pink);
        Glide.with(holder.projectImage.getContext()).load(projectList.get(position).getImageUrl()).into(holder.projectImage);
        Log.d("IMAGETEST", "onBindViewHolder: " + projectList.get(position).getImageUrl());
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
