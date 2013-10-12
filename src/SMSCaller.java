import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Command;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public class SMSCaller extends MIDlet implements CommandListener{
	Form homeForm;
	TextField phoneNumberTextField;
	TextField messageTextField;
	Command callCommand;
	Command smsCommand;
	
	public SMSCaller() {
		homeForm = new Form("SMS Caller");
		phoneNumberTextField = new TextField("Phone Number:", "", 10, TextField.NUMERIC);
		messageTextField = new TextField("Type your message:", "", 160, TextField.ANY);
		smsCommand = new Command("SMS", Command.OK, 0);
		callCommand = new Command("Call", Command.OK, 0);
		homeForm.append(phoneNumberTextField);
		homeForm.append(messageTextField);
		homeForm.addCommand(smsCommand);
		homeForm.addCommand(callCommand);
		homeForm.setCommandListener(this);
		// TODO Auto-generated constructor stub
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		Display.getDisplay(this).setCurrent(homeForm);
	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		if(c == callCommand){
			try{
				platformRequest("tel:" + phoneNumberTextField.getString());
			}
			catch(Exception e){
				
			}
		}
		else if(c == smsCommand){
			sendSMS(phoneNumberTextField.getString(), messageTextField.getString());
		}
	}
	
	public boolean sendSMS(String phone, String message){
		boolean result = true;
		MessageConnection myConnection;
		TextMessage myTextMessage;
		try{
			myConnection = (MessageConnection) Connector.open("sms://" + phone);
			myTextMessage = (TextMessage) myConnection.newMessage(MessageConnection.TEXT_MESSAGE);
			myTextMessage.setPayloadText(message);
			myConnection.send(myTextMessage);
			myConnection.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}

}
