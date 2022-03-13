import java.io.Serializable;

public class phoneNumber implements Serializable {
    String number;
    String type;

    phoneNumber(String num, String ty){
        number = num;
        type = ty;
    }
    @Override
    public String toString() {

        return "Number:" + number  + " Type: " + type;
    }
}