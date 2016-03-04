public class Hugo {

    public static String produceHugo(int i) {
        return "Hugo";
    }

    public static void main (String args[]) {
        for (int i = 0; i < 5; i++) {
            System.out.println(Hugo.produceHugo(i));   
        }
    }
    
}
