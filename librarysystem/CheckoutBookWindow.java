package librarysystem;

import business.*;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Member;
import java.time.LocalDate;

public class CheckoutBookWindow extends JFrame implements LibWindow{
    public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();


    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;
    private JPanel container;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JTextField  isbnText;
    private JLabel isbn;
    private JTextField  memberIdText;
    private JLabel memberId;
    private JButton checkoutButton;
    private JButton displayButton;


    public boolean isInitialized() {
        return isInitialized;
    }
    public void isInitialized(boolean val) {
        isInitialized = val;
    }
    private JTextField messageBar = new JTextField();
    public void clear() {
        messageBar.setText("");
    }

    /* This class is a singleton */
    private CheckoutBookWindow() {}

    public void init() {
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();
        defineLowerHalf();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);
        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        mainPanel.add(lowerHalf, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
        isInitialized(true);
        pack();
        //setSize(660, 500);


    }
    private void defineUpperHalf() {
        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        upperHalf.add(topPanel, BorderLayout.NORTH);
        upperHalf.add(middlePanel, BorderLayout.CENTER);
        upperHalf.add(lowerPanel, BorderLayout.SOUTH);
    }

    private void defineMiddleHalf() {
        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        //middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
        middleHalf.add(s, BorderLayout.SOUTH);
    }

    private void defineLowerHalf() {
        lowerHalf = new JPanel();
        lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("<= Back to Main");
        addBackButtonListener(backButton);
        lowerHalf.add(backButton);

    }

    private void defineTopPanel() {
        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
        JLabel isbnLabel = new JLabel("Add Book Copy");
        Util.adjustLabelFont(isbnLabel, Color.BLUE.darker(), true);
        intPanel.add(isbnLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        displayButton = new JButton("Show Record");
        checkoutButton = new JButton("Checkout");
        checkoutButtonListener(checkoutButton);
        displayButton.addActionListener(new displayListener());
        lowerPanel.add(checkoutButton);
        lowerPanel.add(displayButton);
    }

    private void defineLeftTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        memberIdText = new JTextField(10);
        memberId = new JLabel("Member Id");
        memberId.setFont(Util.makeSmallFont(memberId.getFont()));
        topText.add(memberId);
        bottomText.add(memberIdText);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText,BorderLayout.NORTH);
        leftTextPanel.add(bottomText,BorderLayout.CENTER);
    }

    private void defineRightTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        isbnText = new JTextField(10);
        isbn = new JLabel("Book Isbn");
        isbn.setFont(Util.makeSmallFont(isbn.getFont()));
        topText.add(isbn);
        bottomText.add(isbnText);

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText,BorderLayout.NORTH);
        rightTextPanel.add(bottomText,BorderLayout.CENTER);
    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void checkoutButtonListener(JButton butn){
        butn.addActionListener(evt -> {
            SystemController ci = new SystemController();
            try{
                ci.checkoutBook(memberIdText.getText(), isbnText.getText());
                JOptionPane.showMessageDialog(this, "checkout success");
            }catch(CheckoutException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });
    }

    class displayListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame();
            frame.setTitle("Checkout Record");
            String id = memberIdText.getText();
            String bookId = isbnText.getText();
            DataAccessFacade da = new DataAccessFacade();
            LibraryMember member = da.searchMember(id);
            Book book = da.searchBook(bookId);
            if (member == null || book == null){
                JOptionPane.showMessageDialog(frame, "the id member not found");
            }else {
                String[] columnsNames = {"Items Checked out", "Checkout Date", "Due Date"};
                CheckoutRecord	record = new CheckoutRecord();
                record.addCheckoutEntry(new CheckoutRecordEntry(LocalDate.now(), LocalDate.now().plusDays(21), book.getCopy(1)));
                record.addCheckoutEntry(new CheckoutRecordEntry(LocalDate.of(2022, 3, 21), LocalDate.of(2022, 04, 11), book.getCopy(1)));
                member.setRecord(record);
                String[][] data = member.getRecord().storeEntriesInformationInArray();
                JTable table = new JTable(data, columnsNames);
                table.setBounds(50, 50, 300, 300);
                JScrollPane sp = new JScrollPane();
                frame.add(sp);
                frame.setSize(500, 400);
                frame.setVisible(true);
            }
        }
    }
}
