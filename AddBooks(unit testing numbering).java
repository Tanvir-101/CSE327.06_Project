import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddBooks extends JInternalFrame {

************************************************* ******* 01
private JPanel northPanel = new JPanel();

    private JLabel northLabel = new JLabel("BOOK INFORMATION");

    private JPanel centerPanel = new JPanel();

    private JPanel informationLabelPanel = new JPanel();

    private JLabel[] informationLabel = new JLabel[10];
    private JLabel lblShelfNo = new JLabel(" Shelf No");
    private JTextField txtShelfNo = new JTextField();

    private String[] informationString = {
        " The book subject: ", " The book title: ",
        " The name of the Author(s): ", " The name of the Publisher: ",
        " Copyright year for the book: ", " The edition number: ", " The number of Pages: ",
        " ISBN for the book: ", " The number of copies: ", " The name of the Library: "};

    private JPanel informationTextFieldPanel = new JPanel();

    private JTextField[] informationTextField = new JTextField[10];

    private JPanel insertInformationButtonPanel = new JPanel();

    private JButton insertInformationButton = new JButton("Insert the Information");

    private JPanel southPanel = new JPanel();

    private JButton OKButton = new JButton("Exit");

    private Books book;

    private String[] data;

    private boolean availble = true;
******************************************** ************ 01

    public boolean isCorrect() {
**************************** 02        data = new String[10];
       **************************** 03,04,05 for (int i = 0; i < informationLabel.length; i++) {
 **************************** 06           if (!informationTextField[i].getText().equals("")) {
 **************************** 07               data[i] = informationTextField[i].getText();
            } else {
**************************** 08                return false;
            }
        }
 **************************** 09      return true;
    }

    public void clearTextField() {
**************************** 10,11,12       for (int i = 0; i < informationTextField.length; i++) {
**************************** 13           informationTextField[i].setText(null);
        }
**************************** 14      txtShelfNo.setText(null);
    }


    public AddBooks() {

**************************** 15
        super("Add Books", false, true, false, true);

        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Add16.gif")));

        Container cp = getContentPane();
        cp.setBackground(Color.white);

        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        northPanel.add(northLabel);

        cp.add("North", northPanel);

        centerPanel.setLayout(new BorderLayout());

        centerPanel.setBorder(BorderFactory.createTitledBorder("Add a new book:"));

        informationLabelPanel.setLayout(new GridLayout(11, 1, 1, 1));
**************************** 15

**************************** 16,17,18     for (int i = 0; i < informationLabel.length; i++) {
 **************************** 19         informationLabelPanel.add(informationLabel[i] = new JLabel(informationString[i]));
 ****************************            informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
        }
**************************** 20      centerPanel.add("West", informationLabelPanel);


****************************       informationTextFieldPanel.setLayout(new GridLayout(11, 1, 1, 1));

**************************** 21,22,23        for (int i = 0; i < informationTextField.length; i++) {
**************************** 24          informationTextFieldPanel.add(informationTextField[i] = new JTextField(25));
 ****************************            informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
**************************** 25,26,27,28            if(i==4 || i==5 || i==6 || i==8 )
 **************************** 29               informationTextField[i].addKeyListener(new keyListener());
        }
**************************** 30
        lblShelfNo.setFont(new Font("Tahoma", Font.BOLD, 11));
        informationLabelPanel.add(lblShelfNo);
        txtShelfNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
        informationTextFieldPanel.add(txtShelfNo);
        txtShelfNo.addKeyListener(new keyListener());
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
**************************** 30

            public void actionPerformed(ActionEvent ae) {


 **************************** 31             if (isCorrect()) {
 **************************** 32                 Thread runner = new Thread() {

                        public void run() {
**************************** 33
                            book = new Books();

                            book.connection("SELECT * FROM Books WHERE ISBN = '" + data[7] + "'");
**************************** 33  String ISBN = book.getISBN();
 **************************** 34         if (!data[7].equalsIgnoreCase(ISBN)) {
                                try{
**************************** 35           String sql="INSERT INTO Books (Subject,Title,Author,Publisher,Copyright," +
                                        "Edition,Pages,ISBN,NumberOfBooks,NumberOfAvailbleBooks,Library,Availble,ShelfNo) VALUES "+
                                        " (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                        Class.forName("org.gjt.mm.mysql.Driver");
                                        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
                                        PreparedStatement ps=con.prepareStatement(sql);
                                        ps.setString(1, data[0]);
                                        ps.setString(2, data[1]);
                                        ps.setString(3, data[2]);
                                        ps.setString(4, data[3]);
                                        ps.setInt(5, Integer.parseInt(data[4]));
                                        ps.setInt(6,Integer.parseInt(data[5]));
                                        ps.setInt(7, Integer.parseInt(data[6]));
                                        ps.setString(8, data[7]);
                                        ps.setInt(9, Integer.parseInt(data[8]));
                                        ps.setInt(10, Integer.parseInt(data[8]));
                                        ps.setString(11, data[9]);
                                        ps.setBoolean(12, availble);
                                        ps.setInt(13, Integer.parseInt(txtShelfNo.getText()));
                                        ps.executeUpdate();
                               }
**************************** 35
 **************************** 36        catch(Exception ex){
**************************** 37       JOptionPane.showMessageDialog(null, ex.toString());
                                }

**************************** 38        dispose();
                            } else {
**************************** 39       JOptionPane.showMessageDialog(null, "The book is in the library", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    };
**************************** 40                  runner.start();
                }
                else {
 **************************** 41             JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

 **************************** 42       OKButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
 **************************** 43              dispose();
            }
        });

  **************************** 44
        setVisible(true);

        pack();
        **************************** 44
    }
    class keyListener extends KeyAdapter {

        public void keyTyped(KeyEvent e) {
 **************************** 45             char c = e.getKeyChar();
 ****************************  46               if (!(Character.isDigit(c) ||
**************************** 47                      (c == KeyEvent.VK_BACK_SPACE) ||
**************************** 48                        (c == KeyEvent.VK_ENTER) ||
**************************** 49                         (c == KeyEvent.VK_DELETE))) {

**************************** 50                    getToolkit().beep();
                    JOptionPane.showMessageDialog(null, "This Field Only Accept Integer Number", "WARNING",JOptionPane.DEFAULT_OPTION);
                    e.consume();
                 }**************************** 50
            }
    }
}
