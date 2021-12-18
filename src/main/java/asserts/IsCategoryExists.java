package asserts;

import dto.Product;
import enums.CategoryType;
import lombok.NoArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

@NoArgsConstructor
public class IsCategoryExists extends TypeSafeMatcher<String> {
    public static Matcher<String> isCategoryExists() {
        return new IsCategoryExists();
    }

    @Override
    protected boolean matchesSafely(String actualCategoryTitle) {
        try {
            CategoryType.valueOf(actualCategoryTitle);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("No such category in our dictionary");
    }

}
