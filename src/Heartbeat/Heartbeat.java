/**
 *  	Heartbeat applet
 *
 * 		Author: Diego Leñero Ramírez 
 *				diego.lenero@podgroup.com
 *		 
 */


package Heartbeat;

import sim.toolkit.*;
import sim.access.*;
import javacard.framework.*;

public class Heartbeat extends Applet implements ToolkitInterface, ToolkitConstants {

	static 	byte[] 	menuItemText = new byte[] {'H','e','a','r','t','b','e','a','t'};
	private byte 	menuItem;
    private ToolkitRegistry reg;

    private static byte[] helloWorld = new byte[] {'H','e','l','l','o','W','o','r','l','d'};
    private byte[] data;        // buffer 


    // constants to manage nibbles
    public static final byte NIBBLE_SIZE  = (byte)4;
    public static final byte UPPER_NIBBLE  = (byte)0xF0;
    public static final byte LOWER_NIBBLE  = (byte)0x0F;

    

    // BIP Constants
    private static final byte TAG_DATA_DESTINATION_ADDRESS           = (byte) 0x3E;

    // Bearer variables
    // --> GPRS case...
    
    // The APN is a reference to a GGSN: myoperator.mycountry
    // This value is coded in a special way: LVLV...
    private static byte[] myAPN = {(byte)0x0A,'d','a','t','a','6','4','1','0','0','3'};
    
                

    //GPRS = 0x02
    private static final byte BEARER_TYPE_GPRS                       = (byte) 0x02; 

    // Type of Address IPV4=21,
    private static final byte TYPE_OF_ADDRESS_IPV4      = (byte) 0x21;
    // SIM/ME interface transport level UDP=01
    //private static final byte TRANSPORT_PROTOCOL_TYPE   = (byte) 0x01;
        private static final byte TRANSPORT_PROTOCOL_TYPE = (byte) 0x02;


    private byte result;

     // Allocate 500 bytes for the reception buffer
    private static final short BUFFER_SIZE = (short)0x01F4;  
    // Maximum response handler capacity to be used for the receive data 
    private static short myRspHdlrCapacity;

    // Port number : integer
    private static byte[] portNumber = {(byte)0x00,(byte)0x23};
    // IP Address of the Server 
    //private static byte[] IPAddress = {(byte)163,(byte)187,(byte)203,(byte)1};
    private static byte[] IPAddress = {(byte)172,(byte)217,(byte)17,(byte)3}; // google

    // Channel Identifier given by the ME after the OPEN CHANNEL proactive command
    private static byte channelID = 0x00;



	/**
	*	Constructor of the applet
	*/
	private Heartbeat() {
		reg = ToolkitRegistry.getEntry(); // register to the SIM Toolkit Framework
		//reg.setEvent(event...);

		// Define the applet Menu Entry
		menuItem = reg.initMenuEntry(menuItemText, (short)0, (short)menuItemText.length,
				PRO_CMD_SELECT_ITEM, false, (byte)0, (short)0);
        data        = JCSystem.makeTransientByteArray ((short)2, JCSystem.CLEAR_ON_RESET);
	}

	/**
    * Method called by the JCRE at the installation of the applet
    */
	public static void install(byte[] bArray, short bOffset, byte bLength) {
		Heartbeat applet = new Heartbeat();
		applet.register();
	}


	public void process(APDU apdu) throws ISOException {
		if (selectingApplet())
		return;
	}
	
    /**
    * Method called by the SIM Toolkit Framework
    */
	public void processToolkit(byte event) throws ToolkitException {
		EnvelopeHandler		envHdlr = EnvelopeHandler.getTheHandler();
        ProactiveHandler	proHdlr = ProactiveHandler.getTheHandler();
        ProactiveResponseHandler rspHdlr ;

		if (event == EVENT_MENU_SELECTION) {
			byte selectedItemId = envHdlr.getItemIdentifier();

			if (selectedItemId == menuItem) {

                try {

    				// 1st Open GPRS connection
    				// prepare the OPEN CHANNEL (inmediate link establishment = 0x01 for the qualifier byte)
    				proHdlr.init(PRO_CMD_OPEN_CHANNEL,(byte)1, DEV_ID_ME);
    				// Add Bearer description TLV
                    proHdlr.appendTLV((byte)(TAG_BEARER_DESCRIPTION | TAG_SET_CR),(byte) BEARER_TYPE_GPRS);
                    // Add Buffer size TLV
                    proHdlr.appendTLV((byte)(TAG_BUFFER_SIZE | TAG_SET_CR),(byte)(BUFFER_SIZE>>(byte)8),(byte)BUFFER_SIZE);
                    // Add APN TLV
                    proHdlr.appendTLV((byte)(TAG_NETWORK_ACCESS_NAME | TAG_SET_CR),myAPN,(short)0,(short)myAPN.length);

                    // Add SIM/ME interface transport level TCP=02, Port 80 = http
                    proHdlr.appendTLV((byte) (TAG_SIM_ME_INTERFACE_TRANSPORT_LEVEL | TAG_SET_CR),TRANSPORT_PROTOCOL_TYPE, 
                                        portNumber, (short)0, (short)portNumber.length);
                    // Add Data Destination Address TLV
                    // Type of Address IPV4=21, IPAddress contains the IPV4 Address
                    proHdlr.appendTLV((byte) (TAG_DATA_DESTINATION_ADDRESS | TAG_SET_CR),TYPE_OF_ADDRESS_IPV4, 
                                        IPAddress,(short)0, (short) IPAddress.length);
                    result = proHdlr.send();

/*
                    // 2° Retrieve the channel ID allocated by the ME 
                    if (result == RES_CMD_PERF){
                        rspHdlr = ProactiveResponseHandler.getTheHandler();
                        channelID = rspHdlr.getChannelIdentifier();
                        // Get the maximum size of the Simple TLV list managed by the response handler
                        myRspHdlrCapacity = rspHdlr.getCapacity();
            
                    }
                    else {
                        channelID = 0x00;
                    }


                    proHdlr.init((byte)PRO_CMD_SEND_DATA,(byte)0,channelID);
                    proHdlr.appendTLV(TAG_CHANNEL_DATA,helloWorld,(short)0,(short)helloWorld.length);
                    proHdlr.send();

                    proHdlr.initCloseChannel(channelID);
                    proHdlr.send();
*/

                }

                /*catch (SIMViewException MyException){
                    short reason = MyException.getReason();
                    data[1]=(byte) reason;
                    data[0]=(byte) (((reason & UPPER_NIBBLE) >> NIBBLE_SIZE)& LOWER_NIBBLE);
                    proHdlr.initDisplayText((byte)0x80, DCS_8_BIT_DATA, data,(short)0, (short)2);
                    proHdlr.send();
                }*/

                catch (ToolkitException MyException) {
                    short reason = MyException.getReason();
                    data[1]=(byte) reason;
                    data[0]=(byte) (((reason & UPPER_NIBBLE) >> NIBBLE_SIZE)& LOWER_NIBBLE);
                    proHdlr.initDisplayText((byte)0x80, DCS_8_BIT_DATA, data,(short)0, (short)2);
                    proHdlr.send();
                }
			}
		}
	} 
}