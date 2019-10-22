package elasticsearch.repository;

import elasticsearch.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRepository
 * @Description TODO
 * @Author mc
 * @Date 10/10/2019 10:42 AM
 * @Version 1.0
 **/
@Component
@Repository
public interface UserRepository extends ElasticsearchRepository<User,String> {
    User findByName(String name);
}
