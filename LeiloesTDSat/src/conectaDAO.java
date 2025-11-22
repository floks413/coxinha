
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {

    Connection conn;

    public String url = "jdbc:mysql://127.0.0.1:3306/banco";
    public String user = "root";
    public String senha = "root";

    public Boolean connectDB() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, senha);

            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            
            return false;
        }
    }
    
    public void Desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {

        }
    }
}
