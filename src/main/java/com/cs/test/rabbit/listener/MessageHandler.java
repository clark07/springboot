package com.cs.test.rabbit.listener;

public interface MessageHandler {
	void start();
	void stop();
	void shutdown();
}
