package com.shaunmitchell.messagingapp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.shaunmitchell.messagingapp.R;
import com.shaunmitchell.messagingapp.adapters.MessageAdapter;
import com.shaunmitchell.messagingapp.message.Message;

public class ThreadActivity extends Activity
{
	private EditText m_text;
	private int m_messageIndex;
	private ArrayList<Message> m_messages;
	private ListView m_messageListView;
	private MessageAdapter m_messageAdapter;
	private Button m_sendButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread);

		Intent intent = getIntent();

		m_messageIndex = intent.getIntExtra(MainActivity.THREAD_NO, 0);
		m_messages = MainActivity.threads.get(m_messageIndex).getMessages();

		m_messageListView = (ListView) findViewById(R.id.message_list_view);
		m_messageAdapter = new MessageAdapter(this,
				android.R.layout.simple_list_item_1, m_messages);

		m_messageListView.setAdapter(m_messageAdapter);

		m_text = (EditText) this.findViewById(R.id.text);

		m_sendButton = (Button) findViewById(R.id.send_button);

		addSendButtonListener();

		// move to latest message
		m_messageListView.setSelection(m_messageAdapter.getCount() - 1);
	}

	private void addSendButtonListener()
	{
		m_sendButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String newMessage = m_text.getText().toString().trim();
				if (newMessage.length() > 0)
				{
					m_text.setText("");
					m_messages
							.add(new Message(MainActivity.SENDER, newMessage));
					m_messageAdapter.notifyDataSetChanged();

					MainActivity.threads.get(m_messageIndex).setMessages(
							m_messages);

					m_messageListView.setSelection(m_messageAdapter.getCount() - 1);
					m_messageListView.requestFocus();

					InputMethodManager imm = (InputMethodManager) v
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thread, menu);
		return true;
	}
}
