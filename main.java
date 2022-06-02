public class main {
    public static void main(String[] args){

        if(args.length > 0){
            System.out.println("Have Arguments");
            for(String arg:args) System.out.println("One of My args: " + arg);
        }
    }
}
