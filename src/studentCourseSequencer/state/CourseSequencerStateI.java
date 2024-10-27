package studentCourseSequencer.src.studentCourseSequencer.state;

public interface CourseSequencerStateI {

    public void registerCourse(String course, CourseRegistrationHandler registrationHandler, Context context);
}
