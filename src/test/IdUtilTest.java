import com.stodger.util.IdUtil;
import org.junit.Test;

import java.util.UUID;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-29 09:55
 */
public class IdUtilTest {

    @Test
    public void getIdTest(){
        String id = IdUtil.getId();
        System.out.println(id);
        System.out.println(id.length());
    }
}
