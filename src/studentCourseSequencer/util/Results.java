package studentCourseSequencer.src.studentCourseSequencer.util;

import java.util.List;

public class Results implements FileDisplayInterface, StdoutDisplayInterface {

    private StringBuilder output;

    public Results() {
        this.output = new StringBuilder();
    }

    public void addResult(String studentID, List<String> completedCourses, int semesters, int stateChanges) {
        try {
            output.append(studentID).append(": ");
            for (String course : completedCourses) {
                output.append(course).append(" ");
            }
            output.append("-- ").append(semesters).append(" ").append(stateChanges).append("\n");
            if(semesters == 0){
                output.append("Student deos not Graduated !!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public String getOutput() {
        return output.toString();
    }

    /**
     * Writes a list of results to a specified file using a FileProcessor.
     *
     * @param resultList The list of results to be written to the file.
     * @param fpOut      The FileProcessor object for writing to the file.
     * @return return type void
     */

    @Override
    public void printResultToFile(FileProcessor fpOut) {
        try {
            fpOut.writeLine(fpOut, getOutput());

        } catch (Exception e) {
            System.err.println("Exception Occured in printResultToFile" + e);
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Prints a list of results to the standard output.
     *
     * @param resultList The list of results to be printed to the console.
     * @return return type void
     */

    @Override
    public void printResultsToStdout(String result) {
        System.out.println(result);
    }

    /**
     * Writes an error message to a specified error log file using a FileProcessor.
     *
     * @param errorMsg   The error message to be written to the errorLog file.
     * @param fpOutError The FileProcessor object for writing to the errorLog file.
     * @return return type void
     */

    @Override
    public void printErrorLogsToFile(String errorMsg, FileProcessor fpOutError) {
        try {
            if (fpOutError != null)
                fpOutError.writeLine(fpOutError, errorMsg);

        } catch (Exception e) {
            System.err.println("Exception Occured in results" + e);
            e.printStackTrace();
            System.exit(0);
        }

    }

    /**
     * Prints an error message to the standard output.
     *
     * @param errorMsg The error message to be printed to the console.
     * @return return type void
     */

    @Override
    public void printErrorLogsToStdOut(String errorMsg) {
        System.out.println(errorMsg);
    }

}
