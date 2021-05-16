package dk.au.mad21spring.group20.knittybuddy.feed;

import android.net.IpSecTransform;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;

public class FeedAdaptor extends RecyclerView.Adapter<FeedAdaptor.ProjectViewHolder> {

    //Internal interface for callback on "star" function
    public interface IStarClicker
    {
        void onRemoveStar(int projectIndex);
        void onAddStar(int projectIndex);
    }

    //attribute
    private List<Project> projectList = new ArrayList<>();
    private IStarClicker listener;

    //constructor
    public FeedAdaptor(IStarClicker starClicker) { listener = starClicker; }

    //methods from extended class
    @NonNull
    @Override
    public FeedAdaptor.ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, parent, false);
        ProjectViewHolder viewHolder = new ProjectViewHolder(v, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdaptor.ProjectViewHolder holder, int position) {
        holder.projectImage.setImageResource(projectList.get(position).getImageId());
        holder.projectName.setText(projectList.get(position).getName());
        holder.userName.setText(projectList.get(position).getUserId()); //burde være username, men jeg kan ikke få det til at virke..
        holder.description.setText(projectList.get(position).getDescription());

        if(!TextUtils.isEmpty(projectList.get(position).getImageUrl())){
            Glide.with(holder.projectImage.getContext()).load(projectList.get(position).getImageUrl()).into(holder.projectImage);
        } else {
            holder.projectImage.setImageResource(R.drawable.knittybuddy_launcher_pink);
        }

        //set the visibility for the buttons
        for (String follower : projectList.get(position).getStaredBy()) {
            //check if the current user is following the project
            if (follower.equals(projectList.get(position).getUserId())){
                holder.removeStared.setVisibility(View.VISIBLE);
                holder.setStared.setVisibility(View.INVISIBLE);
            } else {
                holder.removeStared.setVisibility(View.INVISIBLE);
                holder.setStared.setVisibility(View.VISIBLE);
            }
        }
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
    public class ProjectViewHolder extends RecyclerView.ViewHolder {

        //widgets
        ImageView projectImage;
        TextView projectName;
        TextView userName;
        TextView description;
        ImageButton removeStared;
        ImageButton setStared;

        //this is used for the callback
        FeedAdaptor.IStarClicker StarClicker;

        public ProjectViewHolder(@NonNull View itemView, FeedAdaptor.IStarClicker star) {
            super(itemView);

            StarClicker = star;

            projectImage = itemView.findViewById(R.id.projectImageFeedItem);
            projectName = itemView.findViewById(R.id.projectNameFeedList);
            userName = itemView.findViewById(R.id.userNameFeedList);
            description = itemView.findViewById(R.id.projectDescriptionFeedList);

            //listeners for the two buttons
            setStared = itemView.findViewById(R.id.setStaredBtn);
            setStared.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeStared.setVisibility(View.VISIBLE);
                    setStared.setVisibility(View.INVISIBLE);
                    StarClicker.onAddStar(getAdapterPosition());
                }
            });

            removeStared = itemView.findViewById(R.id.removeStartedBtn);
            removeStared.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeStared.setVisibility(View.INVISIBLE);
                    setStared.setVisibility(View.VISIBLE);
                    StarClicker.onRemoveStar(getAdapterPosition());
                }
            });
        }
    }
}
