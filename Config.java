import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.load();
    //ดึงค่าจาก .env
    public static final String HOST = dotenv.get("DB_HOST");
    public static final String PORT = dotenv.get("DB_PORT");
    public static final String DB = dotenv.get("DB_NAME");
    public static final String USER = dotenv.get("DB_USER");
    public static final String PASS = dotenv.get("DB_PASS");

    public static String getURL(){
        return "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB;
    }
    
}