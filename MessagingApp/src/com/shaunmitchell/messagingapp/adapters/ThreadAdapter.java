package com.shaunmitchell.messagingapp.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shaunmitchell.messagingapp.R;
import com.shaunmitchell.messagingapp.message.MessageThread;

public class ThreadAdapter extends ArrayAdapter<MessageThread>
{
	private ArrayList<MessageThread> threads;
	private Context m_mainContext;

	public ThreadAdapter(Context context, int resource,
			ArrayList<MessageThread> threads)
	{
		super(context, resource, threads);
		this.threads = threads;
		this.m_mainContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater) m_mainContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.thread_line, null);
		}

		MessageThread thread = threads.get(position);

		TextView messageText = (TextView) v.findViewById(R.id.message_text);

		messageText.setText(thread.getThreadTitle());

		return v;
	}
}
