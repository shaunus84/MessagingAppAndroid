package com.shaunmitchell.messagingapp.message;


public class Message
{
	private int m_id;
	private String m_messageText;
	
	public Message(int id, String messageText)
	{
		m_id = id;
		m_messageText = messageText;
	}

	public int get_id()
	{
		return m_id;
	}

	public String get_messageText()
	{
		return m_messageText;
	}
}
