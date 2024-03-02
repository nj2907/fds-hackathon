// versions would be a 1D array with either '0' or '1' as its elements.
// '0' indicates the version is bug-free and '1' indicates the version is buggy.
// (Ex - For input [0, 0, 1, 1, 1], the bug was introduced in version 2 and the function should return 1)

public class Challenge {
    /**
     * The main entry point.
     * Don't change the code, besides the input to the function.
     */
    public static void main(String[] args) throws Exception {
        int bugFreeVersion = lastBugFreeVersion(new int[]{0, 0, 1, 1, 1});
        //int bugFreeVersion = lastBugFreeVersion(new int[]{0, 0, 0});
        //int bugFreeVersion = lastBugFreeVersion(new int[]{1, 0, 0});
        if (bugFreeVersion == -1) {
            System.out.println("No versions with bug!! Or test results have inconsistencies");
        } else {
            System.out.println("The bug was introduced in version : " + bugFreeVersion);
        }
    }

    public static int lastBugFreeVersion(int[] versions) {

        int lastBugFreeVersion = -1, lowerVersion = 0, higherVersion = versions.length - 1;

        while (lowerVersion < higherVersion) {
            int mid = lowerVersion + (higherVersion - lowerVersion) / 2;
            if (versions[mid] == 0) {
                lowerVersion = mid + 1;
            } else {
                higherVersion = mid;
            }
        }

        if (versions[lowerVersion] == 1) {
            lastBugFreeVersion = lowerVersion;
        }
        return lastBugFreeVersion;
    }
}
