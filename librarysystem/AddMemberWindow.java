package librarysystem;

import business.Address;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMemberWindow extends JFrame implements LibWindow{
    public static final AddMemberWindow INSTANCE = new AddMemberWindow();

    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;
    private JPanel container;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel leftTextPanel1;
    private JPanel rightTextPanel1;
    private JPanel leftTextPanel2;
    private JPanel rightTextPanel2;
    private JPanel leftTextPanel3;
    private JPanel rightTextPanel3;
    private JPanel leftTextPanel4;
    private JPanel rightTextPanel4;
    private JLabel memberId;
    private JTextField  idText;
    private JLabel firstName;
    private JTextField  firstNameText;
    private JLabel lastName;
    private JTextField  lastNameText;
    private JLabel street;
    private JTextField  streetText;
    private JLabel city;
    private JTextField  cityText;
    private JLabel state;
    private JTextField  stateText;
    private JLabel zip;
    private JTextField  zipText;
    private JLabel telNumber;
    private JTextField  telNumberText;
    private JButton submitButton;
    private JButton backButton;




    public boolean isInitialized() {
        return isInitialized;
    }
    public void isInitialized(boolean val) {
        isInitialized = val;
    }
    private JTextField messageBar = new JTextField();
    public void clear(JTextField textField) {
        textField.setText("");
    }

    // This class is a singleton */
    private AddMemberWindow () {}

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

        backButton = new JButton("<= Back to Main");
        backButton.addActionListener(new BackToMainListener());
        lowerHalf.add(backButton);

        lowerHalf.setLayout(new FlowLayout(FlowLayout.RIGHT));

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitListener());
        lowerHalf.add(submitButton);

    }
    private void defineTopPanel() {
        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
        JLabel loginLabel = new JLabel("Add Member");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);

    }



    private void defineMiddlePanel() {
        middlePanel=new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel1);
        middlePanel.add(rightTextPanel1);
        middlePanel.add(leftTextPanel2);
        middlePanel.add(rightTextPanel2);
        middlePanel.add(leftTextPanel3);
        middlePanel.add(rightTextPanel3);
        middlePanel.add(leftTextPanel4);
        middlePanel.add(rightTextPanel4);
    }
    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        submitButton = new JButton("Submit");
        //submitButton.addActionListener(new SubmitListener());
        lowerPanel.add(submitButton);
    }

    private void createLabelAndTextField(JLabel label, JTextField text, String labelName, JPanel left){
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        text = new JTextField(10);
        label = new JLabel(labelName);
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(label);
        bottomText.add(text);
        left.setLayout(new BorderLayout());
        left.add(topText,BorderLayout.NORTH);
        left.add(bottomText,BorderLayout.CENTER);
    }


    private void defineLeftTextPanel() {
        leftTextPanel1 = new JPanel();
        leftTextPanel2 = new JPanel();
        leftTextPanel3 = new JPanel();
        leftTextPanel4 = new JPanel();
        //createLabelAndTextField(memberId, idText, "Member Id", leftTextPanel1);
        JPanel topText1 = new JPanel();
        JPanel bottomText1 = new JPanel();
        topText1.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText1.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        idText = new JTextField(10);
        memberId = new JLabel("Member Id");
        memberId.setFont(Util.makeSmallFont(memberId.getFont()));
        topText1.add(memberId);
        bottomText1.add(idText);
        leftTextPanel1.setLayout(new BorderLayout());
        leftTextPanel1.add(topText1,BorderLayout.NORTH);
        leftTextPanel1.add(bottomText1,BorderLayout.CENTER);

        //createLabelAndTextField(firstName, firstNameText, "First Name", leftTextPanel2);
        JPanel topText2 = new JPanel();
        JPanel bottomText2 = new JPanel();
        topText2.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText2.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        firstNameText = new JTextField(10);
        firstName = new JLabel("First Name");
        firstName.setFont(Util.makeSmallFont(firstName.getFont()));
        topText2.add(firstName);
        bottomText2.add(firstNameText);
        leftTextPanel2.setLayout(new BorderLayout());
        leftTextPanel2.add(topText2,BorderLayout.NORTH);
        leftTextPanel2.add(bottomText2,BorderLayout.CENTER);
        //createLabelAndTextField(lastName, lastNameText, "Last Name", leftTextPanel3);
        JPanel topText3 = new JPanel();
        JPanel bottomText3 = new JPanel();
        topText3.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText3.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        lastNameText = new JTextField(10);
        lastName = new JLabel("Last Name");
        memberId.setFont(Util.makeSmallFont(lastName.getFont()));
        topText3.add(lastName);
        bottomText3.add(lastNameText);
        leftTextPanel3.setLayout(new BorderLayout());
        leftTextPanel3.add(topText3,BorderLayout.NORTH);
        leftTextPanel3.add(bottomText3,BorderLayout.CENTER);
        //createLabelAndTextField(telNumber, telNumberText, "Telephone Number", leftTextPanel4);
        JPanel topText4 = new JPanel();
        JPanel bottomText4 = new JPanel();
        topText4.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText4.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        telNumberText = new JTextField(10);
        telNumber = new JLabel("Telephone Number");
        telNumber.setFont(Util.makeSmallFont(telNumber.getFont()));
        topText4.add(telNumber);
        bottomText4.add(telNumberText);
        leftTextPanel4.setLayout(new BorderLayout());
        leftTextPanel4.add(topText4,BorderLayout.NORTH);
        leftTextPanel4.add(bottomText4,BorderLayout.CENTER);
    }

    private void defineRightTextPanel() {
        rightTextPanel1 = new JPanel();
        rightTextPanel2 = new JPanel();
        rightTextPanel3 = new JPanel();
        rightTextPanel4 = new JPanel();
        //createLabelAndTextField(memberId, idText, "Member Id", leftTextPanel1);
        JPanel topText1 = new JPanel();
        JPanel bottomText1 = new JPanel();
        topText1.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText1.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        streetText = new JTextField(10);
        street = new JLabel("Street");
        street.setFont(Util.makeSmallFont(street.getFont()));
        topText1.add(street);
        bottomText1.add(streetText);
        rightTextPanel1.setLayout(new BorderLayout());
        rightTextPanel1.add(topText1,BorderLayout.NORTH);
        rightTextPanel1.add(bottomText1,BorderLayout.CENTER);

        //createLabelAndTextField(firstName, firstNameText, "First Name", leftTextPanel2);
        JPanel topText2 = new JPanel();
        JPanel bottomText2 = new JPanel();
        topText2.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText2.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        cityText = new JTextField(10);
        city = new JLabel("City");
        city.setFont(Util.makeSmallFont(city.getFont()));
        topText2.add(city);
        bottomText2.add(cityText);
        rightTextPanel2.setLayout(new BorderLayout());
        rightTextPanel2.add(topText2,BorderLayout.NORTH);
        rightTextPanel2.add(bottomText2,BorderLayout.CENTER);
        //createLabelAndTextField(lastName, lastNameText, "Last Name", leftTextPanel3);
        JPanel topText3 = new JPanel();
        JPanel bottomText3 = new JPanel();
        topText3.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText3.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        stateText = new JTextField(10);
        state = new JLabel("State");
        state.setFont(Util.makeSmallFont(state.getFont()));
        topText3.add(state);
        bottomText3.add(stateText);
        rightTextPanel3.setLayout(new BorderLayout());
        rightTextPanel3.add(topText3,BorderLayout.NORTH);
        rightTextPanel3.add(bottomText3,BorderLayout.CENTER);
        //createLabelAndTextField(telNumber, telNumberText, "Telephone Number", leftTextPanel4);
        JPanel topText4 = new JPanel();
        JPanel bottomText4 = new JPanel();
        topText4.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText4.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        zipText = new JTextField(10);
        zip = new JLabel("Zip Code");
        zip.setFont(Util.makeSmallFont(zip.getFont()));
        topText4.add(zip);
        bottomText4.add(zipText);
        rightTextPanel4.setLayout(new BorderLayout());
        rightTextPanel4.add(topText4,BorderLayout.NORTH);
        rightTextPanel4.add(bottomText4,BorderLayout.CENTER);
    }

    class BackToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        }
    }

    class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            Address address = new Address(streetText.getText(), cityText.getText(), stateText.getText(), zipText.getText());
            SystemController ci = new SystemController();
            ci.addNewMember(idText.getText(), firstNameText.getText(), firstNameText.getText(), telNumberText.getText(), address);
            JOptionPane.showMessageDialog(submitButton, "new member information submitted");
            clear(idText);
            clear(firstNameText);
            clear(lastNameText);
            clear(telNumberText);
            clear(streetText);
            clear(cityText);
            clear(stateText);
            clear(zipText);
        }
    }

}
