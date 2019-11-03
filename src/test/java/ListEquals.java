import java.util.Collections;
import java.util.List;

public class ListEquals {

    /**
     * comparison of two list
     *
     * @param firstList
     * @param secondList
     * @return true when lists equals
     */
    public boolean listEquals(List<String> firstList, List<String> secondList) {
        if (firstList.size() == secondList.size()) {
            for (int i = 0; i < firstList.size(); i++) {
                if (!firstList.get(i).equals(secondList.get(i))) {
                    return false;
                }
            }

        }
        return false;
       /* if (firstList.size() == secondList.size()) {
            return firstList.containsAll(secondList);
        }
        return false;*/
    }
}
