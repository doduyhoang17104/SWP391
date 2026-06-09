/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.RoomDAO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import model.Room;
import model.RoomType;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
/**
 *
 * @author admin
 */
public class createRoom extends HttpServlet {

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
            out.println("<title>Servlet createRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet createRoom at " + request.getContextPath() + "</h1>");
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
        List<RoomType> roomTypes = RoomDAO.getAllRoomTypes();
        request.setAttribute("roomTypes", roomTypes);
        request.getRequestDispatcher("views/dashboard/create-room.jsp").forward(request, response);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String roomNumberStr = request.getParameter("roomNumber");
        String roomTypeIdStr = request.getParameter("roomTypeId");
        String capacityStr = request.getParameter("capacity");
        String status = request.getParameter("status");
        String size = request.getParameter("size");
        String bedStr = request.getParameter("bed");

        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomNumberStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Số phòng không hợp lệ");
            request.setAttribute("roomTypes", RoomDAO.getAllRoomTypes());
            setFormAttributes(request);
            request.getRequestDispatcher("views/dashboard/create-room.jsp").forward(request, response);
            return;
        }

        if (RoomDAO.isRoomNumberExists(roomNumber)) {
            request.setAttribute("error", "Số phòng đã tồn tại, vui lòng chọn số khác");
            request.setAttribute("roomTypes", RoomDAO.getAllRoomTypes());
            setFormAttributes(request);
            request.getRequestDispatcher("views/dashboard/create-room.jsp").forward(request, response);
            return;
        }

        int bed;
        try {
            bed = Integer.parseInt(bedStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Số lượng giường không hợp lệ");
            request.setAttribute("roomTypes", RoomDAO.getAllRoomTypes());
            request.getRequestDispatcher("views/dashboard/create-room.jsp").forward(request, response);
            return;
        }

        List<String> imageUrls = new ArrayList<>();
        try {
            Collection<Part> parts = request.getParts();

            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            for (Part part : parts) {
                if ("images".equals(part.getName()) || "image".equals(part.getName())) {
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    if (fileName != null && !fileName.isEmpty() && part.getSize() > 0) {
                        String newFileName = System.currentTimeMillis() + "_" + fileName;
                        String filePath = uploadPath + File.separator + newFileName;

                        part.write(filePath);

                        imageUrls.add("uploads/" + newFileName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RoomType roomType = new RoomType();
        try {
            roomType.setRoomTypeId(Integer.parseInt(roomTypeIdStr));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Loại phòng không hợp lệ");
            request.setAttribute("roomTypes", RoomDAO.getAllRoomTypes());
            setFormAttributes(request);
            request.getRequestDispatcher("views/dashboard/create-room.jsp").forward(request, response);
            return;
        }

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType);
        room.setCapacity(capacityStr);
        room.setStatus(status);
        room.setSize(size);
        room.setBed(bed);
        room.setBooked(false);

        Timestamp now = new Timestamp(System.currentTimeMillis());
        room.setCreatedAt(now);
        room.setUpdatedAt(now);
        room.setImageUrls(imageUrls);

        boolean created = RoomDAO.createRoom(room);

        if (created) {
            response.sendRedirect("listRoom");
        } else {
            request.setAttribute("error", "Tạo phòng không thành công");
            request.setAttribute("roomTypes", RoomDAO.getAllRoomTypes());
            setFormAttributes(request);
            request.getRequestDispatcher("views/dashboard/create-room.jsp").forward(request, response);
        }
    }

    private void setFormAttributes(HttpServletRequest request) {
        request.setAttribute("roomNumber", request.getParameter("roomNumber"));
        request.setAttribute("roomTypeId", request.getParameter("roomTypeId"));
        request.setAttribute("capacity", request.getParameter("capacity"));
        request.setAttribute("size", request.getParameter("size"));
        request.setAttribute("bed", request.getParameter("bed"));
        request.setAttribute("status", request.getParameter("status"));
        request.setAttribute("roomTypes", RoomDAO.getAllRoomTypes());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
