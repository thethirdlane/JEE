package ttl.jms;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class JMSGooey {

	private JFrame frame;
	private JTextField conFactoryText;
	private JTextField queueText;
	private JPanel panel;
	private JButton btnGetOneMessage;
	private JButton btnReceiveAsynch;
	private JButton btnBrowse;
	private JButton btnConnect;
	private JButton btnStopAsync;
	private JLabel lblMessageSelector;
	private JTextField msgSelText;
	private JTextArea textPane;
	private JScrollPane scrollPane;

	private ConnectionFactory conFactory;
	private Connection connection;
	private Queue queue;
	private GooeyReceiver receiverUtil;
	private JMSBrowser jmsBrowser;
	private JButton btnClear;

	private ExecutorService executor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JMSGooey window = new JMSGooey();
					window.setupListeners();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setupListeners() {
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String btnText = btnConnect.getText();
					String cFactoryName = conFactoryText.getText();
					String queueName = queueText.getText();
					// Either Connect or Disconnect, based on the button Text
					switch (btnText) {
					case "Connect":
						// initConnectionTomEE(cFactoryName, queueName);
						//initConnectionGlassfish(cFactoryName, queueName);
						initConnectionWebLogic(cFactoryName, queueName);
						receiverUtil = new GooeyReceiver(connection, queue);
						receiverUtil.start();

						textPane.append("Connected to " + cFactoryName + ", dest: " + queueName + "\n");

						btnConnect.setText("Disconnect");
						break;
					case "Disconnect":
						receiverUtil.stop();
						textPane.append("Disconnectd from " + cFactoryName + ", dest: " + queueName + "\n");
						btnConnect.setText("Connect");
					}

				} catch (NamingException | JMSException e) {
					e.printStackTrace();
				}

			}
		});
		btnGetOneMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnGetOneMessage.setEnabled(false);
				// We want to do the receive in a background thread, so
				// we don't freeze up the GUI
				executor.execute(() -> {
					TextMessage message = (TextMessage) receiverUtil.receiveMessage();
					// But once we are done, we have to head back to the
					// AWT Thread to actually update the GUI
					SwingUtilities.invokeLater(() -> {
						try {
							appendMessage("Received One Message", message);
							btnGetOneMessage.setEnabled(true);
						} catch (JMSException e) {
							e.printStackTrace();
						}
						textPane.setCaretPosition(textPane.getText().length());
					});
				});
			}
		});

		btnReceiveAsynch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					receiverUtil.startAsync(new MyReceiver());
				} catch (JMSException e) {
					e.printStackTrace();
				}
				// Here, we use two buttons to toggle the state
				btnReceiveAsynch.setEnabled(false);
				btnStopAsync.setEnabled(true);
				textPane.append("JMSGooey: Registered Async Receiver" + "\n");
			}
		});

		btnStopAsync.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					receiverUtil.stopAsync();
					textPane.append("JMSGooey: Stopped Async Receiver" + "\n");
				} catch (JMSException e) {
					e.printStackTrace();
				}
				btnReceiveAsynch.setEnabled(true);
				btnStopAsync.setEnabled(false);
			}
		});

		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					Enumeration<Message> messages = JMSBrowser.browse(receiverUtil.getConnection(),
							receiverUtil.getQueue());

					int count = 0;
					while (messages.hasMoreElements()) {
						TextMessage message = (TextMessage) messages.nextElement();

						appendMessage("Browsed Message", message);
						count++;
					}
					textPane.append("Browsed " + count + " messages\n");
					textPane.setCaretPosition(textPane.getText().length());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				textPane.setText("");
			}
		});

	}

	class MyReceiver implements MessageListener {
		@Override
		public void onMessage(Message message) {
			SwingUtilities.invokeLater(() -> {
				try {
					appendMessage("Received Async Message", message);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				textPane.setCaretPosition(textPane.getText().length());
			});
		}

	}

	/**
	 * For Glassfish. NEEDS to have glassfish Client module in Classpath
	 * 
	 * @param cFactoryName
	 * @param destName
	 * @throws NamingException
	 * @throws JMSException
	 */
	public void initConnectionGlassfish(String cFactoryName, String destName) throws NamingException, JMSException {

		// For GlassFish
		InitialContext context = new InitialContext();
		conFactory = (ConnectionFactory) context.lookup(cFactoryName);

		queue = (Queue) context.lookup(destName);
		connection = conFactory.createConnection();
	}

	public void initConnectionWebLogic(String cFactoryName, String destName) throws NamingException, JMSException {

		// For WebLogic
		Hashtable<String, String> jndiProperties = new Hashtable<>();
		jndiProperties.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
		jndiProperties.put("java.naming.provider.url", "t3://localhost:7001");
		jndiProperties.put("java.naming.security.principal","weblogic");
		jndiProperties.put("java.naming.security.credentials","passw0rd");
		Context context = new InitialContext(jndiProperties);
		conFactory = (ConnectionFactory) context.lookup(cFactoryName);

		queue = (Queue) context.lookup(destName);
		connection = conFactory.createConnection();
	}

	/**
	 * For TomEE. NEEDS to have TomEE in Classpath Also need to start Application
	 * with this incantation: -DResource/javax.jms.ConnectionFactory=
	 * connectionfactory:org.apache.activemq
	 * .ActiveMQConnectionFactory:tcp://localhost:61616
	 * 
	 * @param cFactoryName
	 * @param destName
	 * @throws NamingException
	 * @throws JMSException
	 */
	public void initConnectionTomEE(String cFactoryName, String destName) throws NamingException, JMSException {

		Properties p = new Properties();
		p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", "http://127.0.0.1:8080/tomee/ejb");

		InitialContext context = new InitialContext(p);

		conFactory = (ConnectionFactory) context.lookup(cFactoryName);

		queue = (Queue) context.lookup(destName);
		connection = conFactory.createConnection();
	}

	private void appendMessage(String prelude, Message message) throws JMSException {
		// Big Assumption here. Should Fix at some point to be
		// more flexible
		TextMessage tm = (TextMessage) message;
		StringBuilder buffer = new StringBuilder();
		buffer.append(prelude).append(" ID: ").append(tm.getJMSMessageID());
		buffer.append(", msg = ").append(tm.getText()).append(", ");

		Enumeration<String> props = message.getPropertyNames();
		while (props.hasMoreElements()) {
			String prop = props.nextElement();
			buffer.append(prop).append(":").append(message.getStringProperty(prop)).append(" ");
		}
		textPane.append(buffer.append("\n").toString());
	}

	/**
	 * Create the application.
	 */
	public JMSGooey() {
		executor = Executors.newFixedThreadPool(2);
		initialize();

		int i = GridBagConstraints.REMAINDER;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.weighty = 1.0;
		gbc_panel_1.weightx = 1.0;
		gbc_panel_1.gridwidth = 0;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0 };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblConnectonFactory = new JLabel("Connecton Factory JNDI");
		lblConnectonFactory.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		GridBagConstraints gbc_lblConnectonFactory = new GridBagConstraints();
		gbc_lblConnectonFactory.anchor = GridBagConstraints.EAST;
		gbc_lblConnectonFactory.insets = new Insets(0, 0, 5, 5);
		gbc_lblConnectonFactory.gridx = 1;
		gbc_lblConnectonFactory.gridy = 0;
		panel_1.add(lblConnectonFactory, gbc_lblConnectonFactory);

		conFactoryText = new JTextField("jms/ConnectionFactory");
		conFactoryText.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_conFactoryText = new GridBagConstraints();
		gbc_conFactoryText.weightx = 1.0;
		gbc_conFactoryText.insets = new Insets(0, 0, 5, 0);
		gbc_conFactoryText.fill = GridBagConstraints.HORIZONTAL;
		gbc_conFactoryText.gridx = 2;
		gbc_conFactoryText.gridy = 0;
		panel_1.add(conFactoryText, gbc_conFactoryText);
		conFactoryText.setColumns(10);

		JLabel lblQueueJndi = new JLabel("Queue JNDI");
		lblQueueJndi.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		GridBagConstraints gbc_lblQueueJndi = new GridBagConstraints();
		gbc_lblQueueJndi.anchor = GridBagConstraints.EAST;
		gbc_lblQueueJndi.insets = new Insets(0, 0, 5, 5);
		gbc_lblQueueJndi.gridx = 1;
		gbc_lblQueueJndi.gridy = 1;
		panel_1.add(lblQueueJndi, gbc_lblQueueJndi);

		queueText = new JTextField("jms/Queue");
		queueText.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		queueText.setColumns(10);
		GridBagConstraints gbc_queueText = new GridBagConstraints();
		gbc_queueText.insets = new Insets(0, 0, 5, 0);
		gbc_queueText.fill = GridBagConstraints.HORIZONTAL;
		gbc_queueText.gridx = 2;
		gbc_queueText.gridy = 1;
		panel_1.add(queueText, gbc_queueText);

		lblMessageSelector = new JLabel("Message Selector");
		lblMessageSelector.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		GridBagConstraints gbc_lblMessageSelector = new GridBagConstraints();
		gbc_lblMessageSelector.anchor = GridBagConstraints.EAST;
		gbc_lblMessageSelector.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessageSelector.gridx = 1;
		gbc_lblMessageSelector.gridy = 2;
		panel_1.add(lblMessageSelector, gbc_lblMessageSelector);

		msgSelText = new JTextField();
		msgSelText.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		GridBagConstraints gbc_msgSelText = new GridBagConstraints();
		gbc_msgSelText.insets = new Insets(0, 0, 5, 0);
		gbc_msgSelText.fill = GridBagConstraints.HORIZONTAL;
		gbc_msgSelText.gridx = 2;
		gbc_msgSelText.gridy = 2;
		panel_1.add(msgSelText, gbc_msgSelText);
		msgSelText.setColumns(10);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 0;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		panel_1.add(panel, gbc_panel);

		btnConnect = new JButton("Connect");
		panel.add(btnConnect);

		btnGetOneMessage = new JButton("Get One Message");
		panel.add(btnGetOneMessage);

		btnReceiveAsynch = new JButton("Receive Asynch");
		panel.add(btnReceiveAsynch);

		btnStopAsync = new JButton("Stop Async");
		btnStopAsync.setEnabled(false);
		panel.add(btnStopAsync);

		btnBrowse = new JButton("Browse");
		panel.add(btnBrowse);

		btnClear = new JButton("Clear");
		panel.add(btnClear);

		textPane = new JTextArea();
		textPane.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		scrollPane = new JScrollPane(textPane);
		scrollPane.setPreferredSize(new Dimension(800, 500));
		GridBagConstraints gbc_scroll = new GridBagConstraints();
		gbc_scroll.weighty = 1.0;
		gbc_scroll.gridwidth = 0;
		gbc_scroll.fill = GridBagConstraints.BOTH;
		gbc_scroll.gridx = 0;
		gbc_scroll.gridy = 4;
		panel_1.add(scrollPane, gbc_scroll);

		frame.setSize(1200, 500);
	}

}
