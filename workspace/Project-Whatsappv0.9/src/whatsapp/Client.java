package whatsapp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Client implements Runnable {

	private String title = "Chat Client";
	private JFrame newFrame;
	private JButton sendMessage;
	private JTextField messageBox;
	private JList contactListBox;
	private JTextArea textArea;
	private JTextField phoneNumberIn;
	private JFrame loginFrame;
	
	public void loginDisplay()
	{
		newFrame.setVisible(false);
		loginFrame = new JFrame(title);
		phoneNumberIn = new JTextField(30);
		JLabel label = new JLabel("Enter your phone number");
		JButton enter = new JButton("Enter chat server");
		enter.addActionListener(new enterButtonListener());
		JPanel loginPanel = new JPanel(new GridBagLayout());
		GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        loginPanel.add(label, preLeft);
        loginPanel.add(phoneNumberIn, preRight);
        loginFrame.add(BorderLayout.CENTER, loginPanel);
        loginFrame.add(BorderLayout.SOUTH, enter);
        loginFrame.setSize(500, 500);
        loginFrame.setVisible(true);
	}
	
	public void displayChatBox()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0,1));
		
		JPanel messagePanel = new JPanel();
		messageBox = new JTextField(60);
		messageBox.requestFocusInWindow();
		sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setFont(new Font("Verdana", Font.PLAIN, 12));
        textArea.setForeground(Color.WHITE);
        textArea.setLineWrap(true);
       
        while(contacts == null) {}
        contactListBox = new JList(contacts.toArray());

        mainPanel.add(new JScrollPane(textArea));
        mainPanel.add(new JScrollPane(contactListBox));
        contactListBox.addListSelectionListener(new selectContactsListener());

        

        messagePanel.add(messageBox);
        messagePanel.add(sendMessage);

        mainPanel.add(messagePanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        newFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.println("Window closed");
                stop();
            }
        });
        newFrame.setSize(600, 500);
        newFrame.setVisible(true);
	}
	
	class selectContactsListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent event)
		{
				if (event.getValueIsAdjusting())
				{
					int[] selected = contactListBox.getSelectedIndices();
					ArrayList<String> copy = targets;
					targets.clear();
					for(int i = 0; i < selected.length; i++)
					{
						targets.add((String)contactListBox.getModel().getElementAt(selected[i]));
					}
					ArrayList<String> t = new ArrayList<>();
					for(String s : targets)
						t.add(s);
//					Message get = new Message("-1", "-1", phoneNumber);
					try {
//						output.writeObject((Message)get);
						System.out.println(t);
//						output.writeUnshared(targets);
//						t.add(phoneNumber);
						output.writeObject(t);
//						output.reset();
						System.out.println(targets);
//						setCorrespondence = false;
//						while (!setCorrespondence) {
//							if (setCorrespondence)
//								break;
//						}
//						System.out.println("correspondence set");
						if (targets != copy)
							textArea.setText("");
//						System.out.println("History: " + currentPartners.history);
//						for(Message m : currentPartners.history)
//						{
//							textArea.append(m.toString());
//						}
					} catch (IOException e) 
					{
						System.out.println("Error getting correspondence messages. " + e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
	}
	
	class sendMessageButtonListener implements ActionListener 
	{
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing
            } else if (messageBox.getText().equals(".clear")) {
                textArea.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                textArea.append("<" + phoneNumber + ">:  " + messageBox.getText()
                        + "\n");
                try
    			{
    				String msg = messageBox.getText();
    				if (msg.equals(".exit"))
    					stop();
    				for(String target : targets)
    				{
    					Message toWrite = new Message(phoneNumber, target, msg);    					
    					output.writeObject(toWrite);
    				}
    			}
    			catch(Exception e)
    			{
    				System.out.println("Error occurred while sending message: " + e);
    				stop();
    			}
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }
	
	class enterButtonListener implements ActionListener 
	{
        
		public void actionPerformed(ActionEvent event)
		{
            phoneNumber = phoneNumberIn.getText();
           
        	try 
    		{
        		System.out.println("Sending through phone nr: " + phoneNumber);
    			output.writeObject((String)phoneNumber);
    			output.flush();
    		} catch (IOException e) 
    		{
    			System.out.println("Error sending identification: " + e);
    		}
            loginFrame.setVisible(false);
           // if (socket.isInputShutdown())
            {            	
            	 try {
                 	start();
                 } catch (IOException e) {
                 	e.printStackTrace();
                 }
            	displayChatBox();
            }
            //else
            {
            	//System.out.println("idk");
            //	stop();
            }
        }

    }
	
	private Socket socket = null;
	private String hostName = null;
	private int hostPort = 0;
	private Thread thread = null;
	private BufferedReader console = null;
	private ObjectOutputStream output = null;
	private ClientThread client = null;
	boolean closed = false;
	
	private String phoneNumber = "-1";
	public List<String> contacts = null;
	
	private ArrayList<String> targets;
	private Correspondence currentPartners = null;
	private boolean setCorrespondence = false;
	
	public void setCorrespondence(Correspondence c) 
	{ 
		this.currentPartners = c; 
		setCorrespondence = true;
		System.out.println("fun run");
	}
	
	public void start() throws IOException
	{
		
		
		System.out.println("start reading :)");
		client = new ClientThread(socket, this, phoneNumber);
	}
	
	boolean closed() {return this.closed;}
	
	public void stop()
	{
		if (!closed)
		{			
			try {
				output.writeObject(new Message(phoneNumber, "0", ".exit"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		closed = true;
		if (client != null)
		{			
			client.close();
			client.interrupt();
		}
		if (thread != null)
		{
			thread.interrupt();
		}
		try
		{
			if (console != null)
				console.close();
			if (output != null)
				output.close();
			if (socket != null)
				socket.close();
		}
		catch(Exception e)
		{
			System.out.println("Error closing resource: " + e);
		}
		System.exit(0);
	}
	
	public Client(String hostName, int hostPort)
	{
		System.out.println("Connecting to server...");
		newFrame = new JFrame(title);
		targets = new ArrayList<>();
		this.hostName = hostName;
		this.hostPort = hostPort;
		loginDisplay();
		if (thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
		
		
	}
	
	public void handleMessage(Message msg)
	{
		System.out.println(msg);
		textArea.append("<" + msg.getSender() + ">:  " + msg.getMessage()
        + "\n");
	}
	
	@Override
	public void run() 
	{
		System.out.println("Thread started");
		try
		{
			socket = new Socket(hostName, hostPort);
			System.out.println("Connected to server on " + socket);
		}
		catch(Exception e)
		{
			System.out.println("Exception occurred while conneting to server: " + e);
		}
        console = new BufferedReader(new InputStreamReader(System.in));
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void setContacts(List<String> c)
	{
		this.contacts = c;
	}

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	Client client = null;
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (args.length != 2)
        			System.out.println("Usage: java Client <hostName> <hostPort>");
        		else
        		{
        			client = new Client(args[0], Integer.parseInt(args[1]));
        		}
               // client.displayChatBox();
            }
        });
		
	}

}
