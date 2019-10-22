package elasticsearch.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @ClassName User
 * @Description TODO
 * @Author mc
 * @Date 10/10/2019 10:41 AM
 * @Version 1.0
 **/
@Document(indexName = "content", type = "doc")
@Data
@ToString
public class User {
    /**
     * @Fields serialVersionUID:TODO
     * @Date 2019年8月17日 下午9:56:28
     */
    private static final long serialVersionUID = -4201309328223793614L;

    private String id;
    private String name;
    private String tag;
    private String description;

    public User(String id, String name, String tag, String description) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.description = description;
    }

    public User() {

    }
}

