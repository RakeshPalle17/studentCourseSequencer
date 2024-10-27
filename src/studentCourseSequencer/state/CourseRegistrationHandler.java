package studentCourseSequencer.src.studentCourseSequencer.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import studentCourseSequencer.src.studentCourseSequencer.util.FileDisplayInterface;

public class CourseRegistrationHandler {
    private List<String> completedCourses;
    private List<String> sameSemAllocation;
    private List<String> waitList;
    private int semesters;
    private boolean eligibleForGraduation;
    private int stateChanges;
    private int coursesInCurrentSemester;
    private static Map<String, List<String>> courseToGroupMap = new HashMap<>();
    CourseSequencerStateI StateOne;
    CourseSequencerStateI StateTwo;
    CourseSequencerStateI StateThree;
    CourseSequencerStateI StateFour;
    CourseSequencerStateI StateFive;

    public CourseRegistrationHandler() {
        this.completedCourses = new ArrayList<>();
        this.waitList = new ArrayList<>();
        this.sameSemAllocation = new ArrayList<>();
        this.coursesInCurrentSemester = 0;
        this.semesters = 0;
        this.eligibleForGraduation = false;
        this.stateChanges = 0;
        StateOne = new StateOne();
        StateTwo = new StateTwo();
        StateThree = new StateThree();
        StateFour = new StateFour();
        StateFive = new StateFive();

        setCourseToGroupMap();
    }

    public void printResults(String studentID, FileDisplayInterface results) {
        
        if (eligibleForGraduation) {
            results.addResult(studentID, completedCourses, semesters, stateChanges);
        } else {
            semesters = 0;
            results.addResult(studentID, completedCourses, semesters, stateChanges);

        }
    }

    public void attempt2RegisterCourse(String courseName, CourseSequencerStateI currentState, Context context) {
        String groupName = getGroupName(courseName);
        try {
            if (!eligibleForGraduation) {
                if (!groupName.equals("Group5")) {
                    if (isEligibleToRegister(courseName, currentState) &&
                            !checkSameSemester(courseName) && coursesInCurrentSemester < 3) {
                        registerCourse(courseName, currentState);
                    } else {
                        addToWaitList(courseName);
                    }
                } else {
                    registerCourse(courseName, currentState);
                }

                updateState(currentState, context);
                checkForGraduation(currentState);
                updateSemester(currentState);
                processWaitList(currentState, context);
            }
        } catch (Exception e) {
            System.err.println("Exception Occured in CourseRegistrationHandler" + e);
            e.printStackTrace();
            System.exit(0);
        }

    }

    private void processWaitList(CourseSequencerStateI currentState, Context context) {
        try {
            List<String> coursesToRemove = new ArrayList<>();
            for (String courseName : waitList) {
                String groupName = getGroupName(courseName);
                if (!eligibleForGraduation) {
                    if (!groupName.equals("Group5")) {
                        if (isEligibleToRegister(courseName, currentState) && !checkSameSemester(courseName)
                                && coursesInCurrentSemester < 3) {
                            registerCourse(courseName, currentState);
                            updateState(currentState, context);
                            updateSemester(currentState);
                            checkForGraduation(currentState);
                            coursesToRemove.add(courseName);
                        }
                    } else {
                        registerCourse(courseName, currentState);
                        updateState(currentState, context);
                        updateSemester(currentState);
                        checkForGraduation(currentState);
                    }
                }
            }
            waitList.removeAll(coursesToRemove);
        } catch (Exception e) {
            System.err.println("Exception Occured in CourseRegistrationHandler" + e);
            e.printStackTrace();
            System.exit(0);
        }
    }

    private boolean isEligibleToRegister(String courseName, CourseSequencerStateI currentState) {
        return checkPrerequisites(courseName);
    }

    private boolean checkPrerequisites(String courseName) {
        try {
            List<String> coursePrerequisites = getPrerequisites(courseName);
            if (coursePrerequisites != null) {
                for (String prerequisite : coursePrerequisites) {
                    if (!isCourseTaken(prerequisite)) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Exception Occured in CourseRegistrationHandler" + e);
            e.printStackTrace();
            System.exit(0);
        }
        return true;
    }

    private List<String> getPrerequisites(String courseName) {
        List<String> preceedCourses = new ArrayList<>();
        try {
            if (courseToGroupMap != null) {
                String groupName = getGroupName(courseName);
                List<String> coursePrerequisites = courseToGroupMap.get(groupName);

                for (String value : coursePrerequisites) {
                    if (value.equals(courseName)) {
                        break;
                    }
                    preceedCourses.add(value);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception Occured in CourseRegistrationHandler" + e);
            e.printStackTrace();
            System.exit(0);
        }
        return preceedCourses;
    }

    private boolean isCourseTaken(String courseName) {
        return completedCourses.contains(courseName);
    }

    private void registerCourse(String courseName, CourseSequencerStateI currentState) {
        completedCourses.add(courseName);
        sameSemAllocation.add(courseName);
        coursesInCurrentSemester++;
    }

    private void updateState(CourseSequencerStateI currentState, Context context) {
        CourseSequencerStateI newState = determineState(currentState);
        if (currentState.getClass() != newState.getClass()) {
            context.setState(newState);
            stateChanges++;
        }
    }

    private CourseSequencerStateI determineState(CourseSequencerStateI currentState) {
        int coursesInGroup1 = completedCoursesInGroup("Group1");
        int coursesInGroup2 = completedCoursesInGroup("Group2");
        int coursesInGroup3 = completedCoursesInGroup("Group3");
        int coursesInGroup4 = completedCoursesInGroup("Group4");
        int coursesInGroup5 = completedCoursesInGroup("Group5");
        if (coursesInGroup1 > coursesInGroup2 && coursesInGroup1 > coursesInGroup3
                && coursesInGroup1 > coursesInGroup4 && coursesInGroup1 > coursesInGroup5) {
            return StateOne;
        } else if (coursesInGroup2 > coursesInGroup1 && coursesInGroup2 > coursesInGroup3
                && coursesInGroup2 > coursesInGroup4 && coursesInGroup2 > coursesInGroup5) {
            return StateTwo;
        } else if (coursesInGroup3 > coursesInGroup1 && coursesInGroup3 > coursesInGroup2
                && coursesInGroup3 > coursesInGroup4 && coursesInGroup3 > coursesInGroup5) {

            return StateThree;
        } else if (coursesInGroup4 > coursesInGroup1 && coursesInGroup4 > coursesInGroup2
                && coursesInGroup4 > coursesInGroup3 && coursesInGroup4 > coursesInGroup5) {

            return StateFour;
        } else if (coursesInGroup5 > coursesInGroup1 && coursesInGroup5 > coursesInGroup2
                && coursesInGroup5 > coursesInGroup3 && coursesInGroup5 > coursesInGroup4) {
            return StateFive;
        } else {
            return currentState;
        }
    }

    private boolean checkSameSemester(String courseName) {
        for (String prerequisite : getPrerequisites(courseName)) {
            if (sameSemAllocation.contains(prerequisite)) {
                return true;
            }
        }
        return false;
    }

    public void addToWaitList(String courseName) {
        waitList.add(courseName);
    }

    private void checkForGraduation(CourseSequencerStateI currentState) {
        if (completedCourses.size() >= 10
                && completedCoursesInGroup("Group1") >= 2
                && completedCoursesInGroup("Group2") >= 2
                && completedCoursesInGroup("Group3") >= 2
                && completedCoursesInGroup("Group4") >= 2
                && completedCoursesInGroup("Group5") >= 2) {
            eligibleForGraduation = true;
            if (coursesInCurrentSemester > 0) {
                semesters++;
                sameSemAllocation = new ArrayList<>();
                coursesInCurrentSemester = 0;
            }
        }
    }

    private int completedCoursesInGroup(String group) {
        int courseCount = 0;
        try {
            for (String course : completedCourses) {
                if (getGroupName(course).equals(group)) {
                    courseCount++;
                }
            }
        } catch (Exception e) {
            System.err.println("Exception Occured in CourseRegistrationHandler" + e);
            e.printStackTrace();
            System.exit(0);

        }
        return courseCount;
    }

    private void updateSemester(CourseSequencerStateI currentState) {
        if (coursesInCurrentSemester == 3) {
            semesters++;
            sameSemAllocation = new ArrayList<>();
            coursesInCurrentSemester = 0;
        }
    }

    private static String getGroupName(String course) {
        char courseName = course.charAt(0);
        if (courseName >= 'A' && courseName <= 'D') {
            return "Group1";
        } else if (courseName >= 'E' && courseName <= 'H') {
            return "Group2";
        } else if (courseName >= 'I' && courseName <= 'L') {
            return "Group3";
        } else if (courseName >= 'M' && courseName <= 'P') {
            return "Group4";
        } else if (courseName >= 'Q' && courseName <= 'Z') {
            return "Group5";
        } else {
            return "Unknown";
        }
    }

    private static void setCourseToGroupMap() {

        addValue(courseToGroupMap, "Group1", "A");
        addValue(courseToGroupMap, "Group1", "B");
        addValue(courseToGroupMap, "Group1", "C");
        addValue(courseToGroupMap, "Group1", "D");

        addValue(courseToGroupMap, "Group2", "E");
        addValue(courseToGroupMap, "Group2", "F");
        addValue(courseToGroupMap, "Group2", "G");
        addValue(courseToGroupMap, "Group2", "H");

        addValue(courseToGroupMap, "Group3", "I");
        addValue(courseToGroupMap, "Group3", "J");
        addValue(courseToGroupMap, "Group3", "K");
        addValue(courseToGroupMap, "Group3", "L");

        addValue(courseToGroupMap, "Group4", "M");
        addValue(courseToGroupMap, "Group4", "N");
        addValue(courseToGroupMap, "Group4", "O");
        addValue(courseToGroupMap, "Group4", "P");

        addValue(courseToGroupMap, "Group5", "Q");
        addValue(courseToGroupMap, "Group5", "R");
        addValue(courseToGroupMap, "Group5", "S");
        addValue(courseToGroupMap, "Group5", "T");
        addValue(courseToGroupMap, "Group5", "U");
        addValue(courseToGroupMap, "Group5", "V");
        addValue(courseToGroupMap, "Group5", "W");
        addValue(courseToGroupMap, "Group5", "X");
        addValue(courseToGroupMap, "Group5", "y");
        addValue(courseToGroupMap, "Group5", "z");

    }

    private static void addValue(Map<String, List<String>> map, String key, String value) {
        try {
            if (map != null) {
                if (map.containsKey(key)) {
                    map.get(key).add(value);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(value);
                    map.put(key, list);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception Occured in CourseRegistrationHandler" + e);
            e.printStackTrace();
            System.exit(0);
        }
    }
}
