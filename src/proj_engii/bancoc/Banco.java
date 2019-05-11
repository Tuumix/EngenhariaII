package proj_engii.bancoc;

public class Banco {

    static public Conexao con;

    private Banco() {
    }

    public static boolean conectar() {
        if (getCon() == null) {
            con = new Conexao();
            return con.conectar("jdbc:postgresql://localhost/", "Banco", "postgres", "postgres123");//conexão
        }
        return true;
    }

    public static Conexao getCon() {
        return con;
    }

    public static void setCon(Conexao con) {
        Banco.con = con;
    }
}
