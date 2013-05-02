import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import de.javawi.jstun.attribute.ChangeRequest;
import de.javawi.jstun.attribute.ErrorCode;
import de.javawi.jstun.attribute.MappedAddress;
import de.javawi.jstun.attribute.MessageAttribute;
import de.javawi.jstun.attribute.MessageAttributeException;
import de.javawi.jstun.attribute.MessageAttributeParsingException;
import de.javawi.jstun.attribute.ResponseAddress;
import de.javawi.jstun.header.MessageHeader;
import de.javawi.jstun.header.MessageHeaderParsingException;
import de.javawi.jstun.util.UtilityException;

class LOGGER {
    public static void debug ( String text ){
        System.out.println( text );
    }
}

class Test {
    String stunServer = "192.168.233.131";
    int port = 9801;
    MappedAddress ma;
    int timeout = 300; //ms
    Timer timer;
    DatagramSocket initialSocket;

        

    public static void main( String[] args ){
        Test test = new Test();
        try {
            test.initialSocket = new DatagramSocket();
            test.initialSocket.connect(InetAddress.getByName(test.stunServer), test.port);
            test.initialSocket.setSoTimeout(test.timeout);
            
            test.bindingCommunicationInitialSocket(); 
        } catch ( Exception e ){ e.printStackTrace(); System.out.println("something went wrong"); }

        System.exit(0);
    }

    private boolean bindingCommunicationInitialSocket() throws UtilityException, IOException, MessageHeaderParsingException, MessageAttributeParsingException {
		MessageHeader sendMH = new MessageHeader(MessageHeader.MessageHeaderType.BindingRequest);
		sendMH.generateTransactionID();
		ChangeRequest changeRequest = new ChangeRequest();
		sendMH.addMessageAttribute(changeRequest);
		byte[] data = sendMH.getBytes();
		
		DatagramPacket send = new DatagramPacket(data, data.length, InetAddress.getByName(stunServer), port);
		initialSocket.send(send);
		LOGGER.debug("Binding Request sent.");
	
		MessageHeader receiveMH = new MessageHeader();
		while (!(receiveMH.equalTransactionID(sendMH))) {
			DatagramPacket receive = new DatagramPacket(new byte[200], 200);
			initialSocket.receive(receive);
			receiveMH = MessageHeader.parseHeader(receive.getData());
			receiveMH.parseAttributes(receive.getData());
		}
		ma = (MappedAddress) receiveMH.getMessageAttribute(MessageAttribute.MessageAttributeType.MappedAddress);
        System.out.println( ma.getAddress().toString() );
        System.out.println( ma.getPort() );
		ErrorCode ec = (ErrorCode) receiveMH.getMessageAttribute(MessageAttribute.MessageAttributeType.ErrorCode);
		if (ec != null) {
			LOGGER.debug("Message header contains an Errorcode message attribute.");
			return true;
		}
		if (ma == null) {
			LOGGER.debug("Response does not contain a Mapped Address message attribute.");
			return true;
		}
		return false;
	}
}
