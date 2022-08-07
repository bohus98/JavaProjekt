

import xbohuc00.ZlaHodnotaException;
import xbohuc00.Dovednosti;

import java.util.Scanner;

public class Main implements Dovednosti {

    //jednoduche hlavné menu
    public void printMainMenu(){
        System.out.println();
        System.out.println("Prosim vyberte jednu moznost");
        System.out.println("1.  Pridat studenta");
        System.out.println("2.  Pridat studentovi znamku podla ID");
        System.out.println("3.  Vylucit studenta podla ID");
        System.out.println("4.  Najst studenta podla ID");
        System.out.println("5.  Abecedne zoradeny zoznam studentov");
        System.out.println("6.  Priemer Humanitneho a Technickeho odboru");
        System.out.println("7.  Pocet studentov v jednotlivych skupinach");
        System.out.println("8.  Spustit schopnost studenta podla ID");
    }

    //výber studijnej skupiny do ktorej chceme studenta pridat
    public void chooseGroup(){
        System.out.println("");
        System.out.println("1.  Technicky student");
        System.out.println("2.  Humanitny student");
        System.out.println("3.  Kombinovany student");

        System.out.println("");

    }

//Funkcie hlavneho menu
    public void execute(){

        while(true) {
            printMainMenu();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch(input){
                case "1":
                    System.out.println("Zadajte studentove meno");
                    String name = scanner.nextLine();

                    System.out.println("Zadajte studentove priezvisko");
                    String surname = scanner.nextLine();


                    System.out.println("Zadajte datum narodenia DD MM RRRR");
                    boolean isValidDate = false;
                    int date = 0;
                    //kontrola ci je den v spravnom tvare (medzi 1-31)
                    while(!isValidDate){
                        try {
                            date = scanner.nextInt();
                            if (date > 0 && date <= 31) {
                                isValidDate = true;
                            } else {
                                throw new ZlaHodnotaException("Datum by mal byt v rozpati 1-31");
                            }
                        }catch (ZlaHodnotaException ex){
                            System.err.println(ex.getMessage());
                        }
                    }

                    int month = 0;
                    boolean isValidMonth = false;
                    //Kontrola ci je mesiac v spravnom tvare (1-12)
                    while (!isValidMonth){
                        try{
                            month = scanner.nextInt();
                            if(month>1 && month<=12){
                                isValidMonth = true;
                            }else{
                                throw new ZlaHodnotaException("Mesiac by mal byt v rozpati 1-12");
                            }
                        }catch (ZlaHodnotaException ex){
                            System.err.println(ex.getMessage());
                        }
                    }

                    int year = scanner.nextInt();

                    chooseGroup();
                    String val = scanner.next();
                    if(val.equals("1")){

                        boolean isLeapYear = this.isLeap(year);
                        long id = Databaza.createTechnicalStudent(name, surname, year, month , date, isLeapYear);
                        System.out.println("Studentove id je " + id);


                    }

                    if(val.equals("2")){
                        String zodiac = this.Zverokruh(date,month);
                    long id = Databaza.createHunamityStudent(name , surname , String.valueOf(year), month , date , zodiac);
                    System.out.println("Studentove id je "+id);
                    }

                    if(val.equals("3")){

                        String zodiac = this.Zverokruh(date,month);
                        boolean isLeapYear = this.isLeap(year);

                        long id = Databaza.createCombinedStudents(name , surname , String.valueOf(year), month , date , zodiac,isLeapYear);
                        System.out.println("Studentove id je "+id);

                 }
                 break;

                case "2":

                    Databaza.displayAllStudents();
                    System.out.println(" ");

                    boolean validMarks = false;
                    String marks = null;
                    while(!validMarks){

                        try {
                            System.out.println();
                            System.out.println("Zadajte znamku studenta");
                             marks = scanner.nextLine();
                            //Kontrola ci je znamka v spravnom tvare (1-5)
                            if (Integer.parseInt(marks) > 0 && Integer.parseInt(marks) <= 5) {
                                validMarks = true;
                            } else {
                                throw new ZlaHodnotaException("Znamky by mali byt v rozpati 1-5");
                            }
                        }catch (ZlaHodnotaException ex){
                            System.err.println(ex.getMessage());
                        }
                    }

                    System.out.println("Zadajte Studentove ID");
                    String id = scanner.nextLine();

                    Databaza.addStudentMarks(Integer.parseInt(id), Double.parseDouble(marks));
                    System.out.println("Znamka bola uspesne pridana");


                    break;

                case "3":

                    Databaza.displayAllStudents();
                    System.out.println("");

                    System.out.println("Zadajte ID studenta, ktoreho chcete vylucit");
                    String stdId = scanner.nextLine();

                    Databaza.dismissStudent(Integer.parseInt(stdId));
                    System.out.println("Student bol vyluceny");

                    break;

                case "4":
                    System.out.println("Zadajte Studentove ID");
                    String findStdId = scanner.nextLine();

                    Databaza.getStudentById(Integer.parseInt(findStdId));
                    System.out.println("");

                    break;

                case "5":


                    Databaza.displayAllStudents();
                    System.out.println(" ");

                    break;

                case "6":


                    Databaza.averageOfStudents();
                    System.out.println(" ");

                    break;

                case "7":


                    Databaza.printAllStudents();
                    System.out.println("");

                    break;

                case "8":
                    System.out.println("Zadajte Studentove ID");
                    String skillStudId = scanner.nextLine();

                    Databaza.runSkill(Integer.parseInt(skillStudId));
                    System.out.println("");

                    break;

                    default:
                    System.exit(-1);
            }
        }
    }


    public static void main(String[] args){
        Main main = new Main();
        main.execute();

    }
}
