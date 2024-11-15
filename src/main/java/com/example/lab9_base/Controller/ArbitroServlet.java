package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Dao.DaoArbitros;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "ArbitroServlet", urlPatterns = {"/ArbitroServlet"})
public class ArbitroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        DaoArbitros daoArbitros = new DaoArbitros();
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");

        switch (action) {

            case "buscar":
                String searchType = request.getParameter("searchType");
                String searchTerm = request.getParameter("searchTerm");
                ArrayList<Arbitro> resultados = new ArrayList<>();

                if (searchType != null && searchTerm != null && !searchTerm.isEmpty()) {
                    if (searchType.equals("nombre")) {
                        resultados = daoArbitros.busquedaNombre(searchTerm);
                    } else if (searchType.equals("pais")) {
                        resultados = daoArbitros.busquedaPais(searchTerm);
                    }
                }

                request.setAttribute("listaArbitros", resultados);
                RequestDispatcher view = request.getRequestDispatcher("/arbitros/list.jsp");
                view.forward(request, response);
                break;

            case "guardar":
                String nombre = request.getParameter("nombre");
                String pais = request.getParameter("pais");

                if (nombre != null && !nombre.isEmpty() && pais != null && !pais.isEmpty()) {
                    if (!daoArbitros.existeArbitro(nombre)) {
                        Arbitro nuevoArbitro = new Arbitro();
                        nuevoArbitro.setNombre(nombre);
                        nuevoArbitro.setPais(pais);
                        daoArbitros.crearArbitro(nuevoArbitro);
                        response.sendRedirect(request.getContextPath() + "/ArbitroServlet");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/ArbitroServlet?action=crear");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/ArbitroServlet?action=crear");
                }
                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        DaoArbitros daoArbitros = new DaoArbitros();
        ArrayList<String> paises = new ArrayList<>();
        paises.add("Peru");
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Paraguay");
        paises.add("Uruguay");
        paises.add("Colombia");
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");

        switch (action) {
            case "lista":
                ArrayList<Arbitro> listaArbitros = daoArbitros.listarArbitros();
                request.setAttribute("listaArbitros", listaArbitros);
                view = request.getRequestDispatcher("/arbitros/list.jsp");
                view.forward(request, response);
                break;
            case "crear":
                request.setAttribute("paises", paises);
                view = request.getRequestDispatcher("/arbitros/form.jsp");
                view.forward(request, response);
                break;

            case "borrar":
                // d. Borrar árbitro
                String arbitroId = request.getParameter("id");
                if (arbitroId != null && !arbitroId.isEmpty()) {
                    int id = Integer.parseInt(arbitroId);
                    Arbitro arbitro = daoArbitros.buscarArbitro(id);
                    if (arbitro != null) {
                        daoArbitros.borrarArbitro(id);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/ArbitroServlet");
                break;

        }
    }
}
