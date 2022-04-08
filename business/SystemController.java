package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import javax.swing.*;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static boolean isLoggedIn = false;
	public static boolean librarianNotAllowed = false;
	public static boolean adminNotAllowed = false;



	public boolean isAdminNotAllowed() {
		return adminNotAllowed;
	}

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		isLoggedIn = true;
		currentAuth = map.get(id).getAuthorization();
		if (currentAuth == Auth.LIBRARIAN)
			librarianNotAllowed = true;
		else if (currentAuth == Auth.ADMIN)
			adminNotAllowed = true;
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public void addNewMember(String id, String firstName, String lastName, String telNumber, Address add) {
		librarianNotAllowed = true;
		CheckoutRecord record = new CheckoutRecord();
		LibraryMember   member = new LibraryMember(id, firstName, lastName, telNumber, add, record);
		DataAccessFacade    da = new DataAccessFacade();
		da.saveNewMember(member);
	}

	@Override
	public Book addNewCopy(String isbn) {
		librarianNotAllowed = true;
		DataAccessFacade da = new DataAccessFacade();
		Book book = da.searchBook(isbn);
		if (book != null)
			book.addCopy();
		return book;
	}

	@Override
	public void checkoutBook(String memberId, String isbn) throws CheckoutException {
		DataAccessFacade    da = new DataAccessFacade();
		Book book = da.searchBook(isbn);
		LibraryMember member = da.searchMember(memberId);
		if (member == null){
			throw new CheckoutException("The id member not found");
		}else if (book == null || !book.isAvailable()) {
			throw new CheckoutException("The item is not available");
		}else
			member.checkout(book.getNextAvailableCopy(), LocalDate.now(), LocalDate.now().plusDays(book.getMaxCheckoutLength()));
	}
}
