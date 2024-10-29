/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Thao_Tac_CSDL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/edit_orders")
public class edit_orders extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int courseId = Integer.parseInt(request.getParameter("course_id"));
        double totalPrice = Double.parseDouble(request.getParameter("total_price"));

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Kết nối đến cơ sở dữ liệu
            DBconnect db = new DBconnect();
            conn = db.KetNoi();

            // Câu lệnh SQL để cập nhật thông tin đơn hàng
            String sqlUpdate = "UPDATE orders SET status = ?, user_id = ?, course_id = ?, total_price = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sqlUpdate);
            pstmt.setString(1, status);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, courseId);
            pstmt.setDouble(4, totalPrice);
            pstmt.setInt(5, id);

            // Thực hiện cập nhật
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("Orders_Management.jsp"); // Điều hướng về trang quản lý đơn hàng
            } else {
                response.getWriter().println("Không tìm thấy đơn hàng với ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }
}