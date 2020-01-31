public class Coder extends Jobb {

    private String  programming;
    private boolean front ;

    protected String getfront(){
        if(front){
        return "frontend";}
        else
            return "backend";
    }
}
