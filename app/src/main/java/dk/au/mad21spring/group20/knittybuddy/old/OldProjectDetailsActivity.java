package dk.au.mad21spring.group20.knittybuddy.old;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;

public class OldProjectDetailsActivity extends AppCompatActivity {

    //widgets
    EditText nameProjectEditTxt;
    EditText descriptionProjectDetailsEditTxt;
    Button goBackBtn;
    Button pdfBtn;
    Button publishBtn;
    Button saveBtn;
    Button deleteBtn;
    AlertDialog.Builder builder;

    //attributes
    private String projectId;
    private Project thisProject;

    //life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_project_detail);

        //instantiation of widgets
        nameProjectEditTxt = findViewById(R.id.headerProjectDetailEditTxt);
        descriptionProjectDetailsEditTxt = findViewById(R.id.descriptionProjectDetailEditTxt);
        goBackBtn = findViewById(R.id.goBackProjectDetailBtn);
        pdfBtn = findViewById(R.id.pdfDetailProjectBtn);
        publishBtn = findViewById(R.id.publishDetailProjectBtn);
        saveBtn = findViewById(R.id.saveProjectDetailBtn);
        deleteBtn = findViewById(R.id.deleteProjectDetailBtn);
        projectId = ""; //default value
        builder = new AlertDialog.Builder(this, R.style.alertBoxStyle);

        //get data from ProjectListActivity, who started this activity
        Intent data = getIntent();
        projectId = data.getStringExtra("id"); //TO DO: hardcode name and default

        //have some code here that gets the project object from the viewModel
        thisProject = new Project("", "Hej", "", 123, "", false, "");
        //button listener
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!thisProject.getDescription().equals(descriptionProjectDetailsEditTxt.getText().toString())){
                    if(!thisProject.getName().equals(nameProjectEditTxt.getText().toString())){
                        confirmBack();
                    }
                }
                else if (nameProjectEditTxt.getText().toString().equals("")){
                    makeToast("You must enter a project name");
                }
                else finish();
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

        updateUI(projectId);
    }

    //methods
    private void addSaveButton() {
        saveBtn.setVisibility(View.VISIBLE);
    }

    private void deleteProject(String id) {
        //kode som sletter et projekt
    }

    //reference: https://www.javatpoint.com/android-alert-dialog-example
    private void confirmBack() {
        builder.setMessage("Are you sure you want to go back without saving the changes?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
                        finish();
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
    private void confirmSaveChanges() {
        builder.setMessage("Do you want so save the changes?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        thisProject.setDescription(descriptionProjectDetailsEditTxt.getText().toString());
                        makeToast("Changes saved");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        makeToast("Changes not saved");
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void updateUI(String id){
        nameProjectEditTxt.setText(thisProject.getName());
        nameProjectEditTxt.setText(thisProject.getDescription());
    }

    public void makeToast(String txt){
        Context context = getApplicationContext();
        CharSequence text = txt;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}