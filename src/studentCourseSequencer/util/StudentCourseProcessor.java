package studentCourseSequencer.src.studentCourseSequencer.util;

import studentCourseSequencer.src.studentCourseSequencer.state.Context;

public class StudentCourseProcessor {
    private Context context;

    public StudentCourseProcessor(Context contextIn) {
        context = contextIn;
    }

    public void studentInputFileParser(FileProcessor fpIn1, FileProcessor fpOut, FileDisplayInterface results) {
        String oneLineInfo = null;
        try {
            if ((oneLineInfo = fpIn1.readLine(fpIn1)) != null && !oneLineInfo.isEmpty()) {
                String[] parts = oneLineInfo.split(": ");
                if (parts.length == 2) {
                    String studentID = parts[0];
                    String[] courseNames = parts[1].split(" ");

                    for (String course : courseNames) {
                        context.registerCourse(course);
                    }
                    context.printResults(studentID, results);
                }
            } else {
                System.err.println("Error: empty input file");
                System.exit(0);

            }
        } catch (Exception e) {
            System.err.println("Exception in StudentCourseProcessor Class" + e);
            e.printStackTrace();
            System.exit(0);
        }

    }
}
