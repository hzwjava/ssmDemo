package com.zhengs.test;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBTest {
	public static void main(String[] args){
		try {
			//连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
			//ServerAddress()两个参数分别为 服务器地址 和 端口
			ServerAddress serverAddress = new ServerAddress("localhost",27017);
			List<ServerAddress> addrs = new ArrayList<ServerAddress>();
			addrs.add(serverAddress);
			
			//通过连接认证获取MongoDB连接
			MongoClient mongoClient = new MongoClient(addrs);
			
			//连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("ssmDemo");
			System.out.println("Connect to database successfully"+mongoDatabase);
			
            //创建集合 参数为 “集合名称”  
//            mongoDatabase.createCollection("test1");  
//            System.out.println("Collection created successfully");
			
            //获取集合 参数为“集合名称”  
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("mylog");  
            System.out.println("Collection mycol selected successfully");  
            
            //插入文档
//            Document document = new Document("title", "MongoDB").append("description", "database").append("likes", 100).append("by", "Fly");  
//            List<Document> documents = new ArrayList<Document>();  
//            documents.add(document);  
//            mongoCollection.insertMany(documents);  
//            System.out.println("Document inserted successfully");  
            
            //查询文档
            System.out.println(mongoCollection.count());
//            FindIterable<Document> findIterable = mongoCollection.find();  
//            MongoCursor<Document> mongoCursor = findIterable.iterator();  
//            while(mongoCursor.hasNext()){  
//                System.out.println(mongoCursor.next());  
//            } 
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
}
