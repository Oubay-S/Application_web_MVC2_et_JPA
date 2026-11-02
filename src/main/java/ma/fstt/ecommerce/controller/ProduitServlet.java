package ma.fstt.ecommerce.controller;

import ma.fstt.ecommerce.model.Produit;
import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/vitrine")
public class ProduitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Action par défaut : afficher la vitrine
        if (action == null || "lister".equals(action)) {
            EntityManager em = JPAUtil.getEntityManager();
            try {
                // Requête JPA (JPQL) pour récupérer tous les produits
                List<Produit> produits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();

                // Envoi des données à la vue
                request.setAttribute("produits", produits);
                request.getRequestDispatcher("/vitrine.jsp").forward(request, response);
            } finally {
                em.close();
            }
        }
    }
}
