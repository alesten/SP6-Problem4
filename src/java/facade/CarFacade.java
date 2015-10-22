package facade;

import entity.Car;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CarFacade {

    EntityManager em;

    public CarFacade(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    public List<Car> GetCars() {
        return em.createNamedQuery("car.FindAll").getResultList();
    }

    public void EditCar(Car car) {
        em.getTransaction().begin();

        em.merge(car);

        em.getTransaction().commit();
    }

    public void DeleteCar(Long id) {
        em.getTransaction().begin();

        em.remove(em.find(Car.class, id));

        em.getTransaction().commit();
    }

    public void AddCar(Car car) {
        em.getTransaction().begin();

        em.persist(car);

        em.getTransaction().commit();
    }
}
