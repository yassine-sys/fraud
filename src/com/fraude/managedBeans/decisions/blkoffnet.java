package com.fraude.managedBeans.decisions;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.telnet.TelnetClient;

public class blkoffnet {

	    private TelnetClient telnet = new TelnetClient();
	    private InputStream in;
	    private PrintStream out;
	    private String prompt = ">";
	    private String host="129.20.129.1";
	    private int port=23;
	    private String login="offnetsimbox";
	    private String pwd="chinguitel2016";

	    public blkoffnet(String server, String user, String password) {
	        try {
	                // Connect to the specified server
	                telnet.connect(host, 23);

	                // Get input and output stream references
	                in = telnet.getInputStream();
	                out = new PrintStream(telnet.getOutputStream());

	                // Log the user on
	                readUntil("username:");
	                write(login);
	                readUntil("password:");
	                write(pwd);

	                // Advance to a prompt
	                readUntil(prompt);
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	    }

	    public void su(String password) {
	        try {
	                write("su");
	                readUntil("Password: ");
	                write(password);
	                prompt = "#";
	                readUntil(prompt + " ");
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	    }

	    public String readUntil(String pattern) {
	        try {
	                char lastChar = pattern.charAt(pattern.length() - 1);
	                StringBuffer sb = new StringBuffer();
	                boolean found = false;
	                char ch = (char) in.read();
	                while (true) {
	                        //System.out.print(ch);
	                        sb.append(ch);
	                        if (ch == lastChar) {
	                                if (sb.toString().endsWith(pattern)) {
	                                        return sb.toString();
	                                }
	                        }
	                        ch = (char) in.read();
	                }
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	        return null;
	    }

	    public void write(String value) {
	        try {
	                out.println(value);
	                out.flush();
	                //System.out.println(value);
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	    }

	    public String sendCommand(String command) {

	        try {
	                String testoutput;
	                write(command);

	                testoutput= readUntil("$MSCE-20>");
	                //System.out.println(testoutput);


	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	        return null;
	    }


	    public boolean sendCommandblk(String NUM) {
	        boolean out=true;

	        try {

	                String testoutput ="";
	                //char ch;
	                        if(NUM.startsWith("4")){
	                        write("ADD DNAL:ENTR=30,DIGIT=\""+NUM+"\",CAT=VACANT;");
	                        readUntil("$MSCE-20>");
	                        testoutput= readUntil("$MSCE-20>");
	                        System.out.println(testoutput);
	                }else if(NUM.startsWith("3")){
	                        write("ADD DNAL:ENTR=8,DIGIT=\""+NUM+"\",CAT=VACANT;");
	                        readUntil("$MSCE-20>");
	                        testoutput= readUntil("$MSCE-20>");
	                                                //System.out.println(testoutput);
	                }


	                if(testoutput.contains("Detail information:The analyzed number has already existed!")) out=false;
	                //char ch=(char)System.in.read();



	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	        return out;
	    }

	    
	    
	    public boolean senddeblkcmd(String NUM){
	        boolean out=true;

	        try {

	                String testoutput ="";
	                //char ch;
	                        if(NUM.startsWith("4")){
	                        	write("DEL DNAL:ENTR=30,DIGIT=\""+NUM+"\";"); 
	                   
	                        readUntil("$MSCE-20>");
	                        testoutput= readUntil("$MSCE-20>");
	                        System.out.println(testoutput);
	                }else if(NUM.startsWith("3")){
	                	write("DEL DNAL:ENTR=8,DIGIT=\""+NUM+"\";"); 
	                        readUntil("$MSCE-20>");
	                        testoutput= readUntil("$MSCE-20>");
	                                                //System.out.println(testoutput);
	                }


	                if(testoutput.contains("Detail information:The analyzed number has already existed!")) out=false;
	                //char ch=(char)System.in.read();



	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	        return out;
	    }
	     public String sendCommandSYN() {

	        try {
	                String testoutput;
	                write("SYN;");

	                testoutput=readUntil("Are you sure to transfer data?(Press 'Y/y' Confirm to run,'N/n' cancel running.)");
	                //System.out.println(testoutput);
	                write("y");
	                testoutput=readUntil("Auto backup data after sync success, the path of the file on the server :");
	                                //if(testoutput.contains("Execute error:Current user has no data to be synchronized."))  System.exit(0);
	                //System.out.println(testoutput);


	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	        return null;
	    }

	    public void disconnect() {
	        try {

	                telnet.disconnect();
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	    }











































	        public static boolean isok5(){
	                boolean out=false;
	                Calendar cal = Calendar.getInstance();
	                System.out.println((cal.getTimeInMillis()/60000)%5);

	                if(((cal.getTimeInMillis()/60000)%5)==0){out=true;}
	                return out;
	        }

	 
	}
