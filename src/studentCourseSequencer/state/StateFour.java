package studentCourseSequencer.src.studentCourseSequencer.state;


public class StateFour implements CourseSequencerStateI{
    @Override
    public void registerCourse(String course, CourseRegistrationHandler registrationHandler, Context context) {
        registrationHandler.attempt2RegisterCourse(course, this, context);
    }

}
