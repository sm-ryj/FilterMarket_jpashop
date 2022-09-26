package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //id가 없으면 신규로 보고 persist실행, id가 있으면 데베에 저장된 엔티티를 수정한다고 판단, merge를 실행
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {

        return em.find(Item.class, id);
    }

    public List<Item> findAll() {

        return em.createQuery("select i from Item i",Item.class).getResultList();
    }

}
