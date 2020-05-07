package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Ruize Xu
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        try {
            if (args.length == 0) {
                Utils.message("Please enter a command.");
                throw new GitletException();
            }
            ArrayList<String> input = new ArrayList<>(Arrays.asList(args));
            Repo repo = null;
            File tmpDir = new File(_cwdString + "/.gitlet");
            if (tmpDir.exists()) {
                File mr =  new File(".gitlet/repo");
                if (mr.exists()) {
                    repo = Utils.readObject(mr, Repo.class);
                }
            }
            if (input.get(0).equals("init")) {
                if (!tmpDir.exists()) {
                    repo = new Repo();
                    File mr = new File(".gitlet/repo");
                    Utils.writeObject(mr, repo);
                } else {
                    System.out.println("A Gitlet version-control "
                            + "system already exists in the "
                            + "current directory");
                    System.exit(0);
                }
            }
            if (validCommand(args[0])) {
                if (!tmpDir.exists()) {
                    Utils.message("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                if (input.get(0).equals("checkout")) {
                    if (input.size() == 2) {
                        repo.checkout(input.get(1));
                    } else {
                        input.remove(0);
                        repo.checkout(input);
                    }
                } else if (input.size() == 1) {
                    emptyOperandCommand(input, repo);
                } else if (input.size() == 2) {
                    oneOperandCommand(input, repo);
                }
                Utils.writeObject(new File(".gitlet/repo"), repo);
            } else {
                Utils.message(" No command with that name exists.");
                throw new GitletException();
            }
        } catch (GitletException e) {
            System.exit(0);
        }
    }

    /**
     * Call the method with no input in Class Repo.
     * @param in Arraylist Input Args
     * @param repo Repo the main operation class
     */
    private static void emptyOperandCommand(ArrayList<String> in, Repo repo) {
        String command = in.remove(0);
        switch (command) {
        case "log":
            repo.log();
            break;
        case "status":
            repo.status();
            break;
        case "global-log":
            repo.globalLog();
            break;
        default:
        }
    }

    /**
     * Call the method with one operand in Class Repo.
     * @param in Arraylist Input Args
     * @param repo Repo the main operation class
     */
    private static void oneOperandCommand(ArrayList<String> in, Repo repo) {
        String command = in.get(0);
        String operand = in.get(1);
        switch (command) {
        case "add":
            repo.add(operand);
            break;
        case "commit":
            repo.commit(operand);
            break;
        case "rm":
            repo.rm(operand);
            break;
        case "branch":
            repo.branch(operand);
            break;
        case "rm-branch":
            repo.rmbranch(operand);
            break;
        case "reset":
            repo.reset(operand);
            break;
        case "find":
            repo.find(operand);
            break;
        case "merge":
            repo.merge(operand);
            break;
        default:

        }
    }

    /** Takes in a string ARG word, will return whether or not
     * it is a valid command. */
    private static boolean validCommand(String arg) {
        for (String command: _vaildCommands) {
            if (arg.equals(command)) {
                return true;
            }
        }
        return false;
    }

    /** Array of possible valid commands. */
    private static String[] _vaildCommands = new String[] {"init", "add",
        "commit", "rm", "log", "global-log", "find", "status", "checkout",
        "branch", "rm-branch", "reset", "merge"};

    /**
     * The current working directory, File type.
     */
    private static File _cwd = new File(System.getProperty("user.dir"));

    /**
     * The current working directory, String type.
     */
    private static String _cwdString = System.getProperty("user.dir");

}
