package elasticsearch;

import com.coates.helloservice.HelloServiceApplication;
import elasticsearch.entity.User;
import elasticsearch.service.impl.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @ClassName RepositoryTest
 * @Description TODO
 * @Author mc
 * @Date 10/10/2019 10:46 AM
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HelloServiceApplication.class})
public class RepositoryTest {
    @Autowired
    UserService userService;


    @Test
    public void test(){
        System.out.println("userService："+ userService);
    }

    @Test
    public void add(){
        try{
            User user=new User("20190909","心如孤岛囚我终老","爱情", UUID.randomUUID().toString());
            userService.save(user);
            System.out.println("添加成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void get(){
        try{
            User user=userService.getById("20190909");
            System.out.println(user.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void update(){
        try{
            User user=new User("20190909","测试-update","爱情", UUID.randomUUID().toString());
            userService.update(user);
            System.out.println("修改成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getByName(){
        try{
            User user=userService.getByName("测试-update");
            System.out.println(user.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void delete(){
        try{
            userService.delete("20190909");
            System.out.println("删除成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
