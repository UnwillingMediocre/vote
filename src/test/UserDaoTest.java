
import com.stodger.dao.VoteUserDao;
import com.stodger.dao.impl.VoteUserDaoImpl;
import com.stodger.domain.VoteUser;
import com.stodger.util.IdUtil;
import com.stodger.util.Md5Util;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-29 15:20
 */
public class UserDaoTest {

    private VoteUserDao userDao = null;

    @Before
    public void before(){
        userDao = new VoteUserDaoImpl();
    }

    @Test
    public void findByUsernameUserIdTest() throws SQLException {
        System.out.println(userDao.findByUsernameUserId("admin"));
    }

    @Test
    public void saveVoteUserTest(){
        VoteUser voteUser = new VoteUser();
        voteUser.setUserId(IdUtil.getId());
        voteUser.setUserName("jsu1573");
        voteUser.setPassword(Md5Util.getMd5EnCode("jsu123456789"));
        voteUser.setStatus(1);
        voteUser.setPermission(1);
        voteUser.setVersion(1);
        voteUser.setRevise(1);
        System.out.println(userDao.saveVoteUser(voteUser));
    }

    @Test
    public void findByUsernameAndPasswordTest(){
        VoteUser voteUser = userDao.findByUsernameAndPassword("jsu1573",Md5Util.getMd5EnCode("jsu123456789"));
        System.out.println(voteUser);
    }
}
