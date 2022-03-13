import java.io.Serializable;
import java.util.ArrayList;




public class contact implements Serializable {
    //private static final long serialVersionUID = 1L;
    String name;
     ArrayList<phoneNumber> numbers = new ArrayList<phoneNumber>();

    boolean IsFav;

    public contact(String uName,ArrayList<phoneNumber> nums ,boolean isFav){
        name = uName;
        numbers = nums;
        IsFav = isFav;

    }
    @Override
    public String toString() {
        //li = myContacts.listIterator();
        return "Name:" + name  +"\nPhone Numbers:"+ numbers.toString()

                + "\nIsFav: " + IsFav;
    }



}
