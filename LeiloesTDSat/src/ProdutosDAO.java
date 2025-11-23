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

    public List<ProdutosDTO> Listar() {
        List<ProdutosDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        conectaDAO cn = new conectaDAO();
        if (cn.connectDB()) {
            Connection con = cn.conn;

            try (PreparedStatement prep = con.prepareStatement(sql); ResultSet rs = prep.executeQuery()) {

                while (rs.next()) {
                    ProdutosDTO p = new ProdutosDTO();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setValor(rs.getInt("valor"));
                    p.setStatus(rs.getString("status"));
                    lista.add(p);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Produtos encontrados: " + lista.size());
        return lista;
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
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

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

    public boolean venderProduto(int id) {
        String sql = "UPDATE produtos SET status = 'vendido' WHERE id = ?";

        conectaDAO cn = new conectaDAO();
        if (cn.connectDB()) {
            Connection con = cn.conn;
            try (PreparedStatement prep = con.prepareStatement(sql)) {

                prep.setInt(1, id);
                int linhas = prep.executeUpdate();

                return linhas > 0;

            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
