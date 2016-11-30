package com.cs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by admin on 2016/11/29.
 */
public class ClearQueue {


	    public static void main(String[] args) throws Exception{

			ConnectionFactory cf = new ConnectionFactory();
			//cf.setRequestedHeartbeat(20);
			//cf.setHost("localhost");

			Connection conn = cf.newConnection();
			Channel ch = conn.createChannel();
			ch.queueDelete("demo_q1");
			ch.queueDelete("demo_q2");
			ch.queueDelete("demo_q3");
			ch.queueDelete("demo_q4");
			ch.exchangeDelete("demo_e1");
			ch.exchangeDelete("demo_e2");
			ch.exchangeDelete("demo_e3");


			ch.close();
			conn.close();

		}
}
