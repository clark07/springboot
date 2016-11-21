package com.cs.test.rabbit.listener;


public interface MessageProcessor {
	public void process(Object message);
}
