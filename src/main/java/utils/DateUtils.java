package utils;
import org.springframework.stereotype.Component;
import java.util.Date;


@Component
public class DateUtils {
    public Date getDate() {
        return new Date();
    }
}
