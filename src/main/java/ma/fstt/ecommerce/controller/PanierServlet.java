package ma.fstt.ecommerce.controller;

import ma.fstt.ecommerce.model.LignePanier;
import ma.fstt.ecommerce.model.Panier;
import ma.fstt.ecommerce.model.Produit;
import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/panier")
public class PanierServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "afficher";
        }

        // On stocke l'ID du panier dans la session HTTP
        Long panierId = (Long) request.getSession().getAttribute("panierId");

        EntityManager em = JPAUtil.getEntityManager();
        try {
            Panier panier = null;
            if (panierId != null) {
                panier = em.find(Panier.class, panierId);
            }

            if (panier == null) {
                panier = new Panier();
                panier.setDateCreation(new Date());

                em.getTransaction().begin();
                em.persist(panier); // Sauvegarde le nouveau panier en BDD
                em.getTransaction().commit();

                panierId = panier.getId();
                request.getSession().setAttribute("panierId", panierId);
            }

            if ("afficher".equals(action)) {
                // Charger les lignes du panier depuis la BDD
                List<LignePanier> lignes = em.createQuery(
                        "SELECT l FROM LignePanier l WHERE l.panier.id = :panierId", LignePanier.class)
                        .setParameter("panierId", panierId)
                        .getResultList();

                request.setAttribute("lignes", lignes);
                request.getRequestDispatcher("/panier.jsp").forward(request, response);

            } else if ("ajouter".equals(action)) {
                String idParam = request.getParameter("idProduit");
                if (idParam != null && !idParam.isEmpty()) {
                    Long produitId = Long.parseLong(idParam);
                    Produit produit = em.find(Produit.class, produitId);

                    if (produit != null) {
                        em.getTransaction().begin();

                        // Vérifier si le produit est déjà présent dans le panier
                        List<LignePanier> existantes = em.createQuery(
                                "SELECT l FROM LignePanier l WHERE l.panier.id = :panierId AND l.produit.id = :prodId", LignePanier.class)
                                .setParameter("panierId", panierId)
                                .setParameter("prodId", produitId)
                                .getResultList();

                        if (!existantes.isEmpty()) {
                            LignePanier existing = existantes.get(0);
                            existing.setQuantite(existing.getQuantite() + 1);
                            em.merge(existing);
                        } else {
                            LignePanier ligne = new LignePanier();
                            ligne.setProduit(produit);
                            ligne.setQuantite(1);
                            ligne.setPanier(panier);
                            em.persist(ligne); // Sauvegarde la ligne en BDD
                        }

                        em.getTransaction().commit();
                    }
                }

                // Redirection vers la vitrine
                response.sendRedirect(request.getContextPath() + "/vitrine?action=lister");

            } else if ("supprimer".equals(action)) {
                String idLigneParam = request.getParameter("idLigne");
                if (idLigneParam != null && !idLigneParam.isEmpty()) {
                    Long ligneId = Long.parseLong(idLigneParam);
                    LignePanier ligne = em.find(LignePanier.class, ligneId);

                    if (ligne != null) {
                        em.getTransaction().begin();
                        em.remove(ligne); // Supprime la ligne de la BDD
                        em.getTransaction().commit();
                    }
                }

                // Redirection vers le panier
                response.sendRedirect(request.getContextPath() + "/panier?action=afficher");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ServletException(e);
        } finally {
            em.close();
        }
    }
}
