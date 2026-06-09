/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import model.Room;
import model.RoomType;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Collection;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 20 // 20MB
)
/**
 *
 * @author admin
 */
public class editRoom extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet editRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editRoom at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomIdStr = request.getParameter("id");

        if (roomIdStr == null || roomIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu room ID");
            return;
        }

        int roomId;
        try {
            roomId = Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Room ID không hợp lệ");
            return;
        }

        Room room = RoomDAO.viewRoom(roomId);
        if (room == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Phòng không tồn tại");
            return;
        }

        List<RoomType> roomTypes = RoomDAO.getAllRoomTypes();

        request.setAttribute("room", room);
        request.setAttribute("roomTypes", roomTypes);
        request.getRequestDispatcher("views/dashboard/edit-room.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomIdStr = request.getParameter("roomId");
        String roomNumberStr = request.getParameter("roomNumber");
        String roomTypeIdStr = request.getParameter("roomTypeId");
        String capacity = request.getParameter("capacity");
        String status = request.getParameter("status");
        String size = request.getParameter("size");  // Thêm size
        String bedStr = request.getParameter("bed"); // Thêm bed (int)
        boolean booked = request.getParameter("booked") != null;

        int roomId = 0, roomNumber = 0, roomTypeId = 0, bed = 0;

        Room room = new Room();

        try {
            if (roomIdStr == null || roomNumberStr == null || roomTypeIdStr == null) {
                throw new IllegalArgumentException("Thiếu dữ liệu bắt buộc.");
            }

            roomId = Integer.parseInt(roomIdStr);
            roomNumber = Integer.parseInt(roomNumberStr);
            roomTypeId = Integer.parseInt(roomTypeIdStr);

            if (bedStr != null && !bedStr.isEmpty()) {
                try {
                    bed = Integer.parseInt(bedStr);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Giá trị bed không hợp lệ.");
                    request.setAttribute("room", room);
                    request.setAttribute("roomTypes", RoomDAO.getAllRoomTypes());
                    request.getRequestDispatcher("edit-room.jsp").forward(request, response);
                    return;
                }
            }

            // Thiết lập thuộc tính Room
            room.setRoomId(roomId);
            room.setRoomNumber(roomNumber);
            room.setCapacity(capacity);
            room.setStatus(status);
            room.setBooked(booked);
            room.setSize(size);  // Gán size
            room.setBed(bed);    // Gán bed
            room.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            RoomType rt = new RoomType();
            rt.setRoomTypeId(roomTypeId);
            room.setRoomType(rt);

            // Upload ảnh
            List<String> imageUrls = uploadImages(request);

// Nếu không upload ảnh mới => giữ ảnh cũ
            if (imageUrls == null || imageUrls.isEmpty()) {
                Room oldRoom = RoomDAO.viewRoom(roomId); // lấy lại ảnh cũ từ DB
                if (oldRoom != null) {
                    imageUrls = oldRoom.getImageUrls();
                }
            }

            room.setImageUrls(imageUrls);

            // Gọi DAO để cập nhật
            boolean success = RoomDAO.editRoom(roomId, room);

            if (success) {
                response.sendRedirect("listRoom");
            } else {
                request.setAttribute("error", "Cập nhật thất bại.");
                request.setAttribute("room", room);
                request.setAttribute("roomTypes", RoomDAO.getAllRoomTypes());
                request.getRequestDispatcher("views/dashboard/edit-room.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Số không hợp lệ: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi không xác định: " + e.getMessage());
        }

        // Nếu có lỗi thì forward lại trang edit với dữ liệu đã nhập
        if (request.getAttribute("error") != null) {
            room.setRoomId(roomId);
            room.setRoomNumber(roomNumber);
            room.setCapacity(capacity);
            room.setStatus(status);
            room.setBooked(booked);
            room.setSize(size);
            room.setBed(bed);
            RoomType rt = new RoomType();
            rt.setRoomTypeId(roomTypeId);
            room.setRoomType(rt);

            request.setAttribute("room", room);
            request.setAttribute("roomTypes", RoomDAO.getAllRoomTypes());
            request.getRequestDispatcher("views/dashboard/edit-room.jsp").forward(request, response);
        }
    }

    private List<String> uploadImages(HttpServletRequest request) throws IOException, ServletException {
        List<String> imageUrls = new ArrayList<>();

        String uploadDir = getServletContext().getRealPath("/uploads");
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (Part part : request.getParts()) {
            if (part.getName().equals("images") && part.getSize() > 0) {
                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String filePath = uploadDir + File.separator + fileName;

                part.write(filePath);

                String imageUrl = request.getContextPath() + "/uploads/" + fileName;
                imageUrls.add(imageUrl);
            }
        }

        return imageUrls;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
