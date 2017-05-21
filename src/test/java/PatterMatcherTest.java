import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatterMatcherTest {

    @Value("${com.tenshun.tc.templates.regex}")
    public String regex;

    @Test
    public void test() {
        String pre = "Шаблон сообщения с постановкой ограниченной последовательности ";
        String post = " в середине текста";

        Pattern pattern = Pattern.compile(pre + regex + post);


        String s = regex + " У Вас через 3 дня заканчивается контракт";
        String text2 = "asd asd121vadas! У Вас через 3 дня заканчивается контракт";
        Pattern pattern1 = Pattern.compile(regex + " " + s);
        Pattern pattern2 = Pattern.compile(s);
        String testString = "Шаблон сообщения с постановкой ограниченной последовательности da*sd@}#>*$%^{&-SAD;2'''GA/////<c,a\\?/s123 в середине текста";
        Matcher matcher = pattern.matcher(testString);
        Matcher matcher1 = pattern2.matcher(s);


        Assert.assertEquals(true, matcher.find());
        Assert.assertEquals(true, matcher1.find());
    }
}
