/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    PreparedStatement prep;
    ResultSet resultset;

    private static final List<ProdutosDTO> listagem = new ArrayList<>();

    public static List<ProdutosDTO> Listar() {
        return listagem;
    }

    public static void Adicionar(ProdutosDTO nome) {
        listagem.add(nome);
    }

    public List<ProdutosDTO> listarProdutos() {

        return listagem;
    }

    public void cadastrar(ProdutosDTO p) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        conectaDAO cn = new conectaDAO();
        if (cn.connectDB()) { 
            Connection con = cn.conn;
        
        try (PreparedStatement prep = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            prep.setString(1, p.getNome());
            prep.setInt(2, p.getValor());
            prep.setString(3, p.getStatus());

            prep.executeUpdate();
            con.commit();
            JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        } else {
            System.out.println("Falha ao conectar ao banco!");
        }
    }

}
