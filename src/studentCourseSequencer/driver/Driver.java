package studentCourseSequencer.src.studentCourseSequencer.driver;

import studentCourseSequencer.src.studentCourseSequencer.state.Context;
import studentCourseSequencer.src.studentCourseSequencer.util.FileDisplayInterface;
import studentCourseSequencer.src.studentCourseSequencer.util.FileProcessor;
import studentCourseSequencer.src.studentCourseSequencer.util.Results;
import studentCourseSequencer.src.studentCourseSequencer.util.StudentCourseProcessor;

/**
 * @author placeholder
 *
 */
public class Driver {

	public static void main(String[] args) {

		/*
		 * As the build.xml specifies the arguments as argX, in ca
		 * se the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */

		if (args.length != 2 || args[0].equals("${arg0}") || args[1].equals("${arg1}")) {
			System.err.println("Error: Incorrect number of arguments. Program accepts 2 argumnets.");
			System.exit(0);
		}
		try {

			FileProcessor fpIn = new FileProcessor(args[0], "in");
			FileProcessor fpout = new FileProcessor(args[1], "out");
			FileDisplayInterface result = new Results();

			try {
				StudentCourseProcessor courseProcessor = new StudentCourseProcessor(new Context());
				courseProcessor.studentInputFileParser(fpIn, fpout, result);
				result.printResultToFile(fpout);

			}  catch (Exception e) {
				System.err.println("Exception Occured in Driver" + e);
				e.printStackTrace();
			} finally {
				fpIn.closeFile(fpIn);
				fpout.closeFile(fpout);
				System.exit(0);
			}
		} catch (Exception e) {
			System.err.println("Exception Occured in Driver" + e);
			e.printStackTrace();
			System.exit(0);
		}

	}
}
