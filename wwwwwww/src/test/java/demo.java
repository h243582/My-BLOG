import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class demo {

    public static void main(String[] args) {

        MongoClient client = new MongoClient("120.24.145.157",27017);

        MongoCredential credential = MongoCredential.createCredential("root", "admin",  "Jsu123456.".toCharArray());

        System.out.println("MongoDB connected!!");

        MongoDatabase spitdb = client.getDatabase("hyf_article");
        //得到集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");
        FindIterable<Document> documents = spit.find();

        for (Document d:documents) {
            System.out.println("内容:"+d.getString("content"));
            System.out.println("用户id"+d.getString("userid"));
            System.out.println("浏览量："+d.getInteger("visits"));

        }
        //关闭连接
        client.close();

    }
}