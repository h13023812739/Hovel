package org.hyq.hovel.util;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBUtil {

    private MongoDatabase mongoDatabase;

    public MongoDBUtil(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    /**
     * 插入文档
     */
    public void insert(MongoCollection<Document> collection, Document document) {
        collection.insertOne(document);
    }

    /**
     * 删除文档
     */
    public void delete(MongoCollection<Document> collection, Document filter) {
        collection.deleteMany(filter);
    }

    /**
     * 更新文档
     */
    public void update(MongoCollection<Document> collection, Document filter, Document update) {
        collection.updateMany(filter, new Document("$set", update));
    }

    /**
     * 查询文档
     */
    public List<Document> query(MongoCollection<Document> collection, Document filter) {
        List<Document> results = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find(filter).iterator();
        while (cursor.hasNext()) {
            results.add(cursor.next());
        }
        return results;
    }

    /**
     * Union 查询
     */
    public List<Document> unionQuery(List<MongoCollection<Document>> collections, Document filter) {
        List<Document> results = new ArrayList<>();
        for (MongoCollection<Document> collection : collections) {
            results.addAll(query(collection, filter));
        }
        return results;
    }
}

