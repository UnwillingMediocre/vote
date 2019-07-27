import com.stodger.dao.VoteSubjectDao;
import com.stodger.dao.impl.VoteSubjectDaoImpl;
import com.stodger.vo.SubjectAndOptionVo;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-07-01 10:45
 */
public class VoteSubjectDaoTest {

    private VoteSubjectDao voteSubjectDao;
    @Before
    public void before(){
        voteSubjectDao = new VoteSubjectDaoImpl();
    }
    @Test
    public void saveSubjectAndOptionTest(){
        String [] strs = new String[]{"A123","B234","C345"};
        System.out.println(voteSubjectDao.saveSubjectAndOption("AAA","123",strs,1,new Date(),new Date(),0,1,"voteid_23fde164"));
    }

    @Test
    public void findSubjectAndOptionVoBySubjectIdTest(){
        SubjectAndOptionVo subjectAndOptionVo = voteSubjectDao.findSubjectAndOptionVoBySubjectId(17);
        System.out.println(subjectAndOptionVo.toString() + subjectAndOptionVo.getVoteOptionList().size());
    }

    @Test
    public void findSubjectAndOptionAllTest(){
        List<SubjectAndOptionVo> subjectAndOptionVoList = voteSubjectDao.findSubjectAndOptionAll();
        for(SubjectAndOptionVo subjectAndOptionVo : subjectAndOptionVoList){
            System.out.println(subjectAndOptionVo);
        }
    }
    @Test
    public void findItemBySubjectIdTest(){
        System.out.println(voteSubjectDao.findItemBySubjectId(24));
    }
}
