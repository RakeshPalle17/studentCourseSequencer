package studentCourseSequencer.src.studentCourseSequencer.state;

import studentCourseSequencer.src.studentCourseSequencer.util.FileDisplayInterface;

public class Context {
    private CourseSequencerStateI currentState;
    CourseRegistrationHandler registrationHandler;

    public Context() {
        currentState = new StateOne();
        registrationHandler = new CourseRegistrationHandler();
    }

    public void setState(CourseSequencerStateI stateIn) {
        currentState = stateIn;
    }

    public CourseSequencerStateI getState() {
        return currentState;
    }

    public void registerCourse(String course) {

        currentState.registerCourse(course, registrationHandler, this);

    }

    public void printResults(String studentID, FileDisplayInterface results) {
        registrationHandler.printResults(studentID, results);
    }

}
