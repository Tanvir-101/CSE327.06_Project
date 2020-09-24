import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Date;


public class AddMembers extends JInternalFrame {
	/***************************************************************************
	 ***      declaration of the private variables used in the program       ***
	 ***************************************************************************/

	
	private JPanel northPanel = new JPanel();
	
	private JLabel northLabel = new JLabel("MEMBER INFORMATION");

	
	private JPanel centerPanel = new JPanel();
	
	private JPanel informationLabelPanel = new JPanel();
	
	private JLabel[] informationLabel = new JLabel[7];
	
    private String[] informaionString = {" Reg. No: ", " The Password: ", " Rewrite the password: ",
	                                     " The Name: ", " E-Mail: ", " Major: ", " Valid Upto: "};
	
	private JPanel informationTextFieldPanel = new JPanel();
	
	private JTextField[] informationTextField = new JTextField[4];
	
	private JPasswordField[] informationPasswordField = new JPasswordField[2];

	
	private JPanel insertInformationButtonPanel = new JPanel();
	
	private JButton insertInformationButton = new JButton("Insert the Information");


	private JPanel southPanel = new JPanel();
	
	private JButton OKButton = new JButton("Exit");

	
	private Members member;
	
	private String[] data;

    private DateButton expiry_date;

	
	public boolean isPasswordCorrect() {
        if (Arrays.equals(informationPasswordField[0].getPassword(),informationPasswordField[1].getPassword()))
            data[1] = new String(informationPasswordField[1].getPassword());
        else if(!Arrays.equals(informationPasswordField[0].getPassword(),informationPasswordField[1].getPassword()))
            return false;
        return true;
		
	}

	
	public boolean isCorrect() {
		data = new String[6];
		for (int i = 0; i < informationLabel.length; i++) {
			if (i == 0) {
				if (!informationTextField[i].getText().equals("")) {
					data[i] = informationTextField[i].getText();
				}
				else
					return false;
			}
			if (i == 1 || i == 2) {
				
                if (informationPasswordField[i-1].getPassword().length==0)
					return false;
			}
			if (i == 3 || i == 4 || i == 5) {
				if (!informationTextField[i - 2].getText().equals("")) {
					data[i - 1] = informationTextField[i - 2].getText();
				}
				else
					return false;
			}
            if(i==6)
            {
                if(!expiry_date.getText().equals(""))
                {
                data[i-1]=expiry_date.getText();
                }
                else
                    return false;
            }
		}
		return true;
	}

	
	public void clearTextField() {
		for (int i = 0; i < informationLabel.length; i++) {
			if (i == 0)
				informationTextField[i].setText(null);
			if (i == 1 || i == 2)
				informationPasswordField[i - 1].setText(null);
                //informationPasswordField[i - 1].setPassword("");
			if (i == 3 || i == 4 || i == 5)
				informationTextField[i - 2].setText(null);
		}
	}
	
	public AddMembers() {
		
		super("Add Members", false, true, false, true);
		
		setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Add16.gif")));
       
		Container cp = getContentPane();
        expiry_date = new DateButton();
        expiry_date.setForeground(Color.red);

		
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		northPanel.add(northLabel);
		
		cp.add("North", northPanel);

		
		centerPanel.setLayout(new BorderLayout());
		
		centerPanel.setBorder(BorderFactory.createTitledBorder("Add a new member:"));
		
		informationLabelPanel.setLayout(new GridLayout(7, 1, 1, 1));
		
		informationTextFieldPanel.setLayout(new GridLayout(7, 1, 1, 1));
		
		for (int i = 0; i < informationLabel.length; i++) {
			informationLabelPanel.add(informationLabel[i] = new JLabel(informaionString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		
		centerPanel.add("West", informationLabelPanel);

		
		for (int i = 0; i < informationLabel.length; i++) {
			if (i == 1 || i == 2) {
				informationTextFieldPanel.add(informationPasswordField[i - 1] = new JPasswordField(25));
				informationPasswordField[i - 1].setFont(new Font("Tahoma", Font.PLAIN, 11));
			}
			if (i == 0) {
				informationTextFieldPanel.add(informationTextField[i] = new JTextField(25));
				informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
                informationTextField[i].addKeyListener(new keyListener());
			}
			if (i == 3 || i == 4 || i == 5) {
				informationTextFieldPanel.add(informationTextField[i - 2] = new JTextField(25));
				informationTextField[i - 2].setFont(new Font("Tahoma", Font.PLAIN, 11));
                }
            if(i==6)
            {
                informationTextFieldPanel.add(expiry_date);
            }
		}
		centerPanel.add("East", informationTextFieldPanel);

		
		insertInformationButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		insertInformationButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		insertInformationButtonPanel.add(insertInformationButton);
		centerPanel.add("South", insertInformationButtonPanel);
		cp.add("Center", centerPanel);

		
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		OKButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		southPanel.add(OKButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		cp.add("South", southPanel);

		
		insertInformationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				if (isCorrect()) {
					if (isPasswordCorrect()) {
						Thread runner = new Thread() {
							public void run() {
                                Date expiryDate= new Date();
                                expiryDate=expiry_date.getDate();
                                Date presentDate=new Date();
                                if(presentDate.before(expiryDate))
                               {
                                member = new Members();
								
								member.connection("SELECT * FROM Members WHERE RegNo = " + data[0]);
								int regNo = member.getRegNo();
								if (Integer.parseInt(data[0]) != regNo) {
									member.update("INSERT INTO Members (RegNo,Password,Name,EMail,Major,ValidUpto) VALUES (" +
									        data[0] + ", '" + data[1] + "','" + data[2] + "','" +
									        data[3] + "','" + data[4] + "','" + data[5] + "')");
									
                                    dispose();
								}
								else
									JOptionPane.showMessageDialog(null, "Member is in the Library", "Error", JOptionPane.ERROR_MESSAGE);
							}
                                else
                                    JOptionPane.showMessageDialog(null, "Expiry Date is invalid", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
						};
						runner.start();
					}
					
					else
						JOptionPane.showMessageDialog(null, "the passowrd is wrong", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				else
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		OKButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		
		setVisible(true);
		
		pack();
	}

    class keyListener extends KeyAdapter {

        public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_ENTER) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    JOptionPane.showMessageDialog(null, "This Field Only Accept Integer Number", "WARNING",JOptionPane.DEFAULT_OPTION);
                    e.consume();
                 }
            }
    }
}