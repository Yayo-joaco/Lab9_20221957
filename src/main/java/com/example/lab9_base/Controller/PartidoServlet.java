package com.example.lab9_base.Controller;
import com.example.lab9_base.Dao.DaoPartidos;
import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Seleccion;
import com.example.lab9_base.Bean.Partido;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "PartidoServlet", urlPatterns = {"/PartidoServlet", ""})
public class PartidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        RequestDispatcher view;

        switch (action) {
            case "guardar":
                String fecha = request.getParameter("fecha");
                String numeroJornadaStr = request.getParameter("numeroJornada");
                String seleccionLocalStr = request.getParameter("seleccionLocal");
                String seleccionVisitanteStr = request.getParameter("seleccionVisitante");
                String arbitroStr = request.getParameter("arbitro");

                if (fecha == null || fecha.isEmpty() ||
                        numeroJornadaStr == null || numeroJornadaStr.isEmpty() ||
                        seleccionLocalStr == null || seleccionLocalStr.isEmpty() ||
                        seleccionVisitanteStr == null || seleccionVisitanteStr.isEmpty() ||
                        arbitroStr == null || arbitroStr.isEmpty()) {

                    response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=crear");
                    return;
                }

                try {
                    int numeroJornada = Integer.parseInt(numeroJornadaStr);
                    int seleccionLocalId = Integer.parseInt(seleccionLocalStr);
                    int seleccionVisitanteId = Integer.parseInt(seleccionVisitanteStr);
                    int arbitroId = Integer.parseInt(arbitroStr);

                    if (seleccionLocalId == seleccionVisitanteId) {
                        response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=crear");
                        return;
                    }

                    Partido partido = new Partido();
                    partido.setFecha(fecha);
                    partido.setNumeroJornada(numeroJornada);

                    Seleccion seleccionLocal = new Seleccion();
                    seleccionLocal.setIdSeleccion(seleccionLocalId);
                    partido.setSeleccionLocal(seleccionLocal);

                    Seleccion seleccionVisitante = new Seleccion();
                    seleccionVisitante.setIdSeleccion(seleccionVisitanteId);
                    partido.setSeleccionVisitante(seleccionVisitante);

                    Arbitro arbitro = new Arbitro();
                    arbitro.setIdArbitro(arbitroId);
                    partido.setArbitro(arbitro);

                    DaoPartidos daoPartidos = new DaoPartidos();
                    boolean creado = daoPartidos.crearPartido(partido);

                    if (creado) {
                        response.sendRedirect(request.getContextPath() + "/PartidoServlet");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=crear");
                    }

                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=crear");
                }
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        DaoPartidos daoPartidos = new DaoPartidos();
        switch (action) {
            case "lista":
                ArrayList<Partido> listaPartidos = daoPartidos.listaDePartidos();
                request.setAttribute("listaPartidos", listaPartidos);
                view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
                break;
            case "crear":
                ArrayList<Seleccion> selecciones = daoPartidos.listarSelecciones();
                ArrayList<Arbitro> arbitros = daoPartidos.listarArbitros();

                request.setAttribute("listaSelecciones", selecciones);
                request.setAttribute("listaArbitros", arbitros);

                view = request.getRequestDispatcher("partidos/form.jsp");
                view.forward(request, response);
                break;

        }

    }
}
