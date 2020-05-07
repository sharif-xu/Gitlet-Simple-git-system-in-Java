package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

/** Class Blob for Gitlet.
 *  @author Ruize Xu
 */
public class Blob implements Serializable {
    /** Name of the modified file. */
    private String _name;

    /** HashID of the blob object. */
    private String _hashID;

    /** Store the content which read from file using the
     * Util.readContents() method. */
    private byte[] _contents;

    /** Store the content which read from file using the
     * Util.readContentsAsString() method. */
    private String _contentsAsString;

    /** Store the time the blob object is formed. */
    private String _timestamp;

    /**
     * Constructor.
     * @param name String the name of added file
     */
    public Blob(String name) {
        File file = new File(name);
        _name = name;
        _contents = Utils.readContents(file);
        _contentsAsString = Utils.readContentsAsString(file);
        ZonedDateTime now = java.time.ZonedDateTime.now();
        _timestamp = now.format(DateTimeFormatter.ofPattern
                ("EEE MMM d HH:mm:ss yyyy xxxx", Locale.ENGLISH));
        _hashID = createHashId();
    }

    /**
     * Create the unique hashID for the Blob object.
     * @return String the hashID generate by SHA1 algorithm
     */
    private String createHashId() {
        String contentToString = Arrays.toString(_contents);
        String insideBlob = _name + contentToString;
        return Utils.sha1(insideBlob);
    }

    /** Return the Blob name. */
    public String getName() {
        return _name;
    }

    /** Return the Blob hashID. */
    public String getHashID() {
        return _hashID;
    }

    /** Return the Blob content as a byte array. */
    public byte[] getContents() {
        return _contents;
    }

    /** Return the Blob content as String type. */
    public String getContentsAsString() {
        return _contentsAsString;
    }
}
