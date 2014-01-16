package com.shaunmitchell.messagingapp.message;

import java.util.ArrayList;

import com.shaunmitchell.messagingapp.activity.MainActivity;

public class MessageThread
{
	private ArrayList<Message> m_messages = new ArrayList<Message>();
	private String m_threadTitle;
	private Boolean m_wasRead = false;

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

	public Boolean wasRead()
	{
		return m_wasRead;
	}

	public void setWasRead(Boolean wasRead)
	{
		this.m_wasRead = wasRead;
	}
}
