package com.shaunmitchell.messagingapp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.shaunmitchell.messagingapp.R;
import com.shaunmitchell.messagingapp.adapters.ThreadAdapter;
import com.shaunmitchell.messagingapp.message.Message;
import com.shaunmitchell.messagingapp.message.MessageThread;
import com.shaunmitchell.messagingapp.utility.CannedAnswers;

public class MainActivity extends Activity
{
	// the list of threads and messages within them
	public static ArrayList<MessageThread> threads = new ArrayList<MessageThread>();
	public final static String THREAD_NO = "com.shaunmitchell.messagingapp.THREAD_NO";

	// store the context to allow listeners to use it
	private Context m_mainContext;

	public final static int SENDER = 0;
	public final static int RECIPIENT = 1;
	public final static int TITLE = 2;
	
	private ListView m_threadsListView;
	private Button m_addThreadButton;
	private ThreadAdapter m_threadAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		m_mainContext = this;

		// create a randomised version of the app with canned thread titles and messages
		buildRandomThreads();
		
		// get the list view for this activity, this contains the thread titles to use
		m_threadsListView = (ListView) findViewById(R.id.threads_list_view);
		
		// create an adapter to handle data from the threads array and populate the list view
		m_threadAdapter = new ThreadAdapter(this,
				android.R.layout.simple_list_item_1, threads);

		m_threadsListView.setAdapter(m_threadAdapter);

		// listen for pressing on threads
		addListViewListener();

		// get the add thread button for this activity
		m_addThreadButton = (Button) findViewById(R.id.add_thread_button);
		
		// listen for adding new threads
		addNewThreadButtonListener();
	}

	private void addNewThreadButtonListener()
	{
		m_addThreadButton.setOnClickListener(new View.OnClickListener()
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

				alert.setPositiveButton("OK",
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton)
							{
								String newThreadTitle = input.getText()
										.toString().trim();

								threads.add(new MessageThread(newThreadTitle));

								m_threadAdapter.notifyDataSetChanged();

								Intent myIntent = new Intent(m_mainContext,
										ThreadActivity.class);

								myIntent.putExtra(THREAD_NO, threads.size() - 1);
								startActivity(myIntent);
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton)
							{
							}
						});

				alert.show();
			}
		});
	}

	private void addListViewListener()
	{
		// listen for a list item to be clicked and start a new activity
		m_threadsListView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				// start a new thread activity, this activity displays the message within the thread
				Intent myIntent = new Intent(view.getContext(),
						ThreadActivity.class);

				// pass through the index of this thread within the threads array so the activity's list view can be populated with it
				myIntent.putExtra(THREAD_NO, position);
				startActivity(myIntent);
			}
		});
	}
	
	private void buildRandomThreads()
	{
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
