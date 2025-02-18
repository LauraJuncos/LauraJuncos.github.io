package hibernate.Proyecto_dgc;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TestCRUD {

    public static void main(String[] args) {
        // Crear una nueva entidad
        TestEntity entity = new TestEntity();
        entity.setName("Prueba");
        entity.setDescription("Esta es una descripción de prueba");

        // Guardar la entidad
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(entity);

            transaction.commit();
            System.out.println("Entidad guardada exitosamente: " + entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Leer la entidad desde la base de datos
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TestEntity retrievedEntity = session.get(TestEntity.class, entity.getId());
            System.out.println("Entidad recuperada: " + retrievedEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
