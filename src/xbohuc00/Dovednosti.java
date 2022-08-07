package xbohuc00;

public interface Dovednosti {

    default boolean isLeap(int year){
        boolean flag = false;
        if(year % 400 == 0)
        {
            flag = true;
        }
        else if (year % 100 == 0)
        {
            flag = false;
        }
        else if(year % 4 == 0)
        {
            flag = true;
        }
        else
        {
            flag = false;
        }
        return flag;
    }

    default String Zverokruh (int den, int mesiac)
    {

        if ((den>20 && mesiac ==1) || (den<=18 && mesiac == 2)) {
            return "Vodnar";
        }
        if ((den>19 && mesiac ==2) || (den<=20 && mesiac == 3)) {
            return "Ryba";
        }
        if ((den>21 && mesiac ==3) || (den<=20 && mesiac == 4)) {
            return "Baran";
        }
        if ((den>21 && mesiac ==4) || (den<=20 && mesiac == 5)) {
            return "Byk";
        }
        if ((den>21 && mesiac ==5) || (den<=20 && mesiac == 6)) {
            return "Blizenci";
        }
        if ((den>21 && mesiac ==6) || (den<=20 && mesiac == 7)) {
            return "Rak";
        }
        if ((den>21 && mesiac ==7) || (den<=20 && mesiac == 8)) {
            return "Lev";
        }
        if ((den>21 && mesiac ==8) || (den<=22 && mesiac == 9)) {
            return "Panna";
        }
        if ((den>23 && mesiac ==9) || (den<=20 && mesiac == 10)) {
            return "Vahy";
        }
        if ((den>21 && mesiac ==10) || (den<=22 && mesiac == 11)) {
            return "Skorpion";
        }
        if ((den>23 && mesiac ==11) || (den<=20 && mesiac == 12)) {
            return "Strelec";
        }
        if ((den>21 && mesiac ==12) || (den<=19 && mesiac == 1)) {
            return "Kozorozec";
        }
        return null;
    }




}
