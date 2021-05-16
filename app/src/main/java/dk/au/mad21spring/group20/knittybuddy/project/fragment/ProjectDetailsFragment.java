package dk.au.mad21spring.group20.knittybuddy.project.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import dk.au.mad21spring.group20.knittybuddy.PDFActivity;
import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.project.IProjectSelector;
import dk.au.mad21spring.group20.knittybuddy.project.ViewModel.ProjectDetailViewModel;

import static android.app.Activity.RESULT_OK;

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
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    //app state
    private ProjectDetailViewModel detailVM;

    //attributes
    private Project thisProject;
    private String id;
    private boolean textChanged = false;

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
        saveBtn.setVisibility(View.INVISIBLE);
        deleteBtn = v.findViewById(R.id.deleteProjectDetailBtn);

        builder = new AlertDialog.Builder(getContext(), R.style.alertBoxStyle);

        thisProject = new Project();
        thisProject.setImageId(R.drawable.knittybuddy_launcher_pink);
        thisProject.setId("0");
        thisProject.setPdf("pdf");
        thisProject.setPublished(false);
        thisProject.setUserId("4");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        projectImageProjectDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        pdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDF();
                makeToast(getString(R.string.openPdf));
            }
        });

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thisProject.getPublished() == false){
                    publish();
                }
                else if (thisProject.getPublished() == true){
                    unPublish();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmSaveChanges();
                thisProject.setDescription(descriptionProjectDetailsEditTxt.getText().toString());
                thisProject.setName(nameProjectEditTxt.getText().toString());
                if (thisProject.getName().equals("")){
                    makeToast(getString(R.string.provideProjectName));
                } else {
                    if (thisProject.getId().equals("0")){
                        detailVM.addNewProject(thisProject);
                        makeToast(getString(R.string.projectCreated));
                    } else {
                        detailVM.updateProject(thisProject);
                        makeToast(getString(R.string.changesSaves));
                    }
                    saveBtn.setVisibility(View.INVISIBLE);
                }
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
                if (textChanged == true)
                {
                    addSaveButton();
                } else {
                    textChanged = true;
                }
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

    private void openPDF() {
        Intent pdfIntent = new Intent(getActivity(), PDFActivity.class);
        startActivity(pdfIntent);
//        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//
//        PdfDocument pdfDocument = new PdfDocument();
//        Paint paint = new Paint();
//        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(250, 350, 1).create();
//        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
//        Canvas canvas = myPage.getCanvas();
//
//        paint.setTextSize(15.5f);
//        paint.setColor(Color.rgb(0,50,250));
//
//        canvas.drawText(thisProject.getName(),20,20,paint);
//        paint.setTextSize(10f);
//        canvas.drawText(thisProject.getDescription(),20,40,paint);
//
//        pdfDocument.finishPage(myPage);
//        File file = new File(getContext().getExternalFilesDir("/"),thisProject.getId());
//        try {
//            pdfDocument.writeTo(new FileOutputStream(file));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        pdfDocument.close();
    }

    //selectPicture() and uploadPicture()  based on https://www.youtube.com/watch?v=CQ5qcJetYAI&ab_channel=BenO%27Brien
    private void selectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            projectImageProjectDetail.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture(){
        final String random = UUID.randomUUID().toString();
        StorageReference imageRef = storageReference.child("images/"+random);

        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> firbaseURI = taskSnapshot.getStorage().getDownloadUrl();
                        firbaseURI.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                thisProject.setImageUrl(url);
                                detailVM.updateImageProject(url,thisProject.getId());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public void onResume() { super.onResume(); }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        detailVM = new ViewModelProvider(getActivity()).get(ProjectDetailViewModel.class);
        detailVM.getProject(thisProject.getUserId(), thisProject.getName()).observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> project) {
                updateUI(project.get(0));
            }
        });
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
        thisProject = project;
        if (thisProject.getId().equals("0")){
            deleteBtn.setVisibility(View.INVISIBLE);
        } else {
            deleteBtn.setVisibility(View.VISIBLE);
        }
        updateUI(thisProject);
    }

    private void addSaveButton() {
        saveBtn.setVisibility(View.VISIBLE);
    }

    private void deleteProject(Project project) {
        detailVM.deleteProject(project);
    }

    private void goBack() {
        if (!thisProject.getDescription().equals(descriptionProjectDetailsEditTxt.getText().toString())){
//            if(!thisProject.getName().equals(nameProjectEditTxt.getText().toString())){
//                confirmBack();
//            }
            confirmBack();
        } else if (nameProjectEditTxt.getText().toString().equals("") && !descriptionProjectDetailsEditTxt.getText().toString().equals("")){
            makeToast(getString(R.string.provideProjectName));
        } else if (!thisProject.getName().equals(nameProjectEditTxt.getText().toString())){
            confirmBack();
        } else projectSelector.makeDetailInvisible(); //projectSelector.finish();
    }

    //reference: https://www.javatpoint.com/android-alert-dialog-example
    private void confirmBack() {
        builder.setMessage(R.string.backWithoutSave)
                .setCancelable(false)
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //projectSelector.finish();
                        projectSelector.makeDetailInvisible();
                    }
                })
                .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
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
        builder.setMessage(R.string.delete)
                .setCancelable(false)
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProject(thisProject);
                        makeToast(getString(R.string.projectDeleted));
                        projectSelector.makeDetailInvisible();
                    }
                })
                .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void publish() {
        thisProject.setPublished(true);
        detailVM.updateProject(thisProject);
        makeToast(getString(R.string.projectPublished));
        publishBtn.setText(R.string.unpublish);
    }

    private void unPublish() {
        thisProject.setPublished(false);
        detailVM.updateProject(thisProject);
        makeToast(getString(R.string.publishedRemoved));
        publishBtn.setText(R.string.publish);
    }

    private void updateUI(Project project){
        nameProjectEditTxt.setText(project.getName());
        descriptionProjectDetailsEditTxt.setText(project.getDescription());
        String imageUrl = project.getImageUrl();
        if(!TextUtils.isEmpty(imageUrl)){
            Glide.with(projectImageProjectDetail.getContext()).load(imageUrl).into(projectImageProjectDetail);
        }
        else{
            projectImageProjectDetail.setImageResource(R.drawable.knittybuddy_launcher_pink);
        }

        if (project.getPublished() == true){
            publishBtn.setText(R.string.unpublish);
        }
        if (project.getPublished() == false){
            publishBtn.setText(R.string.publish);
        }
    }

    public void makeToast(String txt){
        Context context = getContext();
        CharSequence text = txt;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
