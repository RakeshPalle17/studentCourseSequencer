Following are the commands and the instructions to run ANT on your project.
#### Note: build.xml is present in studentCourseSequencer/src folder.

-----------------------------------------------------------------------
## Instruction to clean:

####Command: ant -buildfile studentCourseSequencer/src/build.xml clean

Description: It cleans up all the .class files that were generated when you
compiled your code.

-----------------------------------------------------------------------
## Instruction to compile:

####Command: ant -buildfile studentCourseSequencer/src/build.xml all

Description: Compiles your code and generates .class files inside the BUILD folder.

-----------------------------------------------------------------------
## Instruction to run:

####Command:  

ant -buildfile studentCourseSequencer/src/build.xml run -Darg0=input.txt -Darg1=output.txt


Note: Arguments accept the absolute path of the files.

-----------------------------------------------------------------------
## Description:

Time Complexity && Algorithm used:-

Initially I'm using a map of string and list of courses to store groups along with there respective courses in each group. The Time complexity for using map to store the Groups as a Key and List of Courses as a Value. For iterating through each Course in a list would take o(n) where n is the number of Courses in the list. While processing the course registration I used Array list to keep the courses which are not registered because of the pre-requisites rule we have in our assignment and I'm processing that waitlist again after the end of the current semester as can not ssigned to a student in the same semester. So, a course, if put into the wait-list, cannot be removed until at least the current semester is over. Once the current semester is over and if the courses the wailist can be register without violating the pre-requisites I'm assigning it and removing from the waitlist.
After a course successfully registred I'm adding it to the registedCourse list. At the end when processing of all the courses or preferences completed calling a a printresults function to print them to the file.

Space complexity :- 

For storing groups and Courses I'm using Hashmap where Groups as Key and list of courses as values. The space complexity for storing them would be O(n*m) where n is the total number of keys and m is the total number of Course in each Courses list. For storing all the registered courses I'm using an array list which have the space complexity of O(n). where n is the size of the arraylist.



