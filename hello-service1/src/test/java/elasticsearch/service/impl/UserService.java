package elasticsearch.service.impl;

import elasticsearch.entity.User;
import elasticsearch.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UserServiceImp
 * @Description TODO
 * @Author mc
 * @Date 10/10/2019 10:44 AM
 * @Version 1.0
 **/
@Component
@Service("userService")
public class UserService {
    @Resource
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.delete(user);
        userRepository.save(user);
    }

    public User getById(String id) {
        return userRepository.findById(id).get();
    }

    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
