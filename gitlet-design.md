# Gitlet Design Document

**Author**: Ruize Xu

## Classes and Data Structures
### Commit
The class used to store the content of each commit command,
include commit message, timestamp, parent commit and so on.
#### Instance
1. String _message: Store the input message of the commit command.

2. String _timestamp: Store the current time when the new commit object was created. If current commit object does not have a parent commit, 
_timeStamp will be initialize as "00:00:00 UTC, Thursday, 1 January 1970".

3. HashMap\<String, Blob> _blobs: A list of strings of hashes of Blobs that are being tracked.

4. String _uid: The unique hash code

5. String[] _parentid: The array contains all the parent commit's hash code.

### Blob
The class represent the blob object which record each modification of corresponding file.
#### Instance 
1. String _name: Name of the modified file.

2. String _hashID: hashID of the blob object.

3. byte[] _contents:  Store the content which read from file using the Utils.readContents() method.

4. String _contentsAsString: Store the content which read from file using the Util.readContentsAsString() method.

5. String _timestamp: Store the time the blob object is formed.

### Repo
The class is the major part of the whole gitlet, including all command implementation, and the staging area, the current branch, the structure of the whole gitlet system.

#### Instance

1. final File cwd: The current working directory, File type.

2. final String cwdString: The current working directory, String type, mainly used for concat new directory according to cwd.

3. HashMap<String, String> _branches: The structure is used to store the head of each branch, key for branchName, and corresponding value is uid of the exact commit of this branch.

4. String _head: The head pointer that corresponds to the branch that actually will be pointing at the commit that we want.

5. HashMap<String, Blob> _stagingArea: Staging area used to store the blob object, key is file name, value is the Blob object, help us identify whether the file is changed.

6. ArrayList<String> _removedFiles: Removed files are like the opposite of the Staging Area, these are files that WERE tracked before, and now, for the next commit, they're not going to be added.

7. HashMap<Commit, Integer> cCommits: Global variable stores the data used for resurion in dfsForSplitCommit() method.

8. HashMap<Commit, Integer> gCommits: Global variable stores the data used for resurion in dfsForSplitCommit() method.

### Main
Driver class for Gitlet, the tiny stupid version-control system.

#### Instance

1. String[] _vaildCommands: Array of possible valid commands.

2. File _cwd: The current working directory, File type.

3. String _cwdString: The current working directory, String type.

## Algorithms
### Commit class
1. Commit(): Default constructor.

2. Commit(String message): Commit constructor called by init command.

3. Commit(String message, String[] parentid, String branch, HashMap<String, Blob> blobs): The general Commit constructor, called by the commit command.

4. private String generateHash(): Hash generator using the method in Utils.sha1() method.

   

### Blob class

5. Blob(String name): generate a Blob object according to the file name.

6. private String createHashId(): Create the unique hashID for the Blob object. Using the SHA1() algorithm.

7. public String getName(): Return the blob name.

8. public String getHashID(): Return the blob hashi]ID.

9. public byte[] getContents(): Return the Blob content as a byte array.

10. public String getContentsAsString(): Return the Blob content as String type.

### Repo class
1. Repo(): Constructor.

2. public void add(String filename): The add operation.

3. public void commit(String msg): The commit operation. Take in the message with commit command.

4. public void commit(String msg, String[] parents): The commit operation for merge.

5. public void log(): The log operation.

6. public void globalLog(): The global-log operation.

7. public void status(): The status operation.

8. public void stateDetect(String commitHash, ArrayList<File> modify, ArrayList<File> untrack, ArrayList<File> delete): StateDetect function is to detect state information. Help for status in Modifications Not Staged For Commit & Untracked Files, need fix.

9. public void checkout(ArrayList<String> args): The checkout opertion, takes in a Arraylist<String> ARGS.

10. public void checkout(String branchName): This is the third use case for checkout. It takes in a branchName.

11. public void rm(String fileName): The remove opertion. Take in the name of file you want to delete. Using the Utils.restrictedDelete() method.

12. public void branch(String branchName): Create new branch named branchName. But do not change current branch head to the newly created branch.

13. public void rmbranch(String branchName): The remove branch operation.

14. public void reset(String uid): The reset operation. Take in the commit uid you want tio reset to, and refresh all files in the working directory according to the tracked blobs in the Commit object.

15. public void find(String message): The find operation. Find the corresponding commit with the definite message. And print the Commit uid in Terminal.

16. public void merge(String branchName): The merge operation. 

17. private void mergeForSplit(String branchName,
HashMap<String, Blob> splitBlobs, HashMap<String, Blob> currentBlobs, HashMap<String, Blob> givenBlobs): Helper function for merge in first step, traverse the file in split commit, then merge it.

18. private void mergeForGiven(String branchName,
HashMap<String, Blob> splitBlobs, HashMap<String, Blob> currentBlobs, HashMap<String, Blob> givenBlobs): Helper function for merge in second step, traverse the file in split commit, then merge it.

19. private boolean isBlobInHashMap(String blobname, HashMap<String, Blob> blobs): Helper function for check whether the exact blob with the input blobName in the HashMap blobs, return true is the blob in the HashMap, otherwise false.

20. private void checkoutFile(String branchName, HashMap<String, Blob> blobs, String blobName): Calling the checkout method for merge the file between two branches.

21. private void mergeConflict(String branchName, String fileName): This method used to handle the conflict situation when merge two branches.

22. boolean isModified(String fileName, HashMap<String, Blob> h, HashMap<String, Blob> i): Helper Function for check whether the exact file has been changed from commit H to commit I. Return a boolean if the file with name F has been modified from commit H to commit I.

23.  private String splitPoint(String currentBranch, String givenBranch): Takes in two branch names, BRANCH1 and BRANCH2. Returns the SHA ID of the common ancestor commit.

24. private void dfsForSplitCommit(Commit head, int dist, String branch):  Helper function for find the nearest SplitPoint using the DFS.

25. private void checkForUntracked(File dir): This function takes in the present working directory PWD and will determine if there are untracked files that mean that this checkout or Merge operation can't continue.

26. public Commit uidToCommit(String uid): his method is used to find the corresponding Commit object according to the unique uid it contains. Return Commit object read from file.

27. private String shortToLong(String id): Takes in a shortened String ID and returns a String of the full length ID. Return The full size uid of the found Commit.

28. public String getHead(): Return the head commit's uid of current branch.

### Main class
1. public static void main(String... args): Usage: java gitlet.Main ARGS, where ARGS contains <COMMAND> <OPERAND> .... 

2. private static void emptyOperandCommand(ArrayList<String> in, Repo repo): Call the method with no input in Class Repo.

3. private static void oneOperandCommand(ArrayList<String> in, Repo repo): Call the method with one operand in Class Repo.

4. private static boolean validCommand(String arg): Takes in a string ARG word, will return whether or not it is a valid command.

## Persistence
In order to use the Gitlet system with separate commands like java gitlit.main \<command> \<operand> --optional, we should make sure the whole system is persistant. 

As we call the main method in Main class every execution. We can write the file into the directory ".gitlet/repo" each time the main function near to the end point. The repo file actually is the Repo object recordingt the stage area, the untracked file and all other properties the gitlet system should content. In this way, we can make sure the gitlet system remains consistent for all future calls.

Except for the repo file, there are two more directories inside the .gitlet directory. The commits folder used to store all the commits since the initial commit each time we call the commit function in the Repo class if there are new changes in our current working directory. And the staging folder used to store all the blob file, according to the hashid of Blob object. Each time we execute add \<filename> successfully, Repo will generate a blob if the content of the file has changed since last commit.


