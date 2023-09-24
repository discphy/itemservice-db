package hello.itemservice.repository.v2;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static hello.itemservice.domain.QItem.item;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class ItemQueryRepositoryV2 {

	private final JPAQueryFactory query;

	public ItemQueryRepositoryV2(EntityManager em) {
		this.query = new JPAQueryFactory(em);
	}

	public List<Item> findAll(ItemSearchCond cond) {
		return query.select(item)
				.from(item)
				.where(
						likeItemName(cond.getItemName()),
						maxPrice(cond.getMaxPrice())
				).fetch();
	}

	private BooleanExpression maxPrice(Integer maxPrice) {
		if (maxPrice != null) {
			return item.price.loe(maxPrice);
		}

		return null;
	}

	private BooleanExpression likeItemName(String itemName) {
		if (hasText(itemName)) {
			return item.itemName.like("%" + itemName + "%");
		}

		return null;
	}
}
