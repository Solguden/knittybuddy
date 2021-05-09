package dk.au.mad21spring.group20.knittybuddy.project.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.project.IProjectSelector;
import dk.au.mad21spring.group20.knittybuddy.project.ProjectAdapter;
import dk.au.mad21spring.group20.knittybuddy.project.ViewModel.ProjectDetailViewModel;
import dk.au.mad21spring.group20.knittybuddy.project.ViewModel.ProjectListViewModel;

public class ProjectDetailsFragment extends Fragment {

    //widgets
    EditText nameProjectEditTxt;
    EditText descriptionProjectDetailsEditTxt;
    ImageView projectImageProjectDetail;
    Button goBackBtn;
    Button pdfBtn;
    Button publishBtn;
    Button saveBtn;
    Button deleteBtn;
    AlertDialog.Builder builder;

    //app state
    private ProjectDetailViewModel detailVM;

    //attributes
    private Project thisProject;
    private int projectIndex;

    private IProjectSelector projectSelector;

    //empty constructor
    public ProjectDetailsFragment () {

    }

    //life cycle methods
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project_detail, container, false);

        //instantiation of widgets
        nameProjectEditTxt = v.findViewById(R.id.headerProjectDetailEditTxt);
        descriptionProjectDetailsEditTxt = v.findViewById(R.id.descriptionProjectDetailEditTxt);
        projectImageProjectDetail = v.findViewById(R.id.projectImageDetail);
        goBackBtn = v.findViewById(R.id.goBackProjectDetailBtn);
        pdfBtn = v.findViewById(R.id.pdfDetailProjectBtn);
        publishBtn = v.findViewById(R.id.publishDetailProjectBtn);
        saveBtn = v.findViewById(R.id.saveProjectDetailBtn);
        deleteBtn = v.findViewById(R.id.deleteProjectDetailBtn);

        builder = new AlertDialog.Builder(getContext(), R.style.alertBoxStyle);
        thisProject = new Project();

        updateUI();

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        pdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToast("PDF is now open");
            }
        });

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thisProject.getPublished() == false){
                    makeToast("Your project is now published");
                    publishBtn.setText(R.string.unpublish);
                    thisProject.setPublished(true);
                }
                else if (thisProject.getPublished() == true){
                    makeToast("Your project is not longer published");
                    publishBtn.setText(R.string.publish);
                    thisProject.setPublished(false);
                }
            }
        });

        saveBtn.setVisibility(View.GONE);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmSaveChanges();
                thisProject.setDescription(descriptionProjectDetailsEditTxt.getText().toString());
                thisProject.setName(nameProjectEditTxt.getText().toString());
                makeToast("Changes saved");
                saveBtn.setVisibility(View.INVISIBLE);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });

        descriptionProjectDetailsEditTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        nameProjectEditTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return v;
    }

    @Override
    public void onResume() { super.onResume(); }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
    public void setProject(Project project){

    }

    //methods
    private void addSaveButton() {
        saveBtn.setVisibility(View.VISIBLE);
    }

    private void deleteProject(String id) {
        //kode som sletter et projekt
    }

    private void goBack() {
        if (!thisProject.getDescription().equals(descriptionProjectDetailsEditTxt.getText().toString())){
            if(!thisProject.getName().equals(nameProjectEditTxt.getText().toString())){
                confirmBack();
            }
        }
        else if (nameProjectEditTxt.getText().toString().equals("")){
            makeToast("You must enter a project name");
        }
        //else finish();
    }

    //reference: https://www.javatpoint.com/android-alert-dialog-example
    private void confirmBack() {
        builder.setMessage("Are you sure you want to go back without saving the changes?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //reference: https://www.javatpoint.com/android-alert-dialog-example
    private void confirmDelete() {
        builder.setMessage("Are you sure you want to delete this project")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProject(thisProject.getId());
                        makeToast("Project deleted");
                        //finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

//    //reference: https://www.javatpoint.com/android-alert-dialog-example
//    private void confirmSaveChanges() {
//        builder.setMessage("Do you want so save the changes?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        thisProject.setDescription(descriptionProjectDetailsEditTxt.getText().toString());
//                        makeToast("Changes saved");
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        makeToast("Changes not saved");
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

    private void updateUI(){
        nameProjectEditTxt.setText(thisProject.getName());
        nameProjectEditTxt.setText(thisProject.getDescription());
        projectImageProjectDetail.setImageResource(R.drawable.knittybuddy_launcher_pink);
    }

    public void makeToast(String txt){
        Context context = getContext();
        CharSequence text = txt;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
