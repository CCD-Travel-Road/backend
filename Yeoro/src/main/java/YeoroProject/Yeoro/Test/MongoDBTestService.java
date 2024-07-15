package YeoroProject.Yeoro.Test;

import com.mongodb.client.MongoClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDBTestService {

    private final MongoDBTestRepository mongoDBTestRepository;

    @Autowired
    public MongoDBTestService(MongoDBTestRepository mongoDBTestRepository) {
        this.mongoDBTestRepository = mongoDBTestRepository;
    }

    public List<MongoDBTestModel> getAllUser() {
        return mongoDBTestRepository.findAll();
    }
}
