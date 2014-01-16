package com.shaunmitchell.messagingapp;

import java.util.ArrayList;

public class MessageThread
{
	private ArrayList<Message> m_messages = new ArrayList<Message>();
	private String m_threadTitle;

	public MessageThread(String threadTitle)
	{
		m_threadTitle = threadTitle;
		m_messages.add(new Message(MainActivity.TITLE, m_threadTitle));
	}

	public ArrayList<Message> getMessages()
	{
		return m_messages;
	}

	public void setMessages(ArrayList<Message> messages)
	{
		this.m_messages = messages;
	}

	public String getThreadTitle()
	{
		return m_threadTitle;
	}
}
