package gitlet;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/** Class Commit for Gitlet.
 *  @author Ruize Xu
 */
public class Commit implements Serializable {

    /** The input message for the commit command. */
    private String _message;

    /** The time when the commit is generated. */
    private String _timestamp;

    /** The current branch for this commit. */
    private String _branch;

    /** The unique hash id for each commit. */
    private String _uid;

    /** An array of Hashes of parents. */
    private String[] _parentid;

    /**
     * A list of strings of hashes of Blobs that are being tracked.
     * Key is Blob's hashID, and value is the corresponding Blob object.
     */
    private HashMap<String, Blob> _blobs = new HashMap<>();

    /** return message. */
    public String getMessage() {
        return _message;
    }

    /** return timestamp. */
    public String getTimestamp() {
        return _timestamp;
    }

    /** return branch of this commit. */
    public String getBranchName() {
        return _branch;
    }

    /** return uid. */
    public String getUid() {
        return _uid;
    }

    /** return the prarentid of current commit. */
    public String[] getParentid() {
        return _parentid;
    }

    /** return the tracked blobs of current Commit. */
    public HashMap<String, Blob> getBlobs() {
        return _blobs;
    }

    /**
     * Return the first parentID.
     * @return String parentID
     */
    public String getParentID() {
        if (_parentid != null) {
            return _parentid[0];
        }
        return null;
    }

    /**
     * Return the String Array of all parentID.
     * @return String Array
     */
    public String[] getAllParentID() {
        return _parentid;
    }

    /** Default constructor. */
    public Commit() {

    }

    /**
     * Commit constructor called by init command.
     * @param message String the init message
     */
    public Commit(String message) {
        _message = message;
        _timestamp = "Thu Jan 1 00:00:00 1970 +0000";
        _uid = generateHash();
        _branch = "master";
        _blobs = null;
        _parentid = null;
    }

    /**
     * The Commit constructor.
     * @param message String the input message of commit command
     * @param parentid String array contains all parent hashID
     * @param branch String the current branch of the commit
     * @param blobs the tracked blobs of the commit
     */
    public Commit(String message, String[] parentid, String branch,
                  HashMap<String, Blob> blobs) {
        _message = message;
        _branch = branch;
        _parentid = parentid;
        _blobs = blobs;
        ZonedDateTime now = ZonedDateTime.now();
        _timestamp = now.format(DateTimeFormatter.ofPattern
                ("EEE MMM d HH:mm:ss yyyy xxxx", Locale.ENGLISH));
        _uid = generateHash();
    }

    /**
     * Hash generator using the method in Utils.sha1() method.
     * @return String hashID or we call it uid in Commit class
     */
    private String generateHash() {
        String blobToString;
        String parentToString = Arrays.toString(_parentid);
        if (_blobs == null) {
            blobToString = "";
        } else {
            blobToString = _blobs.toString();
        }
        String contentOfHash = _message + _timestamp + _branch
                + parentToString + blobToString;
        return Utils.sha1(contentOfHash);
    }
}
