import com.tt.sqlhelper.annotations.Param;
import org.junit.Test;
import test.dao.UserMapper;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author: guoyitao
 * @date: 2020/11/24 22:19
 * @version: 1.0
 */
public class BaseTest {

    //需要javac编译参数，所以不搞了
    @Test
    public void testFanshe(){
        for (Method method : UserMapper.class.getMethods()) {
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName());
            }
        }
    }

    @Test
    public void testFanshe2(){
        for (Method method : UserMapper.class.getMethods()) {
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getAnnotation(Param.class).value());
            }
        }
    }
}
