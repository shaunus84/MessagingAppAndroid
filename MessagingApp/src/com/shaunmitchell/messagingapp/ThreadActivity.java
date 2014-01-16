package com.shaunmitchell.messagingapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ThreadActivity extends Activity
{
	private EditText m_text;
	private int m_messageIndex;
	private ArrayList<Message> m_messages;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread);

		Intent intent = getIntent();

		m_messageIndex = intent.getIntExtra(MainActivity.THREAD, 0);
		m_messages = MainActivity.threads.get(m_messageIndex).getMessages();

		final ListView listview = (ListView) findViewById(R.id.message_list_view);
		final MessageAdapter messageAdapter = new MessageAdapter(this,
				android.R.layout.simple_list_item_1, m_messages);

		listview.setAdapter(messageAdapter);

		m_text = (EditText) this.findViewById(R.id.text);

		final Button button = (Button) findViewById(R.id.send_button);
		button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String newMessage = m_text.getText().toString().trim();
				if (newMessage.length() > 0)
				{
					m_text.setText("");
					m_messages.add(new Message(MainActivity.SENDER, newMessage));
					messageAdapter.notifyDataSetChanged();

					MainActivity.threads.get(m_messageIndex).setMessages(
							m_messages);
					
					listview.setSelection(messageAdapter.getCount() - 1);
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

	private class MessageAdapter extends ArrayAdapter<Message>
	{
		private ArrayList<Message> messages;

		public MessageAdapter(Context context, int resource,
				ArrayList<Message> messages)
		{
			super(context, resource, messages);
			this.messages = messages;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if (v == null)
			{
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.message_line, null);
			}

			Message message = messages.get(position);

			TextView messageText = (TextView) v.findViewById(R.id.message_text);

			switch (message.get_id())
			{
				case MainActivity.SENDER:
					messageText
							.setBackgroundResource(R.drawable.rounded_shape_sender);
					messageText.setGravity(Gravity.LEFT);
					break;

				case MainActivity.RECIPIENT:
					messageText
							.setBackgroundResource(R.drawable.rounded_shape_recipient);
					messageText.setGravity(Gravity.RIGHT);
					break;

				case MainActivity.TITLE:
					messageText
							.setBackgroundResource(R.drawable.rounded_shape_title);
					messageText.setGravity(Gravity.CENTER);
					break;

				default:
					break;
			}
			messageText.setText(message.get_messageText());
			messageText.getBackground().setAlpha(80);

			return v;
		}
	}

}
