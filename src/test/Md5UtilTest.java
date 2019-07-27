import com.stodger.util.Md5Util;
import org.junit.Test;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-29 09:15
 */
public class Md5UtilTest {

    @Test
    public void getMd5EnCode(){
        System.out.println(Md5Util.getMd5EnCode("jsu123456"));
    }
}
