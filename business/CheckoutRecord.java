package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {
    private List<CheckoutRecordEntry>   entries;

    public List<CheckoutRecordEntry> getEntries() {
        return entries;
    }

    public CheckoutRecord() {
        this.entries = new ArrayList<>();
    }

    public void addCheckoutEntry(CheckoutRecordEntry entry){
        entries.add(entry);
    }

    public String[][] storeEntriesInformationInArray(){
        String[][] data = new String[entries.size()][3];
        for (int i = 0; i < data.length; i++){
            data[i][0] = entries.get(i).getBookCopy().getBook().toString();
            data[i][1] = entries.get(i).getCheckoutDate().toString();
            data[i][2] = entries.get(i).getDueDate().toString();
        }
        return data;
    }
}
