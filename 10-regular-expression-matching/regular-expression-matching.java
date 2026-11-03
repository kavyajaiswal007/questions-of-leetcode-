class Solution {

    public boolean isMatch(String s, String p) {

        return checkMatching(s, p, 0, 0);
    }

    public boolean checkMatching(String s, String p, int i, int j) {

        if (j == p.length()) {
            if (i == s.length()) {
                return true;
            } else {
                return false;
            }
        }

        boolean currentMatch = false;

        if (i < s.length()) {

            if (p.charAt(j) == '.') {
                currentMatch = true;
            } 
            else if (p.charAt(j) == s.charAt(i)) {
                currentMatch = true;
            } 
            else {
                currentMatch = false;
            }
        }

        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {

            boolean zeroOccurrence =
                checkMatching(s, p, i, j + 2);

            boolean oneOrMoreOccurrence = false;

            if (currentMatch) {

                oneOrMoreOccurrence =
                    checkMatching(s, p, i + 1, j);
            }

            if (zeroOccurrence || oneOrMoreOccurrence) {
                return true;
            } else {
                return false;
            }
        }

        if (currentMatch) {

            return checkMatching(s, p, i + 1, j + 1);
        }

        return false;
    }
}