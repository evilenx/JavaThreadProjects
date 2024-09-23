import javax.swing.*;
class Java_JLabel_SignIn
{
	public static void main(String args[])
	{
		//Adding our Frame
		JFrame f= new JFrame("Label Demo");
		//Creating objects for our Labels
		JLabel label1,label2;
		//Creating object for Sign In button
		JButton Button1;
		//Creating object for our text boxes
		JTextField TextBox1,TextBox2;
		//Creating our button
		Button1=new JButton("Sign In");
		//Creating our first Label
		label1=new JLabel("User Name:");
		//Creating our second label
		label2=new JLabel("Password:");
		//Creating our first text field
		TextBox1 = new JTextField(20);
		//Creating our second text field
		TextBox2 = new JTextField(20);
		//Setting bound for our Label1
		label1.setBounds(50,50, 100,30);
		//Setting bound for our Label2
		label2.setBounds(50,100, 100,30);
		//Setting bound for our TextBox1
		TextBox1.setBounds(180,50, 150,20);
		//Setting bound for our TextBox2
		TextBox2.setBounds(180,100, 150,20);
		//Setting bound for our Button1
		Button1.setBounds(110,150,95,30);
		//Adding our Label1,Label2,TextBox1,TextBox2,Button1 to our frame
		f.add(label1);
		f.add(label2);
		f.add(Button1);
		f.add(TextBox1);
		f.add(TextBox2);
		f.setSize(300,300);
		f.setLayout(null);
		f.setVisible(true);
	}
}


