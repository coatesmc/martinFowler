package elasticsearch.service;

import elasticsearch.entity.User;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author mc
 * @Date 10/10/2019 10:43 AM
 * @Version 1.0
 **/
@Component
interface UserServiceI {
    void save(User user);

    void update(User user);

    User getById(String id);

    User getByName(String name);

    void delete(String id);
}
