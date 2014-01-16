package com.shaunmitchell.messagingapp.adapters;

import java.util.ArrayList;

import com.shaunmitchell.messagingapp.R;
import com.shaunmitchell.messagingapp.activity.MainActivity;
import com.shaunmitchell.messagingapp.message.Message;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<Message>
{
	private ArrayList<Message> messages;
	private Context m_mainContext;

	public MessageAdapter(Context context, int resource,
			ArrayList<Message> messages)
	{
		super(context, resource, messages);
		this.messages = messages;
		m_mainContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater) m_mainContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
