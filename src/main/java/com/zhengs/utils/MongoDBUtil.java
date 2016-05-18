package com.zhengs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * MongoDB工具类
 * @author zhengshan
 * @Date 2016-5-18
 */
public class MongoDBUtil {

	/**
	 * MongoDB工具类实例
	 */
    private static MongoDBUtil mongoDBUtil = null;

    /**
     * MongoClient的实例代表数据库连接池，是线程安全的，可以被多线程共享，客户端在多线程条件下仅维持一个实例即可
     * Mongo是非线程安全的，目前mongodb API中已经建议用MongoClient替代Mongo
     */
    private MongoClient mongoClient = null;
    
    /**
     * 获取实例
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @return MongoDB工具类实例
     */
    public synchronized static MongoDBUtil getInstance() {
        if (mongoDBUtil == null) {
            mongoDBUtil = new MongoDBUtil();
        }
        return mongoDBUtil;
    }

    /**
     * MongoDB构造方法
     */
    private MongoDBUtil() {
        if (mongoClient == null) {
        	String ip = PropertiesUtil.getValue("serverIp");
        	int port = Integer.parseInt(PropertiesUtil.getValue("serverPort"));
        	
            MongoClientOptions.Builder build = new MongoClientOptions.Builder();
            /*
             * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
             * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
             * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
             */
            build.maxWaitTime(1000 * 60 * 2);
            build.connectTimeout(1000 * 60 * 1);//与数据库建立连接的timeout设置为1分钟
            build.socketTimeout(0);// 套接字超时时间，0无限制
            build.connectionsPerHost(300);// 连接池设置为300个连接,默认为100
            build.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
            build.writeConcern(WriteConcern.ACKNOWLEDGED);
            MongoClientOptions myOptions = build.build();
            
			ServerAddress serverAddress = new ServerAddress(ip,port);
			List<ServerAddress> addrs = new ArrayList<ServerAddress>();
			addrs.add(serverAddress);
			
            try {
                //数据库连接实例
                mongoClient = new MongoClient(addrs, myOptions);
            } catch (MongoException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 判断指定数据是否存在
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @param filterMap
     *  @return
     */
    public boolean isExits(String dbName, String collectionName, Map<String, Object> filterMap) {
        if (filterMap != null) {
            FindIterable<Document> docs = mongoClient.getDatabase(dbName).getCollection(collectionName).find(new Document(filterMap));

            Document doc = docs.first();
            if (doc != null) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 插入数据
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @param insertMap
     *  @return
     */
    public boolean insert(String dbName, String collectionName, Map<String, Object> insertMap) {
        if (insertMap != null) {
            mongoClient.getDatabase(dbName).getCollection(collectionName).insertOne(new Document(insertMap));
            return true;
        }
        return false;
    }

    /**
     * 根据ID删除数据
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @param _id
     *  @return
     */
    public boolean deleteById(String dbName, String collectionName, String _id) {
        ObjectId objectId = new ObjectId(_id);
        Bson filter = Filters.eq("_id", objectId);

        DeleteResult deleteResult = getDatabase(dbName).getCollection(collectionName).deleteOne(filter);
        long deletedCount = deleteResult.getDeletedCount();

        return deletedCount > 0 ? true : false;
    }

    /**
     * 根据文档对象删除数据
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @param map
     *  @return
     */
    public boolean delete(String dbName, String collectionName, Map<String, Object> map) {
        if (map != null) {
            DeleteResult result = mongoClient.getDatabase(dbName).getCollection(collectionName).deleteMany(new Document(map));
            long deletedCount = result.getDeletedCount();
            return deletedCount > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据文档对象更新数据
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @param filterMap
     *  @param updateMap
     *  @return
     */
    public boolean updateOne(String dbName, String collectionName, Map<String, Object> filterMap, Map<String, Object> updateMap) {
        if (filterMap != null && filterMap.size() > 0 && updateMap != null) {
            UpdateResult result = mongoClient.getDatabase(dbName).getCollection(collectionName).updateOne(new Document(filterMap), new Document("$set", new Document(updateMap)));
            long modifiedCount = result.getModifiedCount();
            return modifiedCount > 0 ? true : false;
        }

        return false;
    }

    /**
     * 根据ID更新数据
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @param _id
     *  @param updateDoc
     *  @return
     */
    public boolean updateById(String dbName, String collectionName, String _id, Document updateDoc) {
        ObjectId objectId = new ObjectId(_id);
        Bson filter = Filters.eq("_id", objectId);

        UpdateResult result = getDatabase(dbName).getCollection(collectionName).updateOne(filter, new Document("$set", updateDoc));
        long modifiedCount = result.getModifiedCount();

        return modifiedCount > 0 ? true : false;
    }

    /**
     * 查询所有数据
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @return
     */
    public List<Document> find(String dbName, String collectionName) {
        final List<Document> resultList = new ArrayList<Document>();
        FindIterable<Document> docs = mongoClient.getDatabase(dbName).getCollection(collectionName).find();
        docs.forEach(new Block<Document>() {
            public void apply(Document document) {
                resultList.add(document);
            }
        });

        return resultList;
    }
    
    /**
     * 根据过滤条件查询数据
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @param filter
     *  @return
     */
    public List<Document> find(String dbName, String collectionName, Bson filter) {
        final List<Document> resultList = new ArrayList<Document>();
        if (filter != null) {
            FindIterable<Document> docs = mongoClient.getDatabase(dbName).getCollection(collectionName).find(filter);
            docs.forEach(new Block<Document>() {
                public void apply(Document document) {
                    resultList.add(document);
                }
            });
        }

        return resultList;
    }

    /**
     * 根据ID查询数据
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @param _id
     *  @return
     */
    public Document findById(String dbName, String collectionName, String _id) {
        ObjectId objectId = new ObjectId(_id);

        Document doc = getDatabase(dbName).getCollection(collectionName).find(Filters.eq("_id", objectId)).first();
        return doc;
    }

    /**
     * 分页查询
     *
     * @param dbName
     * @param collectionName
     * @param filter
     * @param pageIndex      从1开始
     * @param pageSize
     * @return
     */
    public List<Document> findByPage(String dbName, String collectionName, Bson filter, int pageIndex, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);

        final List<Document> resultList = new ArrayList<Document>();
        FindIterable<Document> docs = getDatabase(dbName).getCollection(collectionName).find(filter).sort(orderBy).skip((pageIndex - 1) * pageSize).limit(pageSize);
        docs.forEach(new Block<Document>() {
            public void apply(Document document) {
                resultList.add(document);
            }
        });

        return resultList;
    }

    /**
     * 获取MongoCollection
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @return
     */
    public MongoCollection<?> getCollection(String dbName, String collectionName) {
        return mongoClient.getDatabase(dbName).getCollection(collectionName);
    }

    /**
     * 获取MongoDatabase
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @return
     */
    public MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName);
    }

    /**
     * 获取指定collection的数据量大小
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @return
     */
    public long getCount(String dbName, String collectionName) {
        return getDatabase(dbName).getCollection(collectionName).count();
    }

    /**
     * 根据过滤条件获取指定collection的数据量大小
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName
     *  @param collectionName
     *  @param filter
     *  @return
     */
    public long getCount(String dbName, String collectionName, Bson filter) {
        return getDatabase(dbName).getCollection(collectionName).count(filter);
    }

    /**
     * 查询dbName下的所有表名
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName 数据库名称
     *  @return 表集合
     */
    public List<String> getAllCollections(String dbName) {
        MongoIterable<String> cols = getDatabase(dbName).listCollectionNames();
        List<String> _list = new ArrayList<String>();
        for (String s : cols) {
            _list.add(s);
        }
        return _list;
    }

    /**
     * 获取所有数据库名称列表
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @return 数据库名称集合
     */
    public MongoIterable<String> getAllDatabaseName() {
        MongoIterable<String> s = mongoClient.listDatabaseNames();
        return s;
    }

    /**
     * 删除一个数据库
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName 数据库名称
     */
    public void dropDatabase(String dbName) {
        getDatabase(dbName).drop();
    }

    /**
     * 删除collection
     *	@author zhengshan
     *	@Date 2016-5-18
     *  @param dbName 数据库名称
     *  @param collectionName 集合名称
     */
    public void dropCollection(String dbName, String collectionName) {
        getDatabase(dbName).getCollection(collectionName).drop();
    }

    /**
     * 关闭数据库连接池
     *	@author zhengshan
     *	@Date 2016-5-18
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}