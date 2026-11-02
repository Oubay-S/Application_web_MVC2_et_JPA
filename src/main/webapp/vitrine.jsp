<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vitrine e-Commerce</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background-color: #f9f9f9; }
        .header-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .btn-panier { padding: 8px 16px; background-color: #0275d8; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .produits-grid { display: flex; flex-wrap: wrap; gap: 20px; }
        .produit { background: white; border: 1px solid #ddd; border-radius: 8px; padding: 15px; width: 280px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); }
        .produit h3 { margin-top: 0; color: #333; }
        .produit p { color: #666; font-size: 14px; min-height: 40px; }
        .prix { color: #28a745; font-size: 18px; font-weight: bold; margin-bottom: 15px; }
        .btn-ajouter { display: block; text-align: center; padding: 8px 12px; background-color: #5cb85c; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .btn-ajouter:hover { background-color: #4cae4c; }
    </style>
</head>
<body>
<div class="header-bar">
    <h1>Vitrine e-Commerce</h1>
    <a href="${pageContext.request.contextPath}/panier?action=afficher" class="btn-panier">&#128722; Voir mon panier</a>
</div>
<hr>

<div class="produits-grid">
    <c:forEach var="p" items="${produits}">
        <div class="produit">
            <h3>${p.designation}</h3>
            <p>${p.description}</p>
            <div class="prix">${p.prix} MAD</div>
            <a href="${pageContext.request.contextPath}/panier?action=ajouter&idProduit=${p.id}" class="btn-ajouter">
                Ajouter au panier
            </a>
        </div>
    </c:forEach>
</div>

<c:if test="${empty produits}">
    <p>Aucun produit disponible pour le moment.</p>
</c:if>

<script>
    $(document).ready(function(){
        $(".btn-ajouter").click(function(){
            $(this).text("Ajouté !").css("background-color", "#28a745");
        });
    });
</script>
</body>
</html>
