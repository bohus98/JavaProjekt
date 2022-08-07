import java.sql.*;

public abstract class Databaza {

    static Connection conn = null;
    //Vytvorenie spojenia s databazou
    public static Connection connect() {

        try {

            if(conn == null){
                // parametre databazi
                String url = "jdbc:sqlite:StudentRegistration.db";
                // Naviazanie spojenia s databazou
                conn = DriverManager.getConnection(url);



            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //pridavanie humanitnych studentov
    public static long createHunamityStudent(String name , String surname , String dateOfBirth ,int month , int day,String signofzodiac){

        long studentId=0;
        try{
            long humanityId = 0;
            Connection connection = Databaza.connect();
            PreparedStatement ps = connection.prepareStatement("insert into HumanityStudents(signofzodiac) values(?)");
            ps.setString(1,signofzodiac);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                humanityId = rs.getLong(1);
            }


            ps = connection.prepareStatement("insert into Student(name,surname,dob,humanityStudentId,month,day) values(?,?,?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,surname);
            ps.setString(3,dateOfBirth);
            ps.setLong(4,humanityId);
            ps.setLong(5,month);
            ps.setLong(6,day);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                studentId = rs.getLong(1);
            }

        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        return studentId;
    }


    //vyberiem znamku z databaze, nasledne k nej pridam novu a vydelim 2, tak dostanem priemernu znamku studenta
    public static void addStudentMarks(int id , double marks){
        try{
            Connection connection = Databaza.connect();
            String select = "select marks from Student where ID=?";
            PreparedStatement ps = connection.prepareStatement(select);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            double marksDb =0;
            while(rs.next()){
                marksDb = rs.getInt(1);
            }


            double finalMarks;
            if(marksDb == 0)
                finalMarks = marks;
            else
                finalMarks = (marks+marksDb)/2;

            String sql = "update Student set marks=? where ID=?";
            ps = connection.prepareStatement(sql);
            ps.setDouble(1,finalMarks);
            ps.setInt(2,id);

            ps.executeUpdate();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    //Priemer Humanitneho a Technickeho odboru
    public static void averageOfStudents(){

        try{
            String sql = "select avg(marks) FROM Student where technicalStudentId is not null";
            Connection con = Databaza.connect();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                double avgMarksTech = rs.getDouble(1);
                System.out.println("Priemer Technickeho odboru "+avgMarksTech);
            }


            String sql2="select avg(marks) FROM Student where humanityStudentId is not null";
            ps = con.prepareStatement(sql2);
            rs = ps.executeQuery();

            while(rs.next()){
                double avgMarksTech = rs.getDouble(1);
                System.out.println("Priemer Humanitneho odboru "+avgMarksTech);
            }


        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    //Vypis studentov podla priezviska
    public static void displayAllStudents(){
        try {
            Connection connection = Databaza.connect();
            String sql = "select * from Student order by surname ASC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surName = rs.getString(3);



                System.out.println("ID: "+id+" , Meno: "+name+" , Priezvisko: "+surName);
            }
        }catch(Exception ec) {
            System.err.println(ec.getMessage());
        }
    }

    //Ukazanie informacii na zaklade studentovho ID
    public static void getStudentById(int id){
        try{
            Connection connection = Databaza.connect();
            String sql = "select * from Student where ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString(2);
                String surName = rs.getString(3);
                String dob = rs.getString(4);
                String marks = rs.getString(8);
                String month = rs.getString(9);
                String day = rs.getString(10);

                System.out.println("Meno: "+name+" , Priezvisko: "+surName+" , Rok: "+dob+", Mesiac: "+month+" , Den: "+day+" , Priemer: "+marks);
            }
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    //v pripade vymazania studenta z databaze podla ID
    public static void dismissStudent(int id){
        try{
            Connection connection = Databaza.connect();
            String sql = "delete from Student where ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);

            ps.executeUpdate();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    //vlozenie kombinovanych studentov do databaze
    public static long createCombinedStudents(String name , String surname , String dateOfBirth ,int month , int day, String signofzodiac , Boolean isLeapYear){

        long studentId=0;
        try{
            long combineId = 0;
            Connection connection = Databaza.connect();
            PreparedStatement ps = connection.prepareStatement("insert into CombinedStudents(signofzodiac,isLeap) values(?,?)");
            ps.setString(1,signofzodiac);
            ps.setBoolean(2,isLeapYear);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                combineId = rs.getLong(1);
            }


            ps = connection.prepareStatement("insert into Student(name,surname,dob,combinedStudentId,month,day) values(?,?,?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,surname);
            ps.setString(3,dateOfBirth);
            ps.setLong(4,combineId);
            ps.setLong(5,month);
            ps.setLong(6,day);

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                studentId = rs.getLong(1);
            }

        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        return studentId;
    }

    //vlozenie technickych studentov do databaze
    public static long createTechnicalStudent(String name , String surname , int dateOfBirth , int month , int day, boolean isLeap){

        long studentId=0;
        try{
            long techId = 0;
            Connection connection = Databaza.connect();
            PreparedStatement ps = connection.prepareStatement("insert into TechnicalStudents(isLeap) values(?)");
            ps.setBoolean(1,isLeap);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                techId = rs.getLong(1);
            }


            ps = connection.prepareStatement("insert into Student(name,surname,dob,technicalStudentId,month,day) values(?,?,?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,surname);
            ps.setString(3,String.valueOf(dateOfBirth));
            ps.setLong(4,techId);
            ps.setLong(5,month);
            ps.setLong(6,day);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                studentId = rs.getLong(1);
            }

        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        return studentId;
    }

    //Tato metoda ukaze studentov podla odboru
    public static void printAllStudents() {

        int techStudents = 0;
        int humStudents = 0;
        int combStudents = 0;
        try{
            String sql = "SELECT st.ID,name,\n" +
                    "       surname,\n" +
                    "       dob,\n" +
                    "       marks,\n" +
                    "       month,\n" +
                    "       day,\n" +
                    "       ts.isLeap\n" +
                    "  FROM Student st, TechnicalStudents ts where st.technicalStudentId=ts.ID;\n";
            Connection con = Databaza.connect();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("Technicky Studenti");
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surName = rs.getString(3);
                String dob = rs.getString(4);
                String marks = rs.getString(5);
                int month = rs.getInt(6);
                int day = rs.getInt(7);
                Boolean isLeapYear = rs.getBoolean(8);

                System.out.println("ID: "+id+", Meno: "+name+" , Priezvisko: "+surName+" , Rok: "+dob+" , Mesiac: "+month+" , Den: "+day+",Prestupny: "+isLeapYear+" , Priemer: "+marks);

                techStudents++;
            }


            System.out.println("");

            String sql2="SELECT st.ID,name,\n" +
                    "       surname,\n" +
                    "       dob,\n" +
                    "       marks,\n" +
                    "       month,\n" +
                    "       day,\n" +
                    "       hs.signofzodiac\n" +
                    "  FROM Student st, HumanityStudents hs where st.humanityStudentId=hs.ID;\n";
            ps = con.prepareStatement(sql2);
            rs = ps.executeQuery();


            System.out.println("Humanitny Studenti");
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surName = rs.getString(3);
                String dob = rs.getString(4);
                String marks = rs.getString(5);
                int month = rs.getInt(6);
                int day = rs.getInt(7);
                String sign = rs.getString(8);

                System.out.println("ID: "+id+" , Meno: "+name+" , Priezvisko: "+surName+" , Rok: "+dob+" , Mesiac: "+month+" , Den: "+day+", Znamenie: "+sign+" , Priemer: "+marks);
                humStudents++;
            }


            System.out.println("");

            String sql3="SELECT st.ID,name,\n" +
                    "       surname,\n" +
                    "       dob,\n" +
                    "       marks,\n" +
                    "       month,\n" +
                    "       day,\n" +
                    "       cs.isLeap,\n" +
                    "       cs.signofzodiac\n" +
                    "  FROM Student st, CombinedStudents cs where st.combinedStudentId = cs.ID;\n";

            ps = con.prepareStatement(sql3);
            rs = ps.executeQuery();


            System.out.println("Kombinovany Studenti");
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surName = rs.getString(3);
                String dob = rs.getString(4);
                String marks = rs.getString(5);
                int month = rs.getInt(6);
                int day = rs.getInt(7);
                boolean isLeap = rs.getBoolean(8);
                String sign = rs.getString(9);

                System.out.println("ID: "+id+" , Meno: "+name+" , Priezvisko: "+surName+" , Rok: "+dob+" , Mesiac: "+month+" , Den: "+day+", Prestupny: "+isLeap+ " , Znamenie: "+sign+" , Priemer: "+marks);
                combStudents++;
            }

            System.out.println();
            System.out.println();
            System.out.println();

            int totalCount = techStudents+humStudents+combStudents;
            System.out.println("Studenti");
            System.out.println("Celkovy pocet studentov : "+totalCount);
            System.out.println("Pocet Technickych studentov : "+techStudents);
            System.out.println("Pocet Humanitnych studentov : "+humStudents);
            System.out.println("Pocet Kombinovanych studentov : "+combStudents);


        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    public static void runSkill(int skillStudId) {
        try{
            String sql = "SELECT combinedStudentId,technicalStudentId,humanityStudentId FROM Student where ID=?";
            Connection con = Databaza.connect();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,skillStudId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int combId = rs.getInt(1);
                int techId = rs.getInt(2);
                int humId = rs.getInt(3);

                if(combId != 0){
                    String combSql = "select signofzodiac,isLeap from CombinedStudents where ID=?";

                    ps=con.prepareStatement(combSql);
                    ps.setInt(1,combId);
                    ResultSet combRes = ps.executeQuery();
                    while(combRes.next()){
                        String signOfZodiac = combRes.getString(1);
                        Boolean isLeap = combRes.getBoolean(2);

                        System.out.println("Znamenie je "+signOfZodiac+" Prestupny "+isLeap);
                    }
                }
                if(techId != 0){
                    String techSql = "select isLeap from TechnicalStudents where ID=?";
                    ps = con.prepareStatement(techSql);
                    ps.setInt(1,techId);

                    ResultSet techRes = ps.executeQuery();
                    while(techRes.next()){
                        Boolean isLeap = techRes.getBoolean(1);
                        System.out.println("Prestupny "+isLeap);

                    }
                }
                if(humId != 0){
                    String humSql = "select signofzodiac from HumanityStudents where ID=?";
                    ps = con.prepareStatement(humSql);
                    ps.setInt(1,humId);

                    ResultSet humRes = ps.executeQuery();
                    while(humRes.next()){
                      String zodiacSign = humRes.getString(1);
                        System.out.println("Znamenie je "+zodiacSign);

                    }
                }
            }


        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}