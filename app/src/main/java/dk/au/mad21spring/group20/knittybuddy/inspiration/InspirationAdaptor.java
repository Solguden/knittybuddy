package dk.au.mad21spring.group20.knittybuddy.inspiration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.ComPattern;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.Pattern;

public class InspirationAdaptor extends RecyclerView.Adapter<InspirationAdaptor.InspirationViewHolder> {

    private List<ComPattern> comPatternList;

    public interface IPatternItemClickedListener{
        void onPatternClicked(int index);
    }

    private IPatternItemClickedListener listener;

    public InspirationAdaptor(IPatternItemClickedListener listener) { this.listener = listener; }

    public void updatePatternList(List<ComPattern> list){
        comPatternList = list;
        notifyDataSetChanged();
    }

    public class InspirationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView inspirationImage;
        TextView nameText;
        TextView descriptionText;

        public InspirationViewHolder(@NonNull View itemView, IPatternItemClickedListener patternItemClickedListener) {
            super(itemView);

            inspirationImage = itemView.findViewById(R.id.inspirationImageItem);
            nameText = itemView.findViewById(R.id.inspirationNameListItemtxt);
            descriptionText = itemView.findViewById(R.id.inspirationDescriptionListItemtxt);
            listener = patternItemClickedListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onPatternClicked(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public InspirationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inspiration_list_item, parent, false);
        InspirationViewHolder viewHolder = new InspirationViewHolder(view, listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InspirationViewHolder holder, int position) {
        holder.nameText.setText(comPatternList.get(position).name);
        holder.descriptionText.setText(comPatternList.get(position).pattern_author.name);

        Glide.with(holder.inspirationImage.getContext()).load(comPatternList.get(position).first_photo.square_url).into(holder.inspirationImage);
    }

    @Override
    public int getItemCount() {
        if (comPatternList != null)
            return comPatternList.size();
        return 0;
    }


}
