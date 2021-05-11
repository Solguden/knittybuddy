package dk.au.mad21spring.group20.knittybuddy.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;

public class FeedAdaptor extends RecyclerView.Adapter<FeedAdaptor.ProjectViewHolder> {

    //attribute
    private List<Project> projectList = new ArrayList<>();

    //methods from extended class
    @NonNull
    @Override
    public FeedAdaptor.ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, parent, false);
        ProjectViewHolder viewHolder = new ProjectViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdaptor.ProjectViewHolder holder, int position) {
        holder.projectImage.setImageResource(projectList.get(position).getImageId());
        holder.projectName.setText(projectList.get(position).getName());
        holder.userName.setText(projectList.get(position).getUserId()); //det er ikke userID vi skal have fat i, men derimod userName!
        holder.description.setText(projectList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (projectList != null)
        {
            return projectList.size();
        }
        else return 0;
    }

    //method
    public void updateFeed(List<Project> list){
        projectList = list;
        notifyDataSetChanged();
    }

    //internal class
    public class ProjectViewHolder extends RecyclerView.ViewHolder{

        //widgets
        ImageView projectImage;
        TextView projectName;
        TextView userName;
        TextView description;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectImage = itemView.findViewById(R.id.projectImageFeedItem);
            projectName = itemView.findViewById(R.id.projectNameFeedList);
            userName = itemView.findViewById(R.id.userNameFeedList);
            description = itemView.findViewById(R.id.projectDescriptionFeedList);
        }
    }
}
