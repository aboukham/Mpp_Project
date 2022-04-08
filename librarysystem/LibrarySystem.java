package librarysystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;


public class LibrarySystem extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds, addMember, addBookCopy, logout, checkoutBook;
    String pathToImage;
    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE,
			AddMemberWindow.INSTANCE,
			CheckoutBookWindow.INSTANCE
	};
    	
	public static void hideAllWindows() {
		
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);
			
		}
	}
    
    
    private LibrarySystem() {}
    
    public void init() {
    	formatContentPane();
    	setPathToImage();
    	insertSplashImage();
		
		createMenus();
		//pack();
		setSize(660,500);
		isInitialized = true;
    }
    
    private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		getContentPane().add(mainPanel);	
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
		//pathToImage = currDirectory + Path.of("src", "librarysystem", "library.jpg");
    	pathToImage = currDirectory + "/src/librarysystem/library.jpg";
    }
    
    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));	
    }
    private void createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);		
    }
    
    private void addMenuItems() {
       options = new JMenu("Options");  
 	   menuBar.add(options);
 	   login = new JMenuItem("Login");
		addMember = new JMenuItem("add library member");
 	   login.addActionListener(new LoginListener());
 	   allBookIds = new JMenuItem("All Book Ids");
 	   allBookIds.addActionListener(new AllBookIdsListener());
 	   allMemberIds = new JMenuItem("All Member Ids");
 	   allMemberIds.addActionListener(new AllMemberIdsListener());
		addMember.addActionListener(new AddMemberListener());
		addBookCopy = new JMenuItem("Add Book Copy");
		addBookCopy.addActionListener(new addListener());
		logout = new JMenuItem("Logout");
		logout.addActionListener(new LogoutListener());
		checkoutBook = new JMenuItem("Checkout Book");
		checkoutBook.addActionListener(new CheckoutListener());
 	   options.add(login);
 	   options.add(allBookIds);
 	   options.add(allMemberIds);
		options.add(addMember);
		options.add(addBookCopy);
		options.add(checkoutBook);
		options.add(logout);
    }
    
    class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);
			
		}
    	
    }

	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SystemController.isLoggedIn = false;
			SystemController.librarianNotAllowed = false;
			SystemController.adminNotAllowed = false;
			JOptionPane.showMessageDialog(menuBar, "logout successfully");
		}

	}

    class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!SystemController.isLoggedIn) {
				JOptionPane.showMessageDialog(login, "You are not login, click login option first");
			} else {
				LibrarySystem.hideAllWindows();
				AllBookIdsWindow.INSTANCE.init();

				List<String> ids = ci.allBookIds();
				Collections.sort(ids);
				StringBuilder sb = new StringBuilder();
				for (String s : ids) {
					sb.append(s + "\n");
				}
				System.out.println(sb.toString());
				AllBookIdsWindow.INSTANCE.setData(sb.toString());
				AllBookIdsWindow.INSTANCE.pack();
				//AllBookIdsWindow.INSTANCE.setSize(660,500);
				Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
				AllBookIdsWindow.INSTANCE.setVisible(true);

			}
		}
    }
    
    class AllMemberIdsListener implements ActionListener {

    	@Override
		public void actionPerformed(ActionEvent e) {
			if (!SystemController.isLoggedIn) {
				JOptionPane.showMessageDialog(login, "You are not login, click login option first");
			} else {
				LibrarySystem.hideAllWindows();
				AllMemberIdsWindow.INSTANCE.init();
				AllMemberIdsWindow.INSTANCE.pack();
				AllMemberIdsWindow.INSTANCE.setVisible(true);


				LibrarySystem.hideAllWindows();
				AllBookIdsWindow.INSTANCE.init();

				List<String> ids = ci.allMemberIds();
				Collections.sort(ids);
				StringBuilder sb = new StringBuilder();
				for (String s : ids) {
					sb.append(s + "\n");
				}
				System.out.println(sb.toString());
				AllMemberIdsWindow.INSTANCE.setData(sb.toString());
				AllMemberIdsWindow.INSTANCE.pack();
				//AllMemberIdsWindow.INSTANCE.setSize(660,500);
				Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
				AllMemberIdsWindow.INSTANCE.setVisible(true);
			}
		}
    }

	class AddMemberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!SystemController.isLoggedIn) {
				JOptionPane.showMessageDialog(login, "You are not login, click login option first");
			}else if (SystemController.librarianNotAllowed){
				JOptionPane.showMessageDialog(login, "You are not allowed to do this operation");
			}else {
				LibrarySystem.hideAllWindows();
				AddMemberWindow.INSTANCE.init();
				Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
				AddMemberWindow.INSTANCE.setVisible(true);
			}
		}

	}

	class addListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!SystemController.isLoggedIn) {
				JOptionPane.showMessageDialog(login, "You are not login, click login option first");
			}else if (SystemController.librarianNotAllowed){
				JOptionPane.showMessageDialog(login, "You are not allowed to do this operation");
			}else {
				LibrarySystem.hideAllWindows();
				AddBookCopyWindow.INSTANCE.init();
				Util.centerFrameOnDesktop(AddBookCopyWindow.INSTANCE);
				AddBookCopyWindow.INSTANCE.setVisible(true);
			}
		}

	}

	class CheckoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!SystemController.isLoggedIn) {
				JOptionPane.showMessageDialog(login, "You are not login, click login option first");
			}else if (SystemController.adminNotAllowed){
				JOptionPane.showMessageDialog(login, "You are not allowed to do this operation");
			}else {
				LibrarySystem.hideAllWindows();
				CheckoutBookWindow.INSTANCE.init();
				Util.centerFrameOnDesktop(AddBookCopyWindow.INSTANCE);
				CheckoutBookWindow.INSTANCE.setVisible(true);
			}
		}

	}


	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
    
}
