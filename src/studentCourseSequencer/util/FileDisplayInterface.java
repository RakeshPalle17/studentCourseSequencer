package studentCourseSequencer.src.studentCourseSequencer.util;

import java.util.List;

public interface FileDisplayInterface {

    public void printResultToFile(FileProcessor fpOut);
    public void printErrorLogsToFile(String errorMsg, FileProcessor fpOutError);
    public void addResult(String studentID, List<String> completedCourses, int semesters, int stateChanges);

}
