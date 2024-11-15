<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.lab9_base.Bean.Seleccion" %>
<%@ page import="com.example.lab9_base.Bean.Arbitro" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'/>
    <title>LAB 9</title>
</head>

<body>
<jsp:include page="/includes/navbar.jsp"/>
<div class='container'>
    <div class="row mb-4">
        <div class="col"></div>
        <div class="col-md-6">
            <h1 class='mb-3'>Crear un Partido de Clasificatorias</h1>
            <form method="POST" action="<%=request.getContextPath()%>/PartidoServlet?action=guardar">
                <div class="form-group">
                    <label>Jornada</label>
                    <input type="number" class="form-control" name="numeroJornada" required>
                </div>
                <div class="form-group">
                    <label>Fecha</label>
                    <input class="form-control" name="fecha" type="date" required/>
                </div>
                <div class="form-group">
                    <label>Selección local</label>
                    <select name="seleccionLocal" class="form-control" required>
                        <option value="">Seleccione una selección</option>
                        <% for(Seleccion seleccion : (ArrayList<Seleccion>) request.getAttribute("listaSelecciones")) { %>
                        <option value="<%=seleccion.getIdSeleccion()%>">
                            <%=seleccion.getNombre()%>
                        </option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label>Selección Visitante</label>
                    <select name="seleccionVisitante" class="form-control" required>
                        <option value="">Seleccione una selección</option>
                        <% for(Seleccion seleccion : (ArrayList<Seleccion>) request.getAttribute("listaSelecciones")) { %>
                        <option value="<%=seleccion.getIdSeleccion()%>">
                            <%=seleccion.getNombre()%>
                        </option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label>Árbitro</label>
                    <select name="arbitro" class="form-control" required>
                        <option value="">Seleccione un árbitro</option>
                        <% for(Arbitro arbitro : (ArrayList<Arbitro>) request.getAttribute("listaArbitros")) { %>
                        <option value="<%=arbitro.getIdArbitro()%>">
                            <%=arbitro.getNombre()%>
                        </option>
                        <% } %>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Guardar</button>
                <a href="<%=request.getContextPath()%>/PartidoServlet" class="btn btn-danger">Cancelar</a>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>