<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mon Panier</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        table { border-collapse: collapse; width: 70%; margin-top: 15px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        .btn-delete { color: #d9534f; text-decoration: none; font-weight: bold; }
        .btn-delete:hover { text-decoration: underline; }
        .btn-back { display: inline-block; margin-bottom: 10px; color: #0275d8; text-decoration: none; }
    </style>
</head>
<body>
<h1>Mon Panier</h1>
<a href="${pageContext.request.contextPath}/vitrine?action=lister" class="btn-back">&larr; Retour à la vitrine</a>
<hr>

<table>
    <thead>
    <tr>
        <th>Produit</th>
        <th>Quantité</th>
        <th>Prix Unitaire</th>
        <th>Sous-total</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="total" value="0" />
    <c:forEach var="ligne" items="${lignes}">
        <c:set var="subtotal" value="${ligne.produit.prix * ligne.quantite}" />
        <c:set var="total" value="${total + subtotal}" />
        <tr>
            <td>${ligne.produit.designation}</td>
            <td>${ligne.quantite}</td>
            <td>${ligne.produit.prix} MAD</td>
            <td>${subtotal} MAD</td>
            <td>
                <a href="${pageContext.request.contextPath}/panier?action=supprimer&idLigne=${ligne.id}" class="btn-delete">
                    Supprimer
                </a>
            </td>
        </tr>
    </c:forEach>

    <c:if test="${empty lignes}">
        <tr>
            <td colspan="5" style="text-align: center; color: #777;">Votre panier est vide.</td>
        </tr>
    </c:if>
    </tbody>
</table>

<c:if test="${not empty lignes}">
    <h3>Total : <strong>${total} MAD</strong></h3>
</c:if>

</body>
</html>
