package com.shaunmitchell.messagingapp;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity
{
	public static ArrayList<MessageThread> threads = new ArrayList<MessageThread>();
	public final static String THREAD = "com.shaunmitchell.messagingapp.THREAD";

	private Context m_mainContext;

	public final static int SENDER = 0;
	public final static int RECIPIENT = 1;
	public final static int TITLE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		m_mainContext = this;

		for (int i = 0; i < 25; i++)
		{
			MessageThread thread = new MessageThread(
					CannedAnswers.answers[(int) (Math.random() * ((CannedAnswers.answers.length)))]);

			int random = (int) (Math.random() * (25) + 1);
			int sender = 0;
			for (int j = 0; j < random; j++)
			{
				if (sender == 0)
				{
					sender = 1;
				}
				else if(sender == 1 )
				{
					sender = 0;
				}
				Message firstMessage = new Message(sender, CannedAnswers.answers[(int) (Math.random() * ((CannedAnswers.answers.length)))]);
				thread.getMessages().add(firstMessage);
			}

			threads.add(thread);
		}

		final ListView listview = (ListView) findViewById(R.id.threads_list_view);
		final ThreadAdapter threadAdapter = new ThreadAdapter(this,
				android.R.layout.simple_list_item_1, threads);

		listview.setAdapter(threadAdapter);

		listview.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Intent myIntent = new Intent(view.getContext(),
						ThreadActivity.class);

				myIntent.putExtra(THREAD, position);
				startActivity(myIntent);
			}
		});

		final Button button = (Button) findViewById(R.id.add_thread_button);
		button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(
						m_mainContext);

				alert.setTitle("New Thread");
				alert.setMessage("Type the title of your new thread");

				// Set an EditText view to get user input
				final EditText input = new EditText(m_mainContext);
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton)
							{

								// Do something with value!
								String newThreadTitle = input.getText()
										.toString().trim();

								threads.add(new MessageThread(newThreadTitle));

								threadAdapter.notifyDataSetChanged();

								Intent myIntent = new Intent(m_mainContext,
										ThreadActivity.class);

								myIntent.putExtra(THREAD, threads.size() - 1);
								startActivity(myIntent);
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton)
							{
								// Canceled.
							}
						});

				alert.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class ThreadAdapter extends ArrayAdapter<MessageThread>
	{
		private ArrayList<MessageThread> threads;

		public ThreadAdapter(Context context, int resource,
				ArrayList<MessageThread> threads)
		{
			super(context, resource, threads);
			this.threads = threads;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if (v == null)
			{
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.thread_line, null);
			}

			MessageThread thread = threads.get(position);

			TextView username = (TextView) v.findViewById(R.id.message_text);

			username.setText(thread.getThreadTitle());

			return v;
		}
	}

}
