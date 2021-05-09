package dk.au.mad21spring.group20.knittybuddy.project;

import dk.au.mad21spring.group20.knittybuddy.model.Project;

public interface IProjectSelector {

    void addNewProject();
    void onProjectSelected(Project project);
    void finish();
}
