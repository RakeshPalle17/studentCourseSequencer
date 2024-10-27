package studentCourseSequencer.src.studentCourseSequencer.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileProcessor {

    BufferedReader br = null;
    BufferedWriter bw = null;
    File fileNames = null;

    /**
     * Constructor to initalize BufferedReader and BufferedWriter based on flag
     *
     * @param fileName    The name of the file to which we create buffer
     * @param readOrWrite Flag to indicate input or output buffer
     *
     */

    public FileProcessor(String fileName, String readOrWrite) {

        try {

            this.fileNames = new File(fileName);
            if (readOrWrite.equals("in")) {
                this.br = new BufferedReader(new FileReader(fileNames));
            }

            if (readOrWrite.equals("out")) {
                this.bw = new BufferedWriter(new FileWriter(fileNames));
            }

        } catch (FileNotFoundException e) {
            System.err.println("Exception in FileProcessor " + e);
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Exception in FileProcessor " + e);
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Read data from a specified file using a FileProcessor.
     *
     * @param fp FileProcessor Object used to read the buffer.
     * @return return String one line at a time from input file
     */

    public String readLine(FileProcessor fp) {
        String str = null;
        try {
            while ((str = fp.br.readLine()) != null) {
                return str;
            }
        } catch (IOException e) {
            System.err.println("Exception in FileProcessor " + e);
            e.printStackTrace();
            System.exit(0);
        }

        return str;
    }

    /**
     * Writes to a specified file using a FileProcessor.
     *
     * @param fp FileProcessor Object used to write the buffer.
     * @return return type void
     */

    public void writeLine(FileProcessor fp, String outString) {
        try {
            fp.bw.write(outString);
            fp.bw.flush();

        } catch (IOException e) {
            System.err.println("Exception in FileProcessor " + e);
            e.printStackTrace();
            System.exit(0);
        }

    }

    /**
     * check and close BufferedReader and writer using a FileProcessor.
     *
     * @param fp FileProcessor Object used to close the buffer.
     * @return return type void
     */

    public void closeFile(FileProcessor fp) {
        try {
            if (fp.br != null)
                fp.br.close();
            if (fp.bw != null)
                fp.bw.close();
        } catch (IOException e) {
            System.err.println("Exception in FileProcessor " + e);
            e.printStackTrace();
            System.exit(0);
        }
    }

}
