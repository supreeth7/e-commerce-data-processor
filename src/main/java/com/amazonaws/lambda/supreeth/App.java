package com.amazonaws.lambda.supreeth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
public class App {

	static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static Object handleRequest(Request request, Context context) throws ResourceNotFoundException {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		Product product = null;
		switch(request.getHttpMethod()) {
		case "GET":
			product = mapper.load(Product.class, request.getId());
			if(product==null) {
				throw new ResourceNotFoundException("Product not found:" + request.getId());
			}
			return product;
		case "POST":
			product = request.getProduct();
			mapper.save(product);
			return product;
		default:
			break;
		}
		
		return null;
	}
}
