package dk.au.mad21spring.group20.knittybuddy.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dk.au.mad21spring.group20.knittybuddy.R;

public class ProjectDetailsActivity extends AppCompatActivity {

    //widgets
    TextView nameProjectTxt;
    EditText descriptionProjectDetailsEditTxt;
    Button goBackBtn;
    Button pdfBtn;
    Button publishBtn;

    //attributes
    private String projectId;
    private Project thisProject;

    //life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        //instantiation of widgets
        nameProjectTxt = findViewById(R.id.headerProjectDetailTxt);
        descriptionProjectDetailsEditTxt = findViewById(R.id.descriptionProjectDetailEditTxt);
        goBackBtn = findViewById(R.id.goBackProjectDetailBtn);
        pdfBtn = findViewById(R.id.pdfDetailProjectBtn);
        publishBtn = findViewById(R.id.publishDetailProjectBtn);
        projectId = ""; //default value

        //get data from ProjectListActivity, who started this activity
        Intent data = getIntent();
        projectId = data.getStringExtra("id"); //TO DO: hardcode name and default

        //have some code here that gets the project object from the viewModel
        thisProject = new Project();

        //button listener
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        descriptionProjectDetailsEditTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                thisProject.setDescription(descriptionProjectDetailsEditTxt.getText().toString());
            }
        });

        updateUI(projectId);
    }

    //methods
    private void updateUI(String id){
        if  (id != ""){
            //implement code which gets the project info from db (viewmodel)
        }
    }

    public void makeToast(String txt){
        Context context = getApplicationContext();
        CharSequence text = txt;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}