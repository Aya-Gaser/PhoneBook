import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class main_3 {
    static ArrayList<contact> myContacts = new ArrayList<contact>();
    static File file = new File("myPhonebook.txt");
    static ObjectInputStream ois = null;
    public static void readContacts(){
        if(file.isFile()){
            try {
                ois = new ObjectInputStream(new FileInputStream(file));
                myContacts = (ArrayList<contact>) ois.readObject();
                ois.close();
            }
            catch (EOFException exc)
            {
                System.out.println("No Contacts Saved Yet");
            }
            catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println("Error initializing stream reading");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String option = "-1";
        Scanner myObj = new Scanner(System.in);
        Scanner myObjs = new Scanner(System.in);
        Scanner myObjs2 = new Scanner(System.in);
        Scanner myObjs3 = new Scanner(System.in);
        Scanner myObjs4 = new Scanner(System.in);
        Scanner myObjs5 = new Scanner(System.in);
        ObjectOutputStream oos = null;
        ListIterator li = null;
        readContacts();

        do {
            ArrayList<contact> foundContacts = new ArrayList<contact>();
            System.out.println("Welcome To Your Phonebook!");
            System.out.println("Select option.. ");
            System.out.println("1.Add New Contact \n2.View All Contacts \n3.View Favourite Contacts \n4.Search For Contact \n5.Update Contact \n6.Delete Contact \n7.Set Fav Contact \n0.Exit");
            option = myObjs3.nextLine();
            if(option.equals("0") || option.equals("1") || option.equals("2") || option.equals("3") ||option.equals("4") ||option.equals("5") ||option.equals("6") ||option.equals("7") ) {
                switch (option) {
                    case "1":
                        boolean existName = true;
                        boolean existNumber = true;
                        boolean repeted = true;
                        String nameN = "";
                        String number = "";
                        int nNums;
                        ArrayList<phoneNumber> conNums = new ArrayList<phoneNumber>();
                        String numType;
                        while (existName) {
                            System.out.println("Enter name ");
                            nameN = myObjs.nextLine();
                            existName = checkNameNumberDup(true, nameN);
                            if (existName)
                                System.out.println("Name Already Exist, Please Enter Another Name");
                        }
                        System.out.println("Enter number of phone numbers  ");
                        nNums = myObjs2.nextInt();
                        for (int i = 0; i < nNums ; i++) {
                            while (existNumber || repeted) {
                                System.out.println("Enter number ");
                                number = myObjs4.nextLine();
                                existNumber = checkNameNumberDup(false, number);
                                repeted = IsInputDup(number, conNums);

                                if (existNumber)
                                    System.out.println("Number Already Exist, Please Enter Another Number");
                                else if(repeted)
                                    System.out.println("You Entered This Number before, Please Enter Another Number");

                            }
                            existNumber = true;
                            repeted = true;
                            System.out.println("Enter type for number (Mobile, Home, Work)");
                            numType = myObjs5.nextLine();
                            conNums.add(new phoneNumber(number, numType));

                        }

                        myContacts.add(new contact(nameN, conNums, false));
                        System.out.println(myContacts);
                        oos = new ObjectOutputStream(new FileOutputStream(file));
                        oos.writeObject(myContacts);
                        oos.close();
                        System.out.println("Contact Saved Successfully");
                        break;

                    case "2" :
                        readContacts();
                        System.out.println(" ------------Your Contacts ------------------");
                        if (myContacts.size() > 0)
                            displayContacts(myContacts);
                        else
                            System.out.println("No Contacts Saved Yet");
                        System.out.println("---------------------------------------------");
                        break;

                    case "3" :
                        readContacts();
                        ArrayList<contact> favContacts = new ArrayList<contact>();
                        System.out.println(" ------------Your Fav Contacts ------------------");
                        li = myContacts.listIterator();
                        while (li.hasNext()) {
                            contact con = (contact) li.next();
                            if (con.IsFav)
                                favContacts.add(con);
                        }
                        if (favContacts.size() > 0)
                            displayContacts(favContacts);
                        else
                            System.out.println("No Fav Contacts Yet");
                        System.out.println("---------------------------------------------");
                        break;
                    // serach for contact
                    case "4":
                        readContacts();
                        System.out.println("Select Search Filter");
                        System.out.println("1.Name \n2.Number");
                        String s;
                        int filterOp = myObj.nextInt();
                        if (filterOp == 1)
                            s = "Name";
                        else if (filterOp == 2)
                            s = "Number";
                        else
                            break;
                        System.out.println("Enter Contact " + s);
                        String filter = myObjs2.nextLine();
                        li = myContacts.listIterator();
                        while (li.hasNext()) {
                            contact con = (contact) li.next();
                            switch (filterOp) {
                                case 1:
                                    if (con.name.contains(filter))
                                        foundContacts.add(con);
                                    break;
                                case 2:
                                    for (int i = 0; i < con.numbers.size(); i++) {
                                        if (con.numbers.get(i).number.equals(filter))
                                            foundContacts.add(con);
                                    }


                                    break;

                            }

                        }
                        if (foundContacts.size() > 0)
                            displayContacts(foundContacts);
                        else
                            System.out.println("No Results Matching");
                        System.out.println("---------------------------------------------");

                        break;
                    case "5" :
                        readContacts();
                        System.out.println("Enter Contact Name");
                        String filterE = myObjs.nextLine();
                        li = myContacts.listIterator();
                        contact con = null;
                        boolean foundE = false;
                        while (li.hasNext()) {
                            con = (contact) li.next();
                            if (con.name.equals(filterE)) {
                                foundE = true;
                                System.out.println(con);
                                li.remove();
                                break;
                            }

                        }

                        if (foundE) {
                            boolean EexistName = true;
                            boolean EexistNumber = true;
                            boolean repeted2 = true;
                            int numsE;
                            String newName = "";
                            String newNumber = "";
                            ArrayList<phoneNumber> EconNums = new ArrayList<phoneNumber>();
                            while (EexistName) {
                                System.out.println("Enter name ");
                                newName = myObjs.nextLine();
                                EexistName = checkNameNumberDup(true, newName);
                                if (EexistName)
                                    System.out.println("Name Already Exist, Please Enter Another Name");
                            }
                            System.out.println("Enter number of phone numbers  ");
                            numsE = myObjs2.nextInt();
                            for (int i = 0; i < numsE ; i++) {
                                while (EexistNumber || repeted2) {
                                    System.out.println("Enter number ");
                                    newNumber = myObjs4.nextLine();
                                    EexistNumber = checkNameNumberDup(false, newNumber);
                                    repeted2 = IsInputDup(newNumber, EconNums);
                                    if (EexistNumber)
                                        System.out.println("Number Already Exist, Please Enter Another Number");
                                    if (repeted2)
                                        System.out.println("You Entered This Number Before , Please Enter Another Number");
                                }
                                EexistNumber = true;
                                repeted2 = true;
                                System.out.println("Enter type for number (Mobile, Home, Work)");
                                numType = myObjs5.nextLine();
                                EconNums.add(new phoneNumber(newNumber, numType));

                            }


                            myContacts.add(new contact(newName, EconNums, con.IsFav));
                            oos = new ObjectOutputStream(new FileOutputStream(file));
                            oos.writeObject(myContacts);
                            oos.close();
                            System.out.println("Contact Updated Succesfully");
                        } else
                            System.out.println("Contact Not Found");
                        break;
                    case "6":
                        readContacts();
                        System.out.println("Enter Contact Name");
                        String filterD = myObjs.nextLine();
                        li = myContacts.listIterator();
                        boolean foundD = false;
                        while (li.hasNext()) {
                            contact con2 = (contact) li.next();
                            if (con2.name.equals(filterD)) {
                                foundD = true;
                                System.out.println(con2);
                                li.remove();
                                break;
                            }
                        }
                        if (foundD) {
                            oos = new ObjectOutputStream(new FileOutputStream(file));
                            oos.writeObject(myContacts);
                            oos.close();
                            System.out.println("Contact Deleted Succesfully");
                        } else
                            System.out.println("Contact Not Found");
                        break;

                    case "7":
                        readContacts();
                        System.out.println("Enter Contact Name");
                        String filterV = myObjs.nextLine();
                        li = myContacts.listIterator();
                        boolean foundV = false;
                        contact con3 = null;
                        while (li.hasNext()) {
                            con3 = (contact) li.next();
                            if (con3.name.equals(filterV)) {
                                foundV = true;
                                System.out.println(con3);
                                li.remove();
                                break;
                            }

                        }

                        if (foundV) {
                            con3.IsFav = true;
                            myContacts.add(con3);
                            oos = new ObjectOutputStream(new FileOutputStream(file));
                            oos.writeObject(myContacts);
                            oos.close();
                            System.out.println("Contact Updated Succesfully");
                        } else
                            System.out.println("Contact Not Found");
                        break;


                }



            }
            else {
                System.out.println("Invalid. Please Select Valid Option");

            }
        }
            while (!option.equals("0")) ;
    }


    public static void displayContacts(ArrayList<contact> contacts) {
        ListIterator li = contacts.listIterator();
        while(li.hasNext())
            System.out.println(li.next());

    }
    public static boolean checkNameNumberDup(boolean forName, String nameNum) {
        ListIterator li2 = myContacts.listIterator();
        boolean exist = false;
        while (li2.hasNext()) {
            contact con = (contact) li2.next();
            if (forName && con.name.equals(nameNum)) {
                exist = true;
                break;
            }
            if (!forName) {
                for (int i = 0; i < con.numbers.size(); i++) {
                    if (con.numbers.get(i).number.equals(nameNum)){
                        exist = true;
                        break;
                    }

                }

            }
        }
        return exist;
    }
    public static boolean IsInputDup(String input, ArrayList<phoneNumber> nums){
        for (int j = 0; j < nums.size(); j++) {
            if (nums.get(j).number.equals(input)) {
                return true;

            }
        }
        return false;
    }
}
