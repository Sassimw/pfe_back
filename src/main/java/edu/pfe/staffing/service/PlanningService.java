package edu.pfe.staffing.service;

import edu.pfe.staffing.model.Assignment;
import edu.pfe.staffing.model.Planning;
import edu.pfe.staffing.model.Role;
import edu.pfe.staffing.repository.AssignmentRepository;
import edu.pfe.staffing.repository.PlanningRepository;
import edu.pfe.staffing.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlanningService {

    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    PlanningRepository planningRepository;

    @Autowired
    ProjectService projectService;

    public void createPlanning(Planning planning) {
        planningRepository.save(planning);
    }

    public void AddAssignmentToPlanning(Long idplanning, int month, int day, String projectId) {
        Assignment assignment = new Assignment();
        assignment.setMonth(month);
        assignment.setDay(day);
        assignment.setProject(projectService.getById(Long.parseLong(projectId)));
        assignment.setPlanning(planningRepository.getOne(idplanning));
        assignmentRepository.save(assignment);


    }

    public void deleteAssignmentById(long assignmentId) {
        this.assignmentRepository.deleteById(assignmentId);
    }

    public File savePlanningToFile(Planning planning) {

        String TMP_FILE_PATH = System.getProperty("user.dir") + File.separator + "tmp_files";
        File tmpFile = new File(TMP_FILE_PATH);
        if (!tmpFile.exists())
            tmpFile.mkdir();

        String CSV_FILE_PATH = System.getProperty("user.dir") + File.separator + "tmp_files" + File.separator + "planning.csv";
        File csvOutputFile = new File(CSV_FILE_PATH);

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            List<String[]> dataLines = new ArrayList<>();
            dataLines.add(new String[]{"Date","Project"});
            planning.getAssignments().forEach(
                    assignment -> {
                        String date = "2023-" + assignment.getMonth() + "-" + assignment.getDay();
                        dataLines.add(new String[]
                                {date, assignment.getProject().getName()});
                    }
            );

            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return csvOutputFile;
    }

    private String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(";"));
    }

    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
